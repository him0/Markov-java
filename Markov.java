/* Copyright (C) 1999 Lucent Technologies */
/* Excerpted from 'The Practice of Programming' */
/* by Brian W. Kernighan and Rob Pike */

// http://www.cs.princeton.edu/~bwk/tpop.webpage/Markov.java

// This code is modified for Java version 1.8 by him0.

import java.io.*;
import java.util.*;


class Markov {
	static final int MAXGEN = 10000; // maximum words generated
	public static void main(String[] args) throws IOException
	{
		Chain chain = new Chain();
		int nwords = MAXGEN;

		chain.build(System.in);
		chain.generate(nwords);
	}
}
