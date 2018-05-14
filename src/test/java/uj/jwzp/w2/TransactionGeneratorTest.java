package uj.jwzp.w2;

import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TransactionGeneratorTest {

    @Mock
    RandomService randomService;

    @Mock
    ItemGenerator itemGenerator;

    ProgramParameters programParameters;

    List<Item> items;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void before(){
        programParameters = new ProgramParameters(
                new Pair<Integer, Integer>(1, 20),
                new Pair<String, String>("2018-03-08T00:00:00.000-0100", "2018-03-08T23:59:59.999-0100"),
                "items.csv",
                new Pair<Integer, Integer>(1, 2),
                new Pair<Integer, Integer>(1, 5),
                1,
                System.getProperty("user.dir")
        );

        items = new ArrayList<>();
        items.add(new Item("mleko 3% 1l", 1,
                new BigDecimal("2.30")));
        items.add(new Item("bułeczka", 1,
                new BigDecimal("1.20")));
    }

    @Test
    public void correctValues () throws Exception {
        Mockito.when(randomService.getRandomInt(Mockito.any())).thenReturn(1);
        Mockito.when(randomService.getRandomTimeTimestamp(programParameters.getDateRange()))
                .thenReturn("2018-03-08T00:00:00.000-0100");

        Mockito.when(itemGenerator.generateItems(programParameters)).thenReturn(items);

        TransactionGenerator transactionGenerator = new TransactionGenerator(randomService, itemGenerator);
        List<Transaction> transactionList = transactionGenerator.generateTrasactions(programParameters);

        System.out.println(transactionList.get(0).getSum());

        for (Transaction transaction : transactionList) {
            Assert.assertEquals(transaction.getTimestamp(),
                    randomService.getRandomTimeTimestamp(programParameters.getDateRange()));
            Assert.assertEquals(transaction.getSum(), new BigDecimal("3.50"));

            for (Item expectedItem : items) {
                assertThat(transaction.getItems(), hasItems(
                        expectedItem
                ));
            }
        }
    }

}
