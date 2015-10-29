/* Copyright (C) 1999 Lucent Technologies */
/* Excerpted from 'The Practice of Programming' */
/* by Brian W. Kernighan and Rob Pike */

// http://www.cs.princeton.edu/~bwk/tpop.webpage/Markov.java

// このコードはJava 1.8で動作するようhim0によって修正されています。

import java.io.*;
import java.util.*;


class Markov {
	static final int MAXGEN = 10000; // 生成される単語の最大長
	public static void main(String[] args) throws IOException
	{
		Chain chain = new Chain();
		int nwords = MAXGEN;

		chain.build(System.in);
		chain.generate(nwords);
	}
}
