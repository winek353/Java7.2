package uj.jwzp.w2.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
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

@Service("xmlTransactionWriter")
public class XMLTransactionWriter implements ITransactionWriter {
    private final static Logger logger = LoggerFactory.getLogger(XMLTransactionWriter.class);

    @Override
    public void writeToFile(List<Transaction> transactionList, String outDirectory) throws ParseException {
        logger.trace("creating XML file");

        if(outDirectory.equals(""))
            outDirectory = "transaction.XML";
        else
            outDirectory = outDirectory + "\\transaction.XML";

        try (Writer writer = new FileWriter(new File(outDirectory) )) {
            XStream xstream = new XStream(new DomDriver());

            xstream.alias("transaction", Transaction.class);

            xstream.toXML(transactionList, writer);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("XML file created successfully");
    }
}
