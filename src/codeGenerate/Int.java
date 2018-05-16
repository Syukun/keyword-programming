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
	
	public static Vector<ExpressionPlusType> generateMaxScoreT(int depth, String keywords){
		Vector<Expression> iV = Int.generate(depth);
		Vector<ExpressionPlusType> expsMaxScoreTV = new Vector<ExpressionPlusType>();
		Vector<Expression> iVMax = Int.generateMaxScoreExps(keywords, iV);
		ExpressionPlusType expMaxScoreT = new ExpressionPlusType("Int",iVMax);
		expsMaxScoreTV.add(expMaxScoreT);
		return expsMaxScoreTV;
	}
	
	public static Vector<Expression> generateMaxScoreExps(String keywords,Vector<Expression> exps){
		Vector<Expression> expsMaxSco = new Vector<Expression>();
		int SIZE = exps.size();
		Expression expInit = exps.get(0);
		expsMaxSco.add(expInit);
		float maxScore = expInit.score(keywords);
		for(int i=1 ; i < SIZE ; i++) {
			Expression expI = exps.get(i);
			float currentScore = expI.score(keywords);
			if(currentScore > maxScore) {
				maxScore = currentScore;
				expsMaxSco.clear();
				expsMaxSco.add(expI);
			}else if(currentScore == maxScore) {
				expsMaxSco.add(expI);
			}
		}
		
		return expsMaxSco;
	}
	
	// getDepth
	public int getDepth() {
		return 1;
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