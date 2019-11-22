package training.supportbank;

import java.io.File;
import java.io.IOException;

public abstract class TransactionListParser {
    public abstract TransactionList parseFile(File file) throws IOException;
    public TransactionList parseFile(String path) throws IOException {
        return parseFile(new File(path));
    }
}
