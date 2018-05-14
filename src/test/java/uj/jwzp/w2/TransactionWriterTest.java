package uj.jwzp.w2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionWriterTest {
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    List<Transaction> transactionList;

    List<Item> items;

    @Before
    public void before(){
        items = new ArrayList<>();
        items.add(new Item("mleko 3% 1l", 1,
                new BigDecimal("2.30")));
        items.add(new Item("buleczka", 1,
                new BigDecimal("1.20")));

        transactionList = new ArrayList<>();
        transactionList.add(new Transaction(1, "2018-03-08T00:00:00.000-0100",
                1, items));

        transactionList.get(0).setSum(new TransactionGenerator(new RandomService(),
                new ItemGenerator(new RandomService(), new BufferedReaderService())).computeSum(transactionList.get(0)));
    }

    @Test
    public void json() throws IOException, ParseException {
        String tmpFolder = folder.newFolder().getPath();

        String expectedString = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"timestamp\": \"2018-03-08T00:00:00.000-0100\",\n" +
                "    \"customer_id\": 1,\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"name\": \"mleko 3% 1l\",\n" +
                "        \"quantity\": 1,\n" +
                "        \"price\": 2.30\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"buleczka\",\n" +
                "        \"quantity\": 1,\n" +
                "        \"price\": 1.20\n" +
                "      }\n" +
                "    ],\n" +
                "    \"sum\": 3.50\n" +
                "  }\n" +
                "]";

        JSONTransactionWriter jsonTransactionWriter = new JSONTransactionWriter();
        jsonTransactionWriter.writeToFile(transactionList, tmpFolder);

        String result = "";
        List<String> expected = Files.readAllLines(Paths.get(tmpFolder + "/" + "transaction.json"));
        for(int i = 0; i < expected.size(); i++){
            result += expected.get(i);
            if(i+1 < expected.size())
                result += "\n";
        }
        Assert.assertEquals(expectedString, result);
    }

    @Test
    public void xml() throws IOException, ParseException {
        String tmpFolder = folder.newFolder().getPath();

        String expectedString = "<list>\n" +
                "  <transaction>\n" +
                "    <id>1</id>\n" +
                "    <timestamp>2018-03-08T00:00:00.000-0100</timestamp>\n" +
                "    <customer__id>1</customer__id>\n" +
                "    <items>\n" +
                "      <uj.jwzp.w2.entity.Item>\n" +
                "        <name>mleko 3% 1l</name>\n" +
                "        <quantity>1</quantity>\n" +
                "        <price>2.30</price>\n" +
                "      </uj.jwzp.w2.entity.Item>\n" +
                "      <uj.jwzp.w2.entity.Item>\n" +
                "        <name>buleczka</name>\n" +
                "        <quantity>1</quantity>\n" +
                "        <price>1.20</price>\n" +
                "      </uj.jwzp.w2.entity.Item>\n" +
                "    </items>\n" +
                "    <sum>3.50</sum>\n" +
                "  </transaction>\n" +
                "</list>";

        XMLTransactionWriter xmlTransactionWriter = new XMLTransactionWriter();
        xmlTransactionWriter.writeToFile(transactionList, tmpFolder);

        String result = "";
        List<String> expected = Files.readAllLines(Paths.get(tmpFolder + "/" + "transaction.xml"));
        for(int i = 0; i < expected.size(); i++){
            result += expected.get(i);
            if(i+1 < expected.size())
                result += "\n";
        }
        Assert.assertEquals(expectedString, result);
    }


    @Test
    public void yaml() throws IOException, ParseException {
        String tmpFolder = folder.newFolder().getPath();

        String expectedString = "---\n" +
                "- id: 1\n" +
                "  timestamp: \"2018-03-08T00:00:00.000-0100\"\n" +
                "  items:\n" +
                "  - name: \"mleko 3% 1l\"\n" +
                "    quantity: 1\n" +
                "    price: 2.30\n" +
                "  - name: \"buleczka\"\n" +
                "    quantity: 1\n" +
                "    price: 1.20\n" +
                "  sum: 3.50";
        YamlTransactionWriter yamlTransactionWriter = new YamlTransactionWriter();
        yamlTransactionWriter.writeToFile(transactionList, tmpFolder);

        String result = "";
        List<String> expected = Files.readAllLines(Paths.get(tmpFolder + "/" + "transaction.yaml"));
        for(int i = 0; i < expected.size(); i++){
            result += expected.get(i);
            if(i+1 < expected.size())
                result += "\n";
        }
        Assert.assertEquals(expectedString, result);
    }

}
