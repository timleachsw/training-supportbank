package training.supportbank;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TransactionListParser {
    TransactionList parse() throws IOException, XMLStreamException;

    static TransactionListParser getParserFor(File file) {
        // extract the file extension
        Matcher fileExtensionMatcher = Pattern.compile(".*\\.(.*)$").matcher(file.getPath());
        String fileExtension;
        if (fileExtensionMatcher.find()) {
            fileExtension = fileExtensionMatcher.group(1);
        } else {
            throw new IllegalArgumentException("Cannot parse a file with no file extension.");
        }
        switch (fileExtension) {
            case "csv":
                return new CSVTransactionListParser(file);
            case "json":
                return new JSONTransactionListParser(file);
            case "xml":
                return new XMLTransactionListParser(file);
            default:
                throw new IllegalArgumentException(
                        String.format("Cannot parse file \"%s\": extension \"%s\" not recognised.",
                        file.getPath(), fileExtension));
        }
    }
}
