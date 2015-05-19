package phwginfo.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class IndexNode implements Serializable {

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