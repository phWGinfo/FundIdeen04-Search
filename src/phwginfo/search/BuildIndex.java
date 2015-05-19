package phwginfo.search;

import java.io.*;

public class BuildIndex {

    public static void main(String[] args) throws Exception {
        File file = new File(new File("data"),"Complete-Shakespeare.txt");
        System.err.println("Loading from " + file);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int lineNumber = 0;
        IndexNode rootNode = new IndexNode();
        System.err.println("Building index...");
        while((line=reader.readLine())!=null) {
            lineNumber++;
            // in Wörter dekomponieren, also, durch "nicht Wörter Buchstaben"
            // (siehe "predefined classes" an http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html)
            for(String word: line.split("\\W")) {
                IndexNode node = rootNode.findNode(word, 0, true);
                node.references.add(lineNumber);
            }
        }
        System.err.println("Index built, saving...");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("index.ser"));
        out.writeObject(rootNode);
        out.flush(); out.close();
        System.err.println("... saved.");

    }
}
