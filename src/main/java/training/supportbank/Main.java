package training.supportbank;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String args[]) throws IOException, ParseException {
        // parse CSV file into a TransactionList
        TransactionList transactionList = TransactionList.fromFile("data/Transactions2014.csv");
    }
}
