package com.example.api.controller;

import com.example.api.pojo.CustomError;
import com.example.api.sevice.IntegrationSevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static java.lang.String.format;

@RestController
@Api(tags = "Contract")
public class ContractController {

    private ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private IntegrationSevice integrationSevice;

    @PostMapping("/api/send-sha")
    @ApiOperation(value = "send sha to ETH contract", tags = "Contract", notes = "Method for send transaction to Smart Contract")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idMessage", value = "idMessage param"),
            @ApiImplicitParam(name = "idFrom", value = "idFrom param"),
            @ApiImplicitParam(name = "idTo", value = "idTo param"),
            @ApiImplicitParam(name = "sha", value = "sha param"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomError.class),
            @ApiResponse(code = 2007, message = "Failed to process", response = CustomError.class),
            @ApiResponse(code = 2008, message = "Not enough money to create blockchain tx or not enough gas_price/gas_limit", response = CustomError.class),
    })
    public ResponseEntity<?> storeData(@RequestParam String idMessage,
                                       @RequestParam String idFrom,
                                       @RequestParam String idTo,
                                       @RequestParam String sha) {

        try {

            TransactionReceipt receipt = integrationSevice.storeData(idMessage, idFrom, idTo, sha);

            LOGGER.info(format("Transaction hash - %s", receipt.getTransactionHash()));

            if (receipt.getLogs().size() == 0) {
                LOGGER.error(format("Error storeData method: - %s", "failed to process"));
                return ResponseEntity.status(2007).body(new CustomError(2007, "failed to process"));
            }
            BigInteger txId = Numeric.decodeQuantity(receipt.getLogs().get(0).getTopics().get(1));

            return ResponseEntity.status(200).body(mapper.createObjectNode().put("tx_id", txId.toString()));

        } catch (Exception e) {
            LOGGER.error(format("Error storeData method: - %s", e.getMessage()));
            ResponseEntity response = null;
            if (e.getMessage().contains("Error processing transaction request")){
                response = ResponseEntity.status(2008).body(new CustomError(2008, e.getCause().getMessage()));
            } else {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
            }
                return response;
        }

    }

    @GetMapping("/api/read-sha/{sha}")
    @ApiOperation(value = "Find by sha", tags = "Contract", notes = "Method for find transaction by sha from Smart Contract")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomError.class),
    })
    public ResponseEntity<?> findBySha(@PathVariable String sha) {
        try {
            Tuple4 result = integrationSevice.findBySha(sha);

            if (Long.valueOf(result.getValue4().toString()) == -1) {
                return ResponseEntity.ok(mapper.createObjectNode());
            }

            return ResponseEntity.ok(mapper.createObjectNode()
                    .put("id_message", result.getValue1().toString())
                    .put("id_from", result.getValue2().toString())
                    .put("id_to", result.getValue3().toString())
                    .put("tx_id", new BigInteger(result.getValue4().toString())));

        } catch (Exception e) {
            LOGGER.error(format("Error findBySha method: - %s", e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/api/read-tx/{txId}")
    @ApiOperation(value = "Find by tx_id", tags = "Contract", notes = "Method for find transaction by tx_id from Smart Contract")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomError.class),
    })
    public ResponseEntity<?> findByTxId(@PathVariable BigInteger txId) {
        try {
            Tuple4 result = integrationSevice.findById(txId);

            if (result.getValue4().toString().equals("null")) {
                return ResponseEntity.ok(mapper.createObjectNode());
            }

            return ResponseEntity.ok(mapper.createObjectNode()
                    .put("id_message", result.getValue1().toString())
                    .put("id_from", result.getValue2().toString())
                    .put("id_to", result.getValue3().toString())
                    .put("sha", result.getValue4().toString()));

        } catch (Exception ex) {
            LOGGER.error(format("Error findByTxId method: - %s", ex.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        }
    }
}
