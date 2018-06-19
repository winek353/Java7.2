package uj.jwzp.w2.launcher;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.ITransactionWriter;

import javax.jms.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class Spring {

    //old
    private final static Logger logger = LoggerFactory.getLogger(Spring.class);

    static ApplicationContext ctx = new AnnotationConfigApplicationContext("uj.jwzp.w2");

    public static ITransactionWriter chooseTransactionWriterFormat(String format){
        if(format.equals("xml"))
            return (ITransactionWriter) ctx.getBean("xmlTransactionWriter");
        else if(format.equals("yaml"))
            return (ITransactionWriter) ctx.getBean("yamlTransactionWriter");
        else
            return (ITransactionWriter) ctx.getBean("jsonTransactionWriter");
    }

    public static void main(String[] args) {
        CLIParser cliParser = (CLIParser) ctx.getBean("cliParser");
        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator");

        try {
            ProgramParameters programParameters = cliParser.parseProgramParameters(args);
            ITransactionWriter iTransactionWriter = chooseTransactionWriterFormat(programParameters.getFormat());

            List<Transaction> transactionList = transactionGenerator.generateTrasactions(programParameters);
            iTransactionWriter.writeToFile(transactionList,
                    programParameters.getOutDir());


            //new
            ConnectionFactory f = new ActiveMQConnectionFactory(programParameters.getBroker());//"tcp://199.247.18.11:61616"
            Connection conn = f.createConnection();
            Session s = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue q = s.createQueue(programParameters.getQueue());

            MessageProducer mp = s.createProducer(q);

            for (Transaction transaction: transactionList){
                sendMessage(transaction, s, mp);
                sleep(1000);
            }

            s.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void sendMessage(Transaction transaction, Session session,
                                    MessageProducer producer) throws Exception {
        Message message = session.createObjectMessage(transaction);
        System.out.println("Sending: " + transaction);
        producer.send(message);
    }
}
