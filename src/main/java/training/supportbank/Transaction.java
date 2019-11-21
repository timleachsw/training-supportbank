package training.supportbank;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
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

    public static Transaction fromCSVLine(String line) throws ParseException {
        // split line at commas
        String[] splitLine = line.split(",");
        LocalDate date = LocalDate.parse(splitLine[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
