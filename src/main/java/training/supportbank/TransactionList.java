package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionList {
    private static final Logger LOGGER = LogManager.getLogger();

    private ArrayList<Transaction> transactionList;
    private ArrayList<String> uniqueNames;

    public TransactionList() {
        transactionList = new ArrayList<>();
        uniqueNames = new ArrayList<>();
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
