package training.supportbank;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XMLTransactionListParser implements TransactionListParser {
    private File file;

    public XMLTransactionListParser(File file) {
        this.file = file;
    }

    public TransactionList parse() throws IOException, XMLStreamException {
        TransactionList transactionList = new TransactionList();

        // parse XML using StAX
        FileReader fileReader = new FileReader(file);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(fileReader);

        // iterate through XML file
        while (xmlStreamReader.hasNext()) {
            xmlStreamReader.next();
        }

        return transactionList;
    }
}
