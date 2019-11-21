package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws IOException, ParseException {
        // parse CSV file into a TransactionList
        String csvPath = "data/DodgyTransactions2015.csv";
        LOGGER.info(String.format("Attempting to load transaction list from file %s", csvPath));
        TransactionList transactionList = TransactionList.fromFile(csvPath);

        // command line options
        // in future, perhaps an idea to use a dedicated CLI package instead of parsing them by hand?
        if (args.length != 2 || !args[0].equals("list")) {
            System.out.println("usage:\n" +
                    "\tmain list all\t\tLists all people and their balances" +
                    "\tmain list [account]\tLists the given account's transactions");
            return;
        }
        if (args[1].equals("all")) {
            transactionList.listAll();
        } else {
            transactionList.list(args[1]);
        }
    }
}
