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

    public Transaction() { }

    public Transaction(LocalDate date, String fromAccount, String toAccount, String narrative, BigDecimal amount) {
        this.date = date;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.narrative = narrative;
        this.amount = amount;
    }

    public boolean readCSVLine(String line) {
        try {
            LOGGER.debug("Reading CSV line " + line);
            String[] splitLine = line.split(",");
            LOGGER.debug(String.format("Using LocalDate.parse() to read '%s' in the format dd/mm/yyyy", splitLine[0]));
            this.date = LocalDate.parse(splitLine[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LOGGER.debug(String.format("Using BigDecimal() to read '%s' as a BigDecimal", splitLine[4]));
            this.fromAccount = splitLine[1];
            this.toAccount = splitLine[2];
            this.narrative = splitLine[3];
            this.amount = new BigDecimal(splitLine[4]);
            return true;
        } catch (RuntimeException e) {
            LOGGER.warn(String.format("Unable to read CSV line \"%s\".", line));
            return false;
        }
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
