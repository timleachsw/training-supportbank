package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws IOException, XMLStreamException {
        TransactionList transactionList;
        // command line options
        // in future, perhaps an idea to use a dedicated CLI package instead of parsing them by hand?
        if (args.length != 3 || !args[1].equals("list")) {
            System.out.println("usage:\n" +
                    "\tmain file list all\t\tLists all people and their balances" +
                    "\tmain file list [account]\tLists the given account's transactions");
            return;
        } else {
            // load file
            String path = args[0];
            LOGGER.info(String.format("Attempting to load transaction list from file %s", path));
            transactionList = new TransactionList(path);
        }
        if (args[2].equals("all")) {
            transactionList.listAll();
        } else {
            transactionList.list(args[1]);
        }
    }
}
