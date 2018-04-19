package codeGenerate;

import java.util.List;
import java.util.Vector;

//class Int
class Int extends Expression {
	private int i;

	// Constructor
	public Int(int i) {
		this.i = i;
	}

	// generate Function return integer vector
	public static Vector<Expression> generate(int depth) {
		Vector<Expression> iV = new Vector<Expression>();
		iV.add(new Int(1));
		// iV.add(new Int(2));
		// iV.add(new Int(3));
		// ...
		return iV;
	}

	// score function
	@Override
	public float score(List<KeywordFlag> keywordListFlag) {
		float score = DEFSCORE;
		if (isInKeywordListFlag(this.toString(), keywordListFlag)) {
			if (!Expression.used) {
				score = addPrecise(score, WIK);
			} else {
				Expression.used = false;
			}

		} else {
			score = addPrecise(score, -WNIK);
		}

		return score;
	}

	@Override
	public Type getType() {
		return new Type("Integer");
	}

	public String toString() {
		return "" + i;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Int other = (Int) obj;
		if (i != other.i)
			return false;
		return true;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}