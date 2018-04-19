package codeGenerate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

class Operator {
	private String operator;

	// constructor
	public Operator(String operator) {
		this.operator = operator;
	}

	public float score(List<KeywordFlag> keywordListFlag) {

		float score = Expression.DEFSCORE;

		if (this.isInKeywordListFlag(keywordListFlag)) {
			if (!Expression.used) {
				score = new BigDecimal(Float.toString(score)).add(new BigDecimal(Float.toString(Expression.WIK)))
						.floatValue();
			} else {
				Expression.used = false;
			}

		} else {
			score = new BigDecimal(Float.toString(score)).subtract(new BigDecimal(Float.toString(Expression.WNIK)))
					.floatValue();
		}

		return score;
	}

	// decide whether the expression is in a keywordList and whether the word
	// has been used before.
	public boolean isInKeywordListFlag(List<KeywordFlag> keywordListFlag) {
		for (int i = 0; i < keywordListFlag.size(); i++) {
			if (this.toString().equalsIgnoreCase(keywordListFlag.get(i).getKeyword())) {
				if (!keywordListFlag.get(i).isFlag()) {
					keywordListFlag.get(i).setFlag(true);
					Expression.used = false;
				} else {
					Expression.used = true;
				}

				return true;
			}

		}

		return false;
	}

	public static Vector<Operator> allOperator() {
		Vector<Operator> allOpeV = new Vector<Operator>();
		allOpeV.add(new Operator("+"));
		allOpeV.add(new Operator("-"));
		// allOpeV.add(new Operator("%"));
		return allOpeV;
	}

	@Override
	public String toString() {
		return operator;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}

}