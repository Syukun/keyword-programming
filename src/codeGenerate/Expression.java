package codeGenerate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

public abstract class Expression {
	// default score is -0.05
	public static final float DEFSCORE = -0.05f;
	// add 1.00 when words in keywords query
	public static final float WIK = 1.00f;
	// subtract 0.01 when words not in keywords query
	public static final float WNIK = 0.01f;
	// add +0.001 where f is a local variable , member variable or member method
	// of the enclosing class;
	public static final float LMVAR = 0.001f;
	// decide whether the word in keywordList is used
	public static boolean used = false;

	// generate Vector of Expression which has duplicated ones.
	public static Vector<Expression> generate(int depth) {
		Vector<Expression> exps = new Vector<Expression>();
		if (depth == 1) {
			exps.addAll(Int.generate(depth));
			exps.addAll(Var.generate(depth));
		} else {
			exps.addAll(Expression.generate(depth - 1));
			exps.addAll(MethodInv.generate(depth));
			// exps.addAll(BinOp.generate(depth));
		}
		return exps;
	}
	

	// score function
	public float score(String keywords) {
		List<KeywordFlag> keywordListFlag = new KeywordFlag().toKeywordFlag(keywords);
		return score(keywordListFlag);
	}

	abstract public float score(List<KeywordFlag> keywordListFlag);

	// toString function : calls toString Method in each subclass
	abstract public String toString();

	// type function return the type of an expression
	abstract public Type getType();

	// expression which has max score (only one)
	public static Expression maxScoreExpression(int depth, String keywords) {
		Vector<Expression> expsV = generate(depth);
		Expression maxScoExp = expsV.get(0);
		float maxScore = maxScoExp.score(keywords);
		for (int i = 1; i < expsV.size(); i++) {
			float currentScore = expsV.get(i).score(keywords);
			if (currentScore > maxScore) {
				maxScore = currentScore;
				maxScoExp = expsV.get(i);
			}

		}
		return maxScoExp;
	}

	// all expressions with max score
	// maybe can use sort method in java.Steam to choose the methods which have
	// the max score
	public static Vector<Expression> maxScoreExpressions(int depth, String keywords) {
		Vector<Expression> expsV = generate(depth);
		Vector<Expression> maxScoExpsV = new Vector<Expression>();
		float maxScore = expsV.get(0).score(keywords);
		maxScoExpsV.add(expsV.get(0));
		for (int i = 1; i < expsV.size(); i++) {
			float currentScore = expsV.get(i).score(keywords);
			if (currentScore > maxScore) {
				maxScore = currentScore;
				maxScoExpsV.clear();
				maxScoExpsV.add(expsV.get(i));
			} else if (currentScore == maxScore) {
				maxScoExpsV.add(expsV.get(i));
			}
		}
		return maxScoExpsV;
	}

	// decide whether the word is in a keywordList and whether the corresponding
	// keyword
	// has been used before.
	public boolean isInKeywordListFlag(String word, List<KeywordFlag> keywordListFlag) {
		boolean isInKeywordList = false;

		for (int i = 0; i < keywordListFlag.size(); i++) {
			if (keywordListFlag.get(i).getKeyword().contains(word.toLowerCase())) {
				if (!keywordListFlag.get(i).isFlag()) {
					keywordListFlag.get(i).setFlag(true);
				} else {
					used = true;
				}

				isInKeywordList = true;
			}

		}

		return isInKeywordList;
	}

	// define add function with using BigDecimal class
	public float addPrecise(float num1, float num2) {
		return new BigDecimal(Float.toString(num1)).add(new BigDecimal(Float.toString(num2))).floatValue();
	}
	// eliminate duplicated expressions
		// getDepth : expression -> number
		// usage : return #methodname of the max depth of expression
		// Example (just shape)
		// (Int|Var).numMethod = 0
		// (e0.Mname(e1...en)).numMethod = 1 + (max (e0.numMethod)....)
	abstract public int getDepth();

}