package phwginfo.search;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class IndexSearch {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File(new File("data"),"Complete-Shakespeare.txt")));
        String line;
        int lineNumber = 0;

        IndexNode rootNode = new IndexNode();
        while((line=reader.readLine())!=null) {
            lineNumber++;
            // in Wörter dekomponieren, also, durch "nicht Wörter Buchstaben"
            // (siehe "predefined classes" an http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html)
            for(String word: line.split("\\W")) {
                IndexNode node = rootNode.findNode(word, 0, true);
                node.references.add(lineNumber);
            }
        }

        String query = JOptionPane.showInputDialog("Welches Text suchen wir?", "To be or");
        while(query!=null && query.length()!=0) {
            if(query==null || query.length()==0) break;
            IndexNode node = rootNode.findNode(query, 0, false);
            if(node==null) {
                query = JOptionPane.showInputDialog("Leider nicht gefunden. Versuchen Sie es nochmals. Ihre anfrage?", query);
            } else {

                query = JOptionPane.showInputDialog("Gefunden an Zeilen "+node.references +".\nVersuchen Sie es nochmals. Ihre anfrage?", query);
            }
        }

        System.out.println("Finished.");
    }
}

class IndexNode {

    char character;
    List references = new ArrayList();
    List<IndexNode> children = new ArrayList<IndexNode>();

    /** Geht durch das Baum, um den Knote für das gegebenes Wort zu finden (möglicherweise gestalten) */
    IndexNode findNode(String text, int posInString, boolean create) {
        if(posInString >= text.length()) {
            // gefunden! Wir sind im guten Objekt, dieses zurückgeben!
            return this;
        }

        char characterToAdd = text.charAt(posInString);

        // Hat der Buchstabe?
        IndexNode foundChild = null;
        for(IndexNode node: children) {
            if(node.character == characterToAdd) {
                foundChild = node;
                break;
            }
        }

        // noch nicht drin?
        if(foundChild==null) {
            if(create) {
                foundChild = new IndexNode();
                foundChild.character = characterToAdd;
                children.add(foundChild);
            } else {
                // nichts hier zu finden
                return null;
            }
        }

        // ein Kind is gefunden, wir suchen weiter
        return foundChild.findNode(text, posInString+1, create);
    }

}