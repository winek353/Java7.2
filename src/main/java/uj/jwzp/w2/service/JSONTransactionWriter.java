package uj.jwzp.w2.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.generator.ItemGenerator;

import java.io.*;
import java.text.ParseException;
import java.util.*;

@Service("jsonTransactionWriter")
public class JSONTransactionWriter implements ITransactionWriter {
    private final static Logger logger = LoggerFactory.getLogger(ItemGenerator.class);

    @Override
    public void writeToFile(List<Transaction> transactionList, String outDirectory) throws ParseException {
        logger.trace("creating JSON file");

        if(outDirectory.equals(""))
            outDirectory = "transaction.json";
        else
            outDirectory = outDirectory + "\\transaction.json";

        try (Writer writer = new FileWriter(new File(outDirectory) )) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(transactionList, writer);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("JSON file created successfully");
    }
}