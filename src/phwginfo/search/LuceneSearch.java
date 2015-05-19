package phwginfo.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;

import javax.swing.*;
import java.io.File;

public class LuceneSearch {

    public static void main(String[] args) throws Exception {
        File indexDir = new File("luceneIndex");
        System.out.println("Reading index in " + indexDir);
        Directory index = new MMapDirectory(indexDir);
        IndexReader reader = IndexReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser qP = new QueryParser(Version.LUCENE_40, "text", new StandardAnalyzer(Version.LUCENE_40));

        String query;
        StringBuffer results = new StringBuffer("noch kein Ergebnis");
        while ((query = JOptionPane.showInputDialog("Welches Text suchen wir?\n" + results, "To be or"))!=null) {
            TopDocs topDocs = searcher.search(qP.parse(query), 20);
            for(ScoreDoc sdoc: topDocs.scoreDocs) {
                Document doc = reader.document(sdoc.doc);
                results.append(doc.get("text")).append('\n');
            }
        }
    }

}
