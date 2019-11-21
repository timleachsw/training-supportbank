package training.supportbank;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        // parse CSV file into a TransactionList
        TransactionList transactionList = TransactionList.fromFile("data/Transactions2014.csv");

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
