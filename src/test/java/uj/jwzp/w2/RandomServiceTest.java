package uj.jwzp.w2;

import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;
import uj.jwzp.w2.service.RandomService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomServiceTest {

    RandomService randomService = new RandomService();

    boolean isDateWithinRange(Date testDate, Date startDate, Date endDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

    @Test
    public void randomTimeTimestamp() throws ParseException {
        Pair<String,String> range = new Pair<String, String>("2018-03-08T00:00:00.000-0100",
                "2018-03-08T23:59:59.999-0100");

        String resultString = randomService.getRandomTimeTimestamp(range);

        Date resultDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse( resultString);
        Date start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parse( range.getValue0());
        Date end = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parse( range.getValue1());

        Assert.assertEquals(true, isDateWithinRange( resultDate, start, end) );
    }

    @Test
    public void getRandomInt(){
        Pair<Integer, Integer> range = new Pair<Integer, Integer>(1, 20);

        int result = randomService.getRandomInt(range);

        Assert.assertTrue(result>=range.getValue0()&&result<=range.getValue1());
    }

}
