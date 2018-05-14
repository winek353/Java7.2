package uj.jwzp.w2.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.*;


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

    public static void main(String[] args) {
        //-customerIds 1:20 -dateRange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100  itemsFile items.csv  -itemsCount 5:15 itemsQuantity 1:30 -eventsCount 1000 -outDir ./output -format xml
        CLIParser cliParser = new CLIParser();

        ProgramParameters programParameters = null;
        try {
            programParameters = cliParser.parseProgramParameters(args);
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
