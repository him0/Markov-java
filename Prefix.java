import java.io.*;
import java.util.*;


class Prefix {
	public Vector<String> pref;	// NPREFは入力から単語を調整する
	static final int MULTIPLIER = 31;	// hashCode()で用いる

	// Prefix constructor: 重複し存在するプレフィックス
    @SuppressWarnings("unchecked") // 警告を出さないようにする
    Prefix(Prefix p)
	{
		// Object型のオブジェクトを総称型のオブジェクトに変換するとワーニングが出る
		// 参考
        // http://www.profaim.jp/lang-ref/java/generics/cast.php
        // http://stackoverflow.com/questions/4357297/use-of-vectors-in-java

		// 非推奨な記述を修正
        pref = (Vector<String>) p.pref.clone();
	}

	// Prefix constructor: n copies of str
	Prefix(int n, String str)
	{
		// 非推奨な記述を修正
		pref = new Vector<String>();
		for (int i = 0; i < n; i++)
			pref.addElement(str);
	}

	// Prefix hashCode: すべてのプレフィックス単語からハッシュを生成する
	public int hashCode()
	{
		int h = 0;

		for (int i = 0; i < pref.size(); i++)
			h = MULTIPLIER * h + pref.elementAt(i).hashCode();
		return h;
	}

	// Prefix equals: 同一単語判定のために2つのプレフィックスの比較する
	public boolean equals(Object o)
	{
		Prefix p = (Prefix) o;

		for (int i = 0; i < pref.size(); i++)
			if (!pref.elementAt(i).equals(p.pref.elementAt(i)))
				return false;
		return true;
	}

}
