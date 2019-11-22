package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();

    private LocalDate date;
    private String fromAccount;
    private String toAccount;
    private String narrative;
    private BigDecimal amount;

    public class TransactionJSON {
        public String date;
        public String fromAccount;
        public String toAccount;
        public String narrative;
        public BigDecimal amount;
    }

    public Transaction(LocalDate date, String fromAccount, String toAccount, String narrative, BigDecimal amount) {
        this.date = date;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.narrative = narrative;
        this.amount = amount;
    }

    public Transaction(TransactionJSON transactionJSON) {
        // note for the following that the format is different
        // the JSON file uses yyyy-mm-dd but the CSV uses dd/mm/yyyy
        this.date = LocalDate.parse(transactionJSON.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.fromAccount = transactionJSON.fromAccount;
        this.toAccount = transactionJSON.toAccount;
        this.narrative = transactionJSON.narrative;
        this.amount = transactionJSON.amount;
    }

    public static Transaction fromCSVLine(String line) {
        // split line at commas
        LOGGER.debug("Reading CSV line " + line);
        String[] splitLine = line.split(",");
        LOGGER.debug(String.format("Using LocalDate.parse() to read '%s' in the format dd/mm/yyyy", splitLine[0]));
        LocalDate date = LocalDate.parse(splitLine[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LOGGER.debug(String.format("Using BigDecimal() to read '%s' as a BigDecimal", splitLine[4]));
        BigDecimal amount = new BigDecimal(splitLine[4]);
        return new Transaction(date, splitLine[1], splitLine[2], splitLine[3], amount);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getNarrative() {
        return narrative;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean involves(String name) {
        return name.equals(fromAccount) || name.equals(toAccount);
    }
}
