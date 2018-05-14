package uj.jwzp.w2.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service("transactionGenerator")
public class TransactionGenerator {
    private final static Logger logger = LoggerFactory.getLogger(TransactionGenerator.class);

    private RandomService randomService;
    private ItemGenerator itemGenerator;

    public TransactionGenerator(RandomService randomService, ItemGenerator itemGenerator) {
        this.randomService = randomService;
        this.itemGenerator = itemGenerator;
    }

    public  List<Transaction> generateTrasactions (ProgramParameters programParameters) throws Exception {
        logger.trace("generating transactions");
        List<Transaction> transactionList = new ArrayList<>();

        for (int i=1;i<=programParameters.getEventsCount();i++){
            transactionList.add(new Transaction(i,randomService.getRandomTimeTimestamp(programParameters.getDateRange()),
                    randomService.getRandomInt(programParameters.getCustomerIdsRange()),
                    itemGenerator.generateItems(programParameters) ));
            transactionList.get(i-1).setSum(computeSum(transactionList.get(i-1)));
        }
        logger.info("generating transactions successful");
        return transactionList;
    }

    public BigDecimal computeSum(Transaction transaction){
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Item item: transaction.getItems() ) {
            sum = sum.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            System.out.println("suma = " + item);
        }
        logger.debug("computed sum = " + sum + " transaction id = " + transaction.getId());
        return sum;
    }
}
