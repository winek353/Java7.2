package uj.jwzp.w2.launcher;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.ITransactionWriter;
import uj.jwzp.w2.service.JSONTransactionWriter;
import uj.jwzp.w2.service.XMLTransactionWriter;
import uj.jwzp.w2.service.YamlTransactionWriter;


public class Spring {
//    private final static Logger logger = LoggerFactory.getLogger(Spring.class);
//
//    static ApplicationContext ctx = new AnnotationConfigApplicationContext("uj.jwzp.w2");
//
//    public static ITransactionWriter chooseTransactionWriterFormat(String format){
//        if(format.equals("xml"))
//            return (ITransactionWriter) ctx.getBean("xmlTransactionWriter");
//        else if(format.equals("yaml"))
//            return (ITransactionWriter) ctx.getBean("yamlTransactionWriter");
//        else
//            return (ITransactionWriter) ctx.getBean("jsonTransactionWriter");
//    }
//
//    public static void main(String[] args) {
//        CLIParser cliParser = (CLIParser) ctx.getBean("cliParser");
//        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator");
//
//        try {
//            ProgramParameters programParameters = cliParser.parseProgramParameters(args);
//            ITransactionWriter iTransactionWriter = chooseTransactionWriterFormat(programParameters.getFormat());
//
//            iTransactionWriter.writeToFile(transactionGenerator.generateTrasactions(programParameters),
//                    programParameters.getOutDir());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        JSONTransactionWriter transactionToJsonService = (JSONTransactionWriter) ctx.getBean("transactionToJsonService");
////
////        ProgramParameters programParameters = null;
////        try {
////            programParameters = cliParser.parseProgramParameters(args);
////            transactionToJsonService.createJSON(transactionGenerator.generateTrasactions(programParameters),
////                    programParameters);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
}
