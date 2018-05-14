package uj.jwzp.w2;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class ItemGeneratorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    RandomService randomService;

    @Mock
    BufferedReaderService bufferedReaderService;

    ItemGenerator itemGenerator;

    ProgramParameters programParameters;

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
    }

    @Test
    public void correctValues() throws Exception {
        //given

        Mockito.when(randomService.getRandomInt(Mockito.any())).thenReturn(1);
        Mockito.when(randomService.getRandomTimeTimestamp(programParameters.getDateRange()))
                .thenReturn("2018-03-08T00:00:00.000-0100");

        Mockito.when(bufferedReaderService.getBufferedReader(Mockito.any()))
                .thenReturn( new BufferedReader(new StringReader("name,price\n" +
                        "\"mleko 3% 1l\",2.30\n" +
                        "\"bułeczka\",1.20")));

        itemGenerator = new ItemGenerator(randomService, bufferedReaderService);

        List<Item> expectedItems = new ArrayList<>();
        expectedItems.add(new Item("mleko 3% 1l", randomService
                .getRandomInt(programParameters.getItemsQuantityRange()),
                new BigDecimal("2.30")));
        expectedItems.add(new Item("bułeczka", randomService
                .getRandomInt(programParameters.getItemsQuantityRange()),
                new BigDecimal("1.20")));
        //when
        List<Item> resultItems = itemGenerator.generateItems(programParameters);

        //then
        for (Item expectedItem : expectedItems) {
            assertThat(resultItems, hasItems(
                    expectedItem
            ));
        }

    }
    @Test(expected = Exception.class)
    public void incorrectItemLine() throws Exception {
        //given
        Mockito.when(randomService.getRandomInt(Mockito.any())).thenReturn(1);
        Mockito.when(randomService.getRandomTimeTimestamp(programParameters.getDateRange()))
                .thenReturn("2018-03-08T00:00:00.000-0100");

        Mockito.when(bufferedReaderService.getBufferedReader(Mockito.any()))
                .thenReturn( new BufferedReader(new StringReader("name,price\n" +
                        "\"mleko 3% 1l\",2.30\n" +
                        "WRONG")));

        itemGenerator = new ItemGenerator(randomService, bufferedReaderService);

        //when
        itemGenerator.generateItems(programParameters);

        //then
    }
}
