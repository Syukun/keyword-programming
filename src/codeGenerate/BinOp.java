package codeGenerate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

// class BinOp
class BinOp extends Expression {
	private Expression binOpExpFront;
	private Expression binopExpBack;
	public BinOp binExpression;
	private Operator operator;

	// Constructor
	public BinOp(Expression binOpExpFront, Operator operator, Expression binopExpBack) {
		this.setBinOpExpFront(binOpExpFront);
		this.operator = operator;
		this.setBinopExpBack(binopExpBack);
	}

	// generate Function
	public static Vector<Expression> generateDep(int depth) {
		Vector<Expression> binOpV = new Vector<Expression>();
		Vector<Operator> allOp = Operator.allOperator();
		Vector<Expression> expInt = Int.generate(1);

		if (depth == 1) {
			binOpV.addAll(expInt);
		} else if (depth == 2) {

			for (int i = 0; i < expInt.size(); i++) {
				for (int j = 0; j < allOp.size(); j++) {
					for (int k = 0; k < expInt.size(); k++) {
						binOpV.add(new BinOp(expInt.get(i), allOp.get(j), expInt.get(k)));
					}
				}
			}
		} else {
			// all BinOperator Expressions in depth of Depth minus one
			Vector<Expression> binOpDepM1V = BinOp.generateDep(depth - 1);
			// all BinOperator Expressions under depth of Depth minus one
			Vector<Expression> binOpAllM1V = BinOp.generate(depth - 1);
			// all BinOperator Expressions under depth of Depth minus two
			Vector<Expression> binOpAllM2V = BinOp.generate(depth - 2);
			for (int i = 0; i < binOpDepM1V.size(); i++) {
				for (int j = 0; j < allOp.size(); j++) {

					for (int k = 0; k < binOpAllM1V.size(); k++) {
						// binOperator Expression which depth of
						// BinOpExpFront part is #depth minus one
						BinOp binOpFrontIsMinusOne = new BinOp(binOpDepM1V.get(i), allOp.get(j), binOpAllM1V.get(k));
						binOpV.add(binOpFrontIsMinusOne);
					}
					for (int m = 0; m < binOpAllM2V.size(); m++) {
						// binOperator Expression which depth of
						// BinOpExpBack part is #depth minus one
						BinOp binOpBackIsMinusOne = new BinOp(binOpAllM2V.get(m), allOp.get(j), binOpDepM1V.get(i));
						binOpV.addElement(binOpBackIsMinusOne);
					}

				}
			}
		}
		return binOpV;

	}

	// score function
	public float score(List<KeywordFlag> keywordListFlag) {
		float scoreBinFront = this.getBinOpExpFront().score(keywordListFlag);
		float scoreBinBack = this.getBinopExpBack().score(keywordListFlag);
		float scoreOperator = this.getOperator().score(keywordListFlag);

		float score = new BigDecimal(Float.toString(DEFSCORE)).add(new BigDecimal(Float.toString(scoreBinFront)))
				.add(new BigDecimal(Float.toString(scoreOperator))).add(new BigDecimal(Float.toString(scoreBinBack)))
				.floatValue();
		return score;
	}

	/*
	 * toString Method e.g new BinOp(new Int(1),new Operator("+"),new Int(2)) ===>
	 * "1+2" this could be improved later
	 */
	public String toString() {
		return "" + getBinOpExpFront() + operator + getBinopExpBack();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinOp other = (BinOp) obj;
		if (getBinOpExpFront() == null) {
			if (other.getBinOpExpFront() != null)
				return false;
		} else if (!getBinOpExpFront().equals(other.getBinOpExpFront()))
			return false;
		if (getBinopExpBack() == null) {
			if (other.getBinopExpBack() != null)
				return false;
		} else if (!getBinopExpBack().equals(other.getBinopExpBack()))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}

	public Expression getBinOpExpFront() {
		return binOpExpFront;
	}

	public void setBinOpExpFront(Expression binOpExpFront) {
		this.binOpExpFront = binOpExpFront;
	}

	public Expression getBinopExpBack() {
		return binopExpBack;
	}

	public void setBinopExpBack(Expression binopExpBack) {
		this.binopExpBack = binopExpBack;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return new Type("Integer");
	}

	@Override
	public int getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}