package uj.jwzp.w2.service;

import org.javatuples.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomService {
    public int getRandomInt (Pair<Integer, Integer> range){
        return ThreadLocalRandom.current().nextInt(range.getValue0(), range.getValue1() + 1);
    }

    public String getRandomTimeTimestamp (Pair<String, String> dateRange) throws ParseException {
        Date d1 =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse( dateRange.getValue0());
        Date d2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse( dateRange.getValue1());
        Date randomDate = new Date(ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return dateFormat.format(randomDate);
    }
}
