package training.supportbank;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JSONTransactionListParser implements TransactionListParser {
    private File file;

    private static class TransactionJSON {
        private String date;
        private String fromAccount;
        private String toAccount;
        private String narrative;
        private BigDecimal amount;
        public Transaction toTransaction() {
            return new Transaction(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    fromAccount,
                    toAccount,
                    narrative,
                    amount);
        }
    }

    public JSONTransactionListParser(File file) {
        this.file = file;
    }

    public TransactionList parse() throws IOException {
        TransactionList transactionList = new TransactionList();
        // create Gson object and parse whole file as one string
        Gson gson = new Gson();
        String json = Files.readString(file.toPath());
        TransactionJSON[] transactionArray = gson.fromJson(json, TransactionJSON[].class);

        // iterate through, rather than converting our array directly, to ensure
        // uniqueNames is being updated
        for (TransactionJSON transactionJSON: transactionArray) {
            transactionList.addTransaction(transactionJSON.toTransaction());
        }

        return transactionList;
    }
}
