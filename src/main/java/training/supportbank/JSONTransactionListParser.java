package training.supportbank;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JSONTransactionListParser implements TransactionListParser {
    private File file;

    public JSONTransactionListParser(File file) {
        this.file = file;
    }

    public TransactionList parse() throws IOException {
        TransactionList transactionList = new TransactionList();
        // create Gson object and parse whole file as one string
        Gson gson = new Gson();
        String json = Files.readString(file.toPath());
        Transaction.TransactionJSON[] transactionArray = gson.fromJson(json, Transaction.TransactionJSON[].class);

        // iterate through, rather than converting our array directly, to ensure
        // uniqueNames is being updated
        for (Transaction.TransactionJSON transactionJSON: transactionArray) {
            transactionList.addTransaction(new Transaction(transactionJSON));
        }

        return transactionList;
    }
}
