package phwginfo.search;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SimpleSearch {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File(new File("data"),"Complete-Shakespeare.txt")));
        String line;
        int lineNumber = 0;

        String query = JOptionPane.showInputDialog("Welches Text suchen wir?", "To be or");
        while((line=reader.readLine())!=null) {
            lineNumber++;
            if(line.contains(query)) {
                System.out.println("Line: " + lineNumber + " : " + line);
            }
        }
        System.out.println("Finished.");
    }
}
