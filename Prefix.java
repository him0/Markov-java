import java.io.*;
import java.util.*;


class Prefix {
	public Vector<String> pref;	// NPREF adjacent words from input
	static final int MULTIPLIER = 31;	// for hashCode()

	// Prefix constructor: duplicate existing prefix
    @SuppressWarnings("unchecked") // Added to make the warning silent
    Prefix(Prefix p)
	{
        // Warnig about casting the Object-typed Object to the Generic-typed Object
        // http://www.profaim.jp/lang-ref/java/generics/cast.php
        // http://stackoverflow.com/questions/4357297/use-of-vectors-in-java

		// Fix the un-recommended description
        pref = (Vector<String>) p.pref.clone();
	}

	// Prefix constructor: n copies of str
	Prefix(int n, String str)
	{
		// Fix the un-recommended description
		pref = new Vector<String>();
		for (int i = 0; i < n; i++)
			pref.addElement(str);
	}

	// Prefix hashCode: generate hash from all prefix words
	public int hashCode()
	{
		int h = 0;

		for (int i = 0; i < pref.size(); i++)
			h = MULTIPLIER * h + pref.elementAt(i).hashCode();
		return h;
	}

	// Prefix equals: compare two prefixes for equal words
	public boolean equals(Object o)
	{
		Prefix p = (Prefix) o;

		for (int i = 0; i < pref.size(); i++)
			if (!pref.elementAt(i).equals(p.pref.elementAt(i)))
				return false;
		return true;
	}

}
