import java.io.*;
import java.util.*;


class Chain {
	int NPREF = 1;	// プレフィックスの長さ
	static final String NONWORD = "\n"; // 現れない単語
	Hashtable<Prefix,Vector<String>> statetab = new Hashtable<Prefix,Vector<String>>();
					// キーはプレフィックス, 値はサフィックスのVector<String>
	Prefix prefix = new Prefix(NPREF, NONWORD); // 初期のプレフィックス
	Random rand = new Random();

	public Chain(int prefix_length) {
		NPREF = prefix_length;
		prefix = new Prefix(NPREF, NONWORD);
	}

	// Chain build: 入力のストリームで受け取った場合 String 型に変換
	void build(InputStream is) throws IOException
	{
		// StreamTokenizer は Java1.8 では非推奨である
		// StreamTokenizerk から String.split を利用する形に変更
		InputStreamReader reader = new InputStreamReader(is);
	    StringBuilder builder = new StringBuilder();
	    char[] buf = new char[1024];
	    int numRead;

		while (0 <= (numRead = reader.read(buf))) {
	        builder.append(buf, 0, numRead);
	    }
		build(builder.toString());
	}

	// Chain build: Stringで受け取った場合 状態テーブルを作成
	void build(String seed) {
		String delims = " ";

		String[] tokens = seed.split(delims);
		int tokenCount = tokens.length;
		for (int i=0; i < tokenCount; i++) {
			add(tokens[i]);
		}

		add(NONWORD);
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
