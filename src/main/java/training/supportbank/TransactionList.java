package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TransactionList {
    public void readFile(String path) throws FileNotFoundException {
        // open file
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
    }
}
