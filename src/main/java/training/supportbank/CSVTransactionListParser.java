package training.supportbank;

import java.io.*;

public class CSVTransactionListParser implements TransactionListParser {
    private File file;

    public CSVTransactionListParser(File file) {
        this.file = file;
    }

    public TransactionList parse() throws IOException {
        TransactionList transactionList = new TransactionList();
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        // iterate through line-by-line (discard first line)
        reader.readLine();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            Transaction t = new Transaction();
            // attempt to read line
            if (t.readCSVLine(line)) {
                transactionList.addTransaction(t);
            }
        }

        return transactionList;
    }
}
