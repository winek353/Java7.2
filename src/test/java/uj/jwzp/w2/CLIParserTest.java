package uj.jwzp.w2;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CLIParserTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    String outputDirName = "shouldNotExists";

    @After
    public void deleteOutputFile() throws IOException {
        Path path = Paths.get(outputDirName);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    public void correctValues() throws Exception {
        String[] args = { "-customerIds", "1:20", "-dateRange",
                "2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100",  "-itemsFile", "items.csv",
                "-itemsCount", "5:15", "-itemsQuantity", "1:30", "-eventsCount", "1000", "-outDir", "./" + outputDirName};

        CLIParser cliParser = new CLIParser();

        //when
        ProgramParameters programParameters = cliParser.parseProgramParameters(args);

        //then
        Assert.assertEquals(Integer.valueOf("1"), programParameters.getCustomerIdsRange().getValue0());
        Assert.assertEquals(Integer.valueOf("20"), programParameters.getCustomerIdsRange().getValue1());

        Assert.assertEquals("2018-03-08T00:00:00.000-0100", programParameters.getDateRange().getValue0());
        Assert.assertEquals("2018-03-08T23:59:59.999-0100", programParameters.getDateRange().getValue1());

        Assert.assertEquals("items.csv", programParameters.getItemsFileName());

        Assert.assertEquals(Integer.valueOf("5"), programParameters.getItemsCountRange().getValue0());
        Assert.assertEquals(Integer.valueOf("15"), programParameters.getItemsCountRange().getValue1());

        Assert.assertEquals(Integer.valueOf("1"), programParameters.getItemsQuantityRange().getValue0());
        Assert.assertEquals(Integer.valueOf("30"), programParameters.getItemsQuantityRange().getValue1());

        Assert.assertEquals(Integer.valueOf("1000"), programParameters.getEventsCount());

        Assert.assertEquals("./" + outputDirName, programParameters.getOutDir());
    }

    @Test
    public void defaultValues() throws Exception {
        String[] args = {"-itemsFile", "items.csv"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        CLIParser cliParser = new CLIParser();

        //when
        ProgramParameters programParameters = cliParser.parseProgramParameters(args);

        //then
        Assert.assertEquals(Integer.valueOf("1"), programParameters.getCustomerIdsRange().getValue0());
        Assert.assertEquals(Integer.valueOf("20"), programParameters.getCustomerIdsRange().getValue1());

        Assert.assertEquals(dateFormat.format(DateUtils.truncate(new Date(), Calendar.DATE)),
                programParameters.getDateRange().getValue0());
        Assert.assertEquals(dateFormat.format(DateUtils.addMilliseconds(DateUtils.ceiling(new Date(), Calendar.DATE), -1)),
                programParameters.getDateRange().getValue1());

        Assert.assertEquals("items.csv", programParameters.getItemsFileName());

        Assert.assertEquals(Integer.valueOf("1"), programParameters.getItemsCountRange().getValue0());
        Assert.assertEquals(Integer.valueOf("5"), programParameters.getItemsCountRange().getValue1());

        Assert.assertEquals(Integer.valueOf("1"), programParameters.getItemsQuantityRange().getValue0());
        Assert.assertEquals(Integer.valueOf("5"), programParameters.getItemsQuantityRange().getValue1());

        Assert.assertEquals(Integer.valueOf("100"), programParameters.getEventsCount());

        Assert.assertEquals( System.getProperty("user.dir"), programParameters.getOutDir());
    }

    @Test
    public void noCSVFile() throws Exception {
        String[] args = {};

        CLIParser cliParser = new CLIParser();

        exception.expect(Exception.class);
        exception.expectMessage("no csv file provided");
        //when
        ProgramParameters programParameters = cliParser.parseProgramParameters(args);

        //then
    }

    @Test
    public void wrongIntervalParameter() throws Exception {
        String[] args =  {"-customerIds", "20:1", "-itemsFile", "items.csv" };

        CLIParser cliParser = new CLIParser();

        exception.expect(Exception.class);
        exception.expectMessage("wrong interval in program parameter");

        //when
        cliParser.parseProgramParameters(args);

        //then
    }

    @Test
    public void wrongIntervalParameter2() throws Exception {
        String[] args =  {"-customerIds", "-1:10", "-itemsFile", "items.csv" };

        CLIParser cliParser = new CLIParser();

        exception.expect(Exception.class);
        exception.expectMessage("wrong interval in program parameter");

        //when
        cliParser.parseProgramParameters(args);

        //then
    }

    @Test
    public void wrongDateParameter() throws Exception {
        String[] args =  {"-dateRange", "wrong:date",
                "-itemsFile", "items.csv" };

        CLIParser cliParser = new CLIParser();

        exception.expect(Exception.class);

        //when
        ProgramParameters programParameters = cliParser.parseProgramParameters(args);
    }

    @Test
    public void createOutputDir () throws Exception {
        String[] args = { "-customerIds", "1:20", "-dateRange",
                "2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100",  "-itemsFile", "items.csv",
                "-itemsCount", "5:15", "-itemsQuantity", "1:30", "-eventsCount", "1000", "-outDir", "./" + outputDirName};

        CLIParser cliParser = new CLIParser();

        Path path = Paths.get(outputDirName);
        //when
        ProgramParameters programParameters = cliParser.parseProgramParameters(args);

        //then
        Assert.assertTrue(Files.exists(path));
    }
}
