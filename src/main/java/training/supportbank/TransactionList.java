package training.supportbank;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionList {
    private static final Logger LOGGER = LogManager.getLogger();

    private ArrayList<Transaction> transactionList;
    private ArrayList<String> uniqueNames;

    public TransactionList() {
        transactionList = new ArrayList<>();
        uniqueNames = new ArrayList<>();
    }

    public TransactionList(String path) throws IOException {
        transactionList = new ArrayList<>();
        uniqueNames = new ArrayList<>();
        readFile(path);
    }

    public void readFile(String path) throws IOException {
        // CSV or JSON?
        if (path.matches(".*\\.csv")) {
            // open file
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            // iterate through line-by-line (discard first line)
            clear();
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Transaction t = new Transaction();
                // attempt to read line
                if (t.readCSVLine(line)) {
                    addTransaction(t);
                }
            }
        } else if (path.matches(".*\\.json")) {
            // create Gson object and parse whole file as one string
            Gson gson = new Gson();
            String json = Files.readString(Path.of(path));
            Transaction.TransactionJSON[] transactionArray = gson.fromJson(json, Transaction.TransactionJSON[].class);

            // iterate through, rather than converting our array directly, to ensure
            // uniqueNames is being updated
            for (Transaction.TransactionJSON transactionJSON: transactionArray) {
                addTransaction(new Transaction(transactionJSON));
            }
        } else {
            Matcher fileTypeMatcher = Pattern.compile(".*(\\..*$)").matcher(path);
            if (fileTypeMatcher.find()) {
                throw new IllegalArgumentException(String.format("Don't know how to read %s files.",
                        fileTypeMatcher.group(1)));
            } else {
                throw new IllegalArgumentException("Don't know how to read files with no extension.");
            }
        }
    }

    public void clear() {
        transactionList.clear();
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
        String from = transaction.getFromAccount();
        String to = transaction.getToAccount();
        if (!uniqueNames.contains(from)) {
            uniqueNames.add(from);
        }
        if (!uniqueNames.contains(to)) {
            uniqueNames.add(to);
        }
    }

    public void addTransaction(LocalDate date, String from, String to, String narrative, BigDecimal amount) {
        transactionList.add(new Transaction(date, from, to, narrative, amount));
    }

    public ArrayList<Transaction> getTransactions() {
        return transactionList;
    }

    public void listAll() {
        for (String name: uniqueNames) {
            System.out.println(String.format("%10s: £%s", name, balance(name).toPlainString()));
        }
    }

    public void list(String name) {
        System.out.println(String.format("%s balance: £%s", name, balance(name).toPlainString()));
        System.out.println("Transaction list:");
        for (Transaction transaction: transactionList) {
            if (transaction.involves(name)) {
                System.out.println(String.format("%1$tY/%1$tm/%1$td: %2$6s\t%3$-16s%4$s",
                        transaction.getDate(),
                        "£" + transaction.getAmount().toPlainString(),
                        transaction.getFromAccount().equals(name)
                                ? "  to " + transaction.getToAccount()
                                : "from " + transaction.getFromAccount(),
                        transaction.getNarrative()));
            }
        }
    }

    public boolean includes(String name) {
        for (Transaction transaction: transactionList) {
            if (transaction.involves(name)) {
                return true;
            }
        }
        return false;
    }

    public BigDecimal balance(String name) {
        // iterate through all transactions, keeping a tally
        // returns ZERO if name does not match
        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction transaction: transactionList) {
            if (transaction.involves(name)) {
                if (transaction.getFromAccount().equals(name)) {
                    balance = balance.subtract(transaction.getAmount());
                } else {
                    balance = balance.add(transaction.getAmount());
                }
            }
        }
        return balance;
    }
}
