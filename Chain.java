import java.io.*;
import java.util.*;


class Chain {
	static final int NPREF = 2;	// プレフィックスの長さ
	static final String NONWORD = "\n"; // 現れない単語
	Hashtable<Prefix,Vector<String>> statetab = new Hashtable<Prefix,Vector<String>>();
					// キーはプレフィックス, 値はサフィックスのVector<String>
	Prefix prefix = new Prefix(NPREF, NONWORD); // 初期のプレフィックス
	Random rand = new Random();

	// Chain build: 入力のストリームから状態テーブルを作成する
	void build(InputStream in) throws IOException
	{
		// StreamTokenizer は Java1.8 では非推奨である
		// StreamTokenizerk から String.split を利用する形に変更
		String line = convertInputStreamToString(in);
		String delims = " ";

		String[] tokens = line.split(delims);
		int tokenCount = tokens.length;
		for (int i=0; i < tokenCount; i++) {
			add(tokens[i]);
		}

		add(NONWORD);
	}

	// 追加したメソッド
	// Chain convertInputStreamToString: InputStream を String型に変換する
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

	// Chain add: サフィックスのリストに単語を追加する
	void add(String word)
	{
		// 非推奨な記述を修正
		Vector<String> suf = statetab.get(prefix);
		if (suf == null) {
			suf = new Vector<String>();
			statetab.put(new Prefix(prefix), suf);
		}
		suf.addElement(word);
		prefix.pref.removeElementAt(0);
		prefix.pref.addElement(word);
	}

	// Chain generate: 出力の単語を生成
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
