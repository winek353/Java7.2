package uj.jwzp.w2.generator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("itemGenerator")
public class ItemGenerator {
    private final static Logger logger = LoggerFactory.getLogger(ItemGenerator.class);

    private RandomService randomService;
    BufferedReaderService bufferedReaderService;

    public ItemGenerator(RandomService randomService, BufferedReaderService bufferedReaderService) {
        this.randomService = randomService;
        this.bufferedReaderService = bufferedReaderService;
    }


    public List<Item> generateItems(ProgramParameters programParameters) throws Exception {
        String csvFileName = "src\\main\\resources\\"+programParameters.getItemsFileName();//"/tmp/" + programParameters.getItemsFileName();// "src\\main\\resources\\"+storage items.csv
        String line;
        String cvsSplitBy = ",";
        String [] itemLine;
        List<Item> items = new ArrayList<>();

        BufferedReader br = bufferedReaderService.getBufferedReader(csvFileName);
        int i=0;
        for(;i<programParameters.getItemsCountRange().getValue0() && br.ready();i++){
            br.readLine();
        }
        for (;i<=programParameters.getItemsCountRange().getValue1() && br.ready();i++){
            line = br.readLine();
            itemLine = line.split(cvsSplitBy);
            if (isItemLineCorrect(itemLine)){
                items.add(new Item(itemLine[0].replaceAll("\"", ""),
                        randomService.getRandomInt(programParameters.getItemsQuantityRange()),
                        new BigDecimal(itemLine[1])) );
            }
            else{
                logger.error("incorrect line number " + i + " in csv file");
                throw new Exception("incorrect line number " + i + " in csv file");
            }
        }
        return items;
    }
    private boolean isItemLineCorrect( String [] itemLine){
        if (itemLine.length < 2)
            return false;
        if(itemLine[0] == null || itemLine[1] == null)
            return false;
        if (! isStringBigDecimal(itemLine[1]))
            return false;
        return true;
    }

    private boolean isStringBigDecimal(String string){
        try {
            new BigDecimal(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
