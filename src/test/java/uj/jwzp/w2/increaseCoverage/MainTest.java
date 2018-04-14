package uj.jwzp.w2.increaseCoverage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.Main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

public class MainTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    String[] args = { "-customerIds", "1:20", "-dateRange",
            "2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100",  "-itemsFile", "items.csv",
            "-itemsCount", "5:15", "-itemsQuantity", "1:30", "-eventsCount", "1000", "-outDir", "./uselessOutput"};

    @Test
    public void correctValues () throws Exception {
        Main main = new Main();

        Path path = Paths.get("uselessOutput/transaction.json");
        //when
        main.main(args);

        //then
        Assert.assertTrue(Files.exists(path));

        //after
        Files.delete(path);
        Files.delete(Paths.get("uselessOutput"));
    }
}
