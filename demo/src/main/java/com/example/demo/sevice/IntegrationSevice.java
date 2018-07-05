package com.example.demo.sevice;

import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import java.math.BigInteger;

@Service
public interface IntegrationSevice {

    TransactionReceipt storeData(String idMessage, String idFrom, String idTo, String sha) throws Exception;

    Tuple4<String, String, String, BigInteger> findBySha(String sha) throws Exception;

    Tuple4<String, String, String, String> findById(BigInteger txId) throws Exception;
}
