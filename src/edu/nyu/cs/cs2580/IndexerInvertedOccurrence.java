package edu.nyu.cs.cs2580;

import java.io.IOException;
import java.util.Vector;

import edu.nyu.cs.cs2580.SearchEngine.Options;

/**
 * @CS2580: Implement this class for HW2.
 */


public class IndexerInvertedOccurrence extends Indexer {
	 
  // Stores all Document in memory.
  private Vector<Document> _documents = new Vector<Document>();
	  
  public IndexerInvertedOccurrence(Options options) {
    super(options);
    System.out.println("Using Indexer: " + this.getClass().getSimpleName());
  }

  @Override
  public void constructIndex() throws IOException {
	  String corpusFile = _options._corpusPrefix;
	  DocProcessor dp = new DocProcessor(corpusFile);
	  while(dp.hasNextDoc()){
		  dp.nextDoc();
		  processDocument(dp.title, dp.body);
	  }
  }
  
  private void processDocument(String title, String body){
	  Document doc = new Document(_documents.size());
	  doc.setTitle(title);
	  _documents.add(doc);
	  ++_numDocs;
	  System.out.println(title);
  }

  @Override
  public void loadIndex() throws IOException, ClassNotFoundException {
  }

  @Override
  public Document getDoc(int docid) {
    return null;
  }

  /**
   * In HW2, you should be using {@link DocumentIndexed}.
   */
  @Override
  public Document nextDoc(Query query, int docid) {
    return null;
  }

  @Override
  public int corpusDocFrequencyByTerm(String term) {
    return 0;
  }

  @Override
  public int corpusTermFrequency(String term) {
    return 0;
  }

  @Override
  public int documentTermFrequency(String term, String url) {
    SearchEngine.Check(false, "Not implemented!");
    return 0;
  }
}
