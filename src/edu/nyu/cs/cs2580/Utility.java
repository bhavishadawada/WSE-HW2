package edu.nyu.cs.cs2580;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This class contains all utility methods
 * @author bdawada
 *
 */
public class Utility {
	// This can then help removing stop words
	public static Set<String> tokenize(String document){
		Set<String> uniqueTermSet = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(document);
		while(st.hasMoreTokens()){
			uniqueTermSet.add(st.nextToken());
		}
		return uniqueTermSet;
	}
	
	public static void main(String[] args) {
		String doc = "This is to test set to";
		tokenize(doc);
	}
}

