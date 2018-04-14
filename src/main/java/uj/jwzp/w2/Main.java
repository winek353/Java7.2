package uj.jwzp.w2;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;
import uj.jwzp.w2.service.TransactionToJSONService;


public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        CLIParser cliParser = new CLIParser();

        ProgramParameters programParameters = null;
        try {
            programParameters = cliParser.parseProgramParameters(args);
            TransactionGenerator transactionGenerator = new TransactionGenerator(new RandomService(),
                    new ItemGenerator(new RandomService(), new BufferedReaderService()));

            TransactionToJSONService transactionToJsonService = new TransactionToJSONService();

            transactionToJsonService.createJSON(transactionGenerator.generateTrasactions(programParameters),
                    programParameters);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
