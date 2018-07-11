package com.example.api.sevice.impl;

import com.example.api.contract.Storage;
import com.example.api.sevice.IntegrationSevice;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class IntegrationServiceImpl implements IntegrationSevice {

    private static Properties prop;
    private static String LOCALHOST;
    private static String FROM;
    private static String PRIVATE_KEY;
    private static String TO;
//    private static String GAS_PRICE;
    private static String GAS_LIMIT;

    private static Web3j web3j;
    private static Storage storage;


    static {
        InputStream is = null;
        try {
            prop = new Properties();
            is = ClassLoader.class.getResourceAsStream("/application.properties");
            prop.load(is);
            LOCALHOST = prop.getProperty("smart.localhost");
            FROM = prop.getProperty("smart.from");
            PRIVATE_KEY = prop.getProperty("smart.private.key");
            TO = prop.getProperty("smart.to");
//            GAS_PRICE = prop.getProperty("smart.gas.price");
            GAS_LIMIT = prop.getProperty("smart.gas.limit");
            web3j = Web3j.build(new HttpService(LOCALHOST));
            storage = new Storage(TO,
                    web3j,
                    Credentials.create(PRIVATE_KEY),
                    web3j.ethGasPrice().sendAsync().get().getGasPrice(),
                    new BigInteger(GAS_LIMIT));

            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TransactionReceipt storeData(String idMessage, String idFrom, String idTo, String sha) throws Exception {
            return storage.storeData(idMessage, idFrom, idTo, sha).sendAsync().get();
    }

    @Override
    public Tuple4<String, String, String, BigInteger> findBySha(String sha) throws Exception {
        return storage.findBySha(sha).sendAsync().get();
    }

    @Override
    public Tuple4<String, String, String, String> findById(BigInteger txId) throws Exception {
        return storage.findById(txId).sendAsync().get();
    }

    @Override
    public BigInteger getGasPrice() throws ExecutionException, InterruptedException {
        return web3j.ethGasPrice().sendAsync().get().getGasPrice();
    }

}
