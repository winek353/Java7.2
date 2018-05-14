package uj.jwzp.w2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.entity.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.List;

@Service("yamlTransactionWriter")
public class YamlTransactionWriter implements ITransactionWriter {
    private final static Logger logger = LoggerFactory.getLogger(YamlTransactionWriter.class);

    @Override
    public void writeToFile(List<Transaction> transactionList, String outDirectory) throws ParseException {
        logger.trace("creating yaml file");

        if(outDirectory.equals(""))
            outDirectory = "transaction.yaml";
        else
            outDirectory = outDirectory + "\\transaction.yaml";

        try (Writer writer = new FileWriter(new File(outDirectory) )) {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            // Write object as YAML file
            mapper.writeValue(writer, transactionList);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("yaml file created successfully");

    }
}
