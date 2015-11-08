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
		int nwords = MAXGEN;
		boolean input_from_file = false;
		int prefix_length = 3;
		String file_name = "";

		for (int i=0; i<args.length; i++) {
            if ("-f".equals(args[i])) {
				i++;
                file_name = args[i];
				input_from_file = true;
            } else if ("-p".equals(args[i])) {
				i++;
                prefix_length = Integer.parseInt(args[i]);
            } else {
	        System.err.println("未定義の引数を検知しました．s");
            }
        }

		Chain chain = new Chain(prefix_length);

		if(input_from_file) {
			String seed = loadFromFile(file_name);
			chain.build(seed);
		}else {
			chain.build(System.in);
		}
		chain.generate(nwords);
	}

	public static String loadFromFile(String name) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(name));
		String seed = "";
        String line = "";

		while ((line = br.readLine()) != null) {
			seed = seed + line;
		}
		br.close();
		return seed;
	}
}
