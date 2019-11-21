package training.supportbank;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionList {
    private ArrayList<Transaction> transactionList;

    public TransactionList() {
        transactionList = new ArrayList<>();
    }

    public static TransactionList fromFile(String path) throws IOException, ParseException {
        TransactionList list = new TransactionList();
        list.readFile(path);
        return list;
    }

    public void readFile(String path) throws IOException, ParseException {
        // open file
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        // iterate through line-by-line (discard first line)
        clear();
        reader.readLine();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            addTransaction(Transaction.fromCSVLine(line));
        }
    }

    public void clear() {
        transactionList.clear();
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    public void addTransaction(LocalDate date, String from, String to, String narrative, BigDecimal amount) {
        transactionList.add(new Transaction(date, from, to, narrative, amount));
    }

    public ArrayList<Transaction> getTransactions() {
        return transactionList;
    }

    public void listAll() {
        // TODO
    }

    public void list(String name) {
        // TODO
    }
}
