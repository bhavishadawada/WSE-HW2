package edu.nyu.cs.cs2580;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import edu.nyu.cs.cs2580.SearchEngine.Options;

/**
 * @CS2580: Implement this class for HW2.
 */

// check if this should implement serializable
public class IndexerInvertedDoconly extends Indexer {

	// Data structure to maintain unique terms with id
	private Map<String, Integer> _dictionary;

	// Data structure to store number of times a term occurs in Document
	private Map<String,Integer> _documentTermFrequency;
	// Data structure to store number of times a term occurs in the complete Corpus
	private Map<String, Integer> _corpusTermFrequency;
	// Data structure to store unique terms in the document
	//private Vector<String> _terms = new Vector<String>();

	// Stores all Document in memory.
	private List<Document> _documents;
	private Map<Character, Map<String, List<Integer>>> _characterMap;

	public IndexerInvertedDoconly(Options options) {
		super(options);
		_documentTermFrequency = new HashMap<String,Integer>();
		_corpusTermFrequency = new HashMap<String,Integer>();
		_documents = new ArrayList<Document>();
		_characterMap = new HashMap<Character, Map<String, List<Integer>>>();
		System.out.println("Using Indexer: " + this.getClass().getSimpleName());
	}

	@Override
	public void constructIndex() throws IOException {
		String corpusFile = _options._corpusPrefix + "/corpus.tsv";
		System.out.println("Construct index from: " + corpusFile);

		BufferedReader reader = new BufferedReader(new FileReader(corpusFile));
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				processDocument(line);
			}
		} finally {
			reader.close();
		}
	}

	private void processDocument(String content) {
		// The problem is this will include num_views also
		Scanner s = new Scanner(content).useDelimiter("\t");
		int docId = _numDocs;
		DocumentIndexed doc = new DocumentIndexed(docId);
		String title = s.next();
		Set<String> uniqueTermSetTitle = Utility.tokenize(title);
		buildMapFromTokens(uniqueTermSetTitle,docId);
		doc.setTitle(title);
		// set the url here
		Set<String> uniqueTermSetBody = Utility.tokenize(s.next());
		buildMapFromTokens(uniqueTermSetBody,docId);
		// write to the file here based on some condition
		// so that it does not create out of bound memory
		
		++ _numDocs;
	}

	private void buildMapFromTokens(Set<String> uniqueTermSet, int docId){
		for(String token: uniqueTermSet){
			if(_corpusTermFrequency.containsKey(token)){
				int value = _corpusTermFrequency.get(token) + 1;
				_corpusTermFrequency.put(token, value);
			}
			else{
				_corpusTermFrequency.put(token, 1);
			}

			char start = token.charAt(0);
			if (_characterMap.containsKey(start)) {
				Map<String, List<Integer>> wordMap = _characterMap
						.get(start);
				if (wordMap.containsKey(token)) {
					List<Integer> docList = wordMap.get(token);
					if(!docList.contains(docId)){
						docList.add(docId);
					}

				}
				else{
					List<Integer> tempDocList = new ArrayList<Integer>();
					tempDocList.add(docId);
					wordMap.put(token, tempDocList);
				}
			}else{
				// else for if not characterMap
				Map<String, List<Integer>> tempMap = new HashMap<String, List<Integer>>();
				List<Integer> tempList = new ArrayList<Integer>();
				tempList.add(docId);
				tempMap.put(token,tempList);
				_characterMap.put(start,tempMap);		
			}
		}
	}

	// This is used when the SearchEngine is called with the serve option
	@Override
	public void loadIndex() throws IOException, ClassNotFoundException {
	}

	@Override
	public Document getDoc(int docid) {
		return null;
	}

	/**
	 * In HW2, you should be using {@link DocumentIndexed}
	 */

	//TODO: This is to be implemented as discussed in class?????
	@Override
	public Document nextDoc(Query query, int docid) {
		return null;
	}

	// number of documents the term occurs in
	@Override
	public int corpusDocFrequencyByTerm(String term) {
		return _dictionary.containsKey(term) ?
				_termDocFrequency.get(_dictionary.get(term)) : 0;
	}

	//number of times a term appears in corpus
	@Override
	public int corpusTermFrequency(String term) {
		return _dictionary.containsKey(term) ?
				_termCorpusFrequency.get(_dictionary.get(term)) : 0;
	}

	// number of times a term occurs in document
	@Override
	public int documentTermFrequency(String term, String url) {
		SearchEngine.Check(false, "Not implemented!");
		return 0;
	}
}
