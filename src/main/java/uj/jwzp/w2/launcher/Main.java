package uj.jwzp.w2.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static ITransactionWriter chooseTransactionWriterFormat(String format){
        if(format.equals("xml"))
            return new XMLTransactionWriter();
        else if(format.equals("yaml"))
            return new YamlTransactionWriter();
        else
            return new JSONTransactionWriter();
    }

    public static String [] propertiesToArray() {
        File file = new File("/tmp/generator.properties");

        List<String> result = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String [] line;
            while (br.ready()){
                line = br.readLine().split("=");
                result.add("-" + line[0]);
                result.add(line[1].replaceAll("\"", ""));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toArray(new String[0]);
    }


    public static void main(String[] args) {
        //-customerIds 1:20 -dateRange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100  -itemsFile items.csv  -itemsCount 5:15 itemsQuantity 1:30 -eventsCount 1000 -outDir ./output -format xml
//        args = propertiesToArray();
        CLIParser cliParser = new CLIParser();

        ProgramParameters programParameters = null;
        try {
            programParameters = cliParser.parseProgramParameters(args);
            System.out.println(programParameters);
            TransactionGenerator transactionGenerator = new TransactionGenerator(new RandomService(),
                    new ItemGenerator(new RandomService(), new BufferedReaderService()));

            ITransactionWriter iTransactionWriter = chooseTransactionWriterFormat(programParameters.getFormat());
            iTransactionWriter.writeToFile(transactionGenerator.generateTrasactions(programParameters),
                    programParameters.getOutDir());

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
