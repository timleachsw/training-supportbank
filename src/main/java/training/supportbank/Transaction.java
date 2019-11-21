package training.supportbank;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private Date _date;
    private String _from;
    private String _to;
    private String _narrative;
    private BigDecimal _amount;

    public Transaction(Date date, String from, String to, String narrative, BigDecimal amount) {
        _date = date;
        _from = from;
        _to = to;
        _narrative = narrative;
        _amount = amount;
    }

    public Date get_date() {
        return _date;
    }

    public String get_from() {
        return _from;
    }

    public String get_to() {
        return _to;
    }

    public String get_narrative() {
        return _narrative;
    }

    public BigDecimal get_amount() {
        return _amount;
    }
}
