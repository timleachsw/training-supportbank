package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();

    private LocalDate date;
    private String from;
    private String to;
    private String narrative;
    private BigDecimal amount;

    public Transaction(LocalDate date, String from, String to, String narrative, BigDecimal amount) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.narrative = narrative;
        this.amount = amount;
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getNarrative() {
        return narrative;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean involves(String name) {
        return name.equals(from) || name.equals(to);
    }
}
