import java.io.*;
import java.util.*;


class Chain {
	static final int NPREF = 2;	// size of prefix
	static final String NONWORD = "\n";
					// "word" that can't appear
	Hashtable<Prefix,Vector<String>> statetab = new Hashtable<Prefix,Vector<String>>();
					// key = Prefix, value = suffix Vector
	Prefix prefix = new Prefix(NPREF, NONWORD);
					// initial prefix
	Random rand = new Random();

	// Chain build: build State table from input stream
	void build(InputStream in) throws IOException
	{
		// StreamTokenizer is not recommended in Java version 1.8
		/*
		StreamTokenizer st = new StreamTokenizer(in);

		st.resetSyntax();                     // remove default rules
		st.wordChars(0, Character.MAX_VALUE); // turn on all chars
		st.whitespaceChars(0, ' ');           // except up to blank
		while (st.nextToken() != st.TT_EOF)
			add(st.sval);
		*/

		// replace the StreamTokenizer to String.split
		String line = convertInputStreamToString(in);
		String delims = " ";

		String[] tokens = line.split(delims);
		int tokenCount = tokens.length;
		for (int i=0; i < tokenCount; i++) {
			add(tokens[i]);
		}

		add(NONWORD);
	}

	// Add this method to Original Chain Class
	// Chain convertInputStreamToString: convert InputStream to String
	static String convertInputStreamToString(InputStream is) throws IOException {
	    InputStreamReader reader = new InputStreamReader(is);
	    StringBuilder builder = new StringBuilder();
	    char[] buf = new char[1024];
	    int numRead;

		while (0 <= (numRead = reader.read(buf))) {
	        builder.append(buf, 0, numRead);
	    }
	    return builder.toString();
	}

	// Chain add: add word to suffix list, update prefix
	void add(String word)
	{
		// Fix the un-recommended description
		Vector<String> suf = statetab.get(prefix);
		if (suf == null) {
			suf = new Vector<String>();
			statetab.put(new Prefix(prefix), suf);
		}
		suf.addElement(word);
		prefix.pref.removeElementAt(0);
		prefix.pref.addElement(word);
	}

	// Chain generate: generate output words
	void generate(int nwords)
	{
		prefix = new Prefix(NPREF, NONWORD);
		for (int i = 0; i < nwords; i++) {
			Vector<String> s = statetab.get(prefix);
			if (s == null) {
				System.err.println("Markov: internal error: no state");
				System.exit(1);
			}
			int r = Math.abs(rand.nextInt()) % s.size();
			String suf = (String) s.elementAt(r);
			if (suf.equals(NONWORD))
				break;
			System.out.println(suf);
			prefix.pref.removeElementAt(0);
			prefix.pref.addElement(suf);
		}
	}
}
