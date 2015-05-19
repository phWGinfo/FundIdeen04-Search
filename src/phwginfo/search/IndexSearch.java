package phwginfo.search;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IndexSearch {
    public static void main(String[] args) throws Exception {

        ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("index.ser"));
        System.out.println("Loading index from index.ser.");
        IndexNode rootNode = (IndexNode) objIn.readObject();
        objIn.close();
        System.out.println("... Loaded index from index.ser.");

        String query = JOptionPane.showInputDialog("Welches Text suchen wir?", "To be or");
        while(query!=null && query.length()!=0) {
            if(query==null || query.length()==0) break;
            IndexNode node = rootNode.findNode(query, 0, false);
            StringBuffer lines = new StringBuffer();
            if(node==null) {
                query = JOptionPane.showInputDialog("Leider nicht gefunden. Versuchen Sie es nochmals. Ihre anfrage?", query);
            } else {
                for(Object lineNumber : node.references) {
                    int l = (Integer) lineNumber;
                    lines.append(readLineFromFile(l)).append("\n");
                }
                query = JOptionPane.showInputDialog("Gefunden an Zeilen \n"+ lines +".\nVersuchen Sie es nochmals. Ihre anfrage?", query);
            }
        }

        System.out.println("Finished.");
    }

    private static String readLineFromFile(int lineNumber) throws IOException {
        File file = new File(new File("data"),"Complete-Shakespeare.txt");
        BufferedReader bIn = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
        String line = "";
        for(int i=0; i<lineNumber; i++) line = bIn.readLine();
        return line;
    }
}

