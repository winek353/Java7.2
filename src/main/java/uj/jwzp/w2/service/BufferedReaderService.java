package uj.jwzp.w2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uj.jwzp.w2.generator.ItemGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class BufferedReaderService {
    private final static Logger logger = LoggerFactory.getLogger(BufferedReaderService.class);

    public BufferedReader getBufferedReader(String fileName){
        try {
            return  new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
