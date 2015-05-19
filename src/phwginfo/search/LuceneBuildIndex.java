package phwginfo.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LuceneBuildIndex {

    public static void main(String[] args) throws Exception {
        File indexDir = new File("luceneIndex");
        if(!indexDir.isDirectory()) indexDir.mkdir();
        System.out.println("Will write index in " + indexDir);
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        Directory index = new MMapDirectory(indexDir);
        IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig(Version.LUCENE_40, analyzer));
        indexWriter.deleteAll();

        File textFile = new File(new File("data"),"Complete-Shakespeare.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(textFile),"utf-8"));
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine())!=null) {
            lineNumber++;
            Document doc = new Document();
            doc.add(new TextField("text", line, Field.Store.YES));
            doc.add(new IntField("line", lineNumber, Field.Store.YES));
            indexWriter.addDocument(doc);
        }

        System.out.println("Index built.");
        indexWriter.commit();
        indexWriter.close();
    }

}
