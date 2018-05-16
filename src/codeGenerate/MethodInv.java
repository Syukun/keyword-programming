package codeGenerate;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

class MethodInv extends Expression {
	private Expression expFront;
	private MName mName;
	private Expression[] expBack;

	// constructor
	public MethodInv(Expression expFront, MName mName, Expression[] expBack) {
		this.setExpFront(expFront);
		this.setmName(mName);
		this.setExpBack(expBack);
	}

	// giving the example of lines.add(pointOne,pointTwo);

	// generate Method without duplicating
	public static Vector<Expression> generate(int depth) {
		// all possible methods generated in depth
		Vector<Expression> methodsOfDep = new Vector<Expression>();
		// all possible methods generated in depth minus one
		Vector<Expression> methodsOfDepM1 = Expression.generate(depth - 1);
		// all method names
		Vector<MName> allMName = MName.allMethodName();
		if (depth < 2) {
		} else {
			// mname is an element of all Method Names
			for (MName mname : allMName) {
				// expOfDepM1 is an element of all expressions generated from depth minus one
				for (Expression expOfDepM1 : methodsOfDepM1) {
					// DUPLICATED : decide whether there is at least one expression in exact depth
					// minus one if all of them are not then return true;
					boolean DUPLICATED = true;
					if (mname.matchReceiverType(expOfDepM1.getType())) {
						if (expOfDepM1.getDepth() == (depth - 1)) {
							DUPLICATED = false;
						}
						// vector of expressions of all expressions including receiver and parameter
						// expressions
						Vector<DuplicateFlag> expsDF_V = new Vector<DuplicateFlag>();
						expsDF_V.add(new DuplicateFlag(DUPLICATED, expOfDepM1, new Expression[0]));
						Vector<DuplicateFlag> expsAllParaDF_V = generateParameterExps(expsDF_V, mname, methodsOfDepM1,
								1, depth);
						for (DuplicateFlag expsAllParaDF : expsAllParaDF_V) {
							if (!expsAllParaDF.isFlag()) {
								MethodInv methodInvOfDep = new MethodInv(expsAllParaDF.getExpR(), mname,
										expsAllParaDF.getExpsP());
								methodsOfDep.add(methodInvOfDep);
							}

						}
					}

				}
			}
		}

		return methodsOfDep;
	}

	// generate all possible parameter expressions with decide whether it is a
	// duplucated expressions
	// which rank-th of mname's parameter is settled
	// return result until rank equals method name's #parameter
	private static Vector<DuplicateFlag> generateParameterExps(Vector<DuplicateFlag> expsDF_V, MName mname,
			Vector<Expression> methodsOfDepM1, int rank, int depth) {

		if (rank == mname.getParameterType().length) {
			return expsDF_V;
		} else {
			Vector<DuplicateFlag> expsDF_V_auxi = new Vector<DuplicateFlag>();
			// Expression[] -> Vector<Expression[]>
			for (DuplicateFlag expOfAllParaDF : expsDF_V) {
				expsDF_V_auxi.addAll(addForeach(expOfAllParaDF, mname, methodsOfDepM1, rank, depth));
			}
			return generateParameterExps(expsDF_V_auxi, mname, methodsOfDepM1, rank + 1, depth);
		}
	}

	public static Vector<DuplicateFlag> addForeach(DuplicateFlag expOfAllParaF, MName mname,
			Vector<Expression> methodsOfDepM1, int rank, int depth) {
		Vector<DuplicateFlag> expParDF_V = new Vector<DuplicateFlag>();
		int paraLength = expOfAllParaF.getExpsP().length;
		for (Expression exp : methodsOfDepM1) {
			if (mname.matchType(exp.getType(), rank)) {
				Expression[] expPara = new Expression[paraLength + 1];
				for (int i = 0; i < paraLength; i++) {
					expPara[i] = expOfAllParaF.getExpsP()[i];
				}
				expPara[paraLength] = exp;
				boolean DF_Old = expOfAllParaF.isFlag();
				Expression expR = expOfAllParaF.getExpR();
				boolean DF_Con = !(exp.getDepth() == (depth - 1));
				if(DF_Old) {
					expParDF_V.add(new DuplicateFlag(DF_Con,expR,expPara));
				}else {
					expParDF_V.add(new DuplicateFlag(DF_Old,expR,expPara));
				}
			}
		}
		return expParDF_V;

	}

	// score function
	public float score(List<KeywordFlag> keywordListFlag) {

		float score = 0;
		float scoreFront = this.getExpFront().score(keywordListFlag);
		float scoreMName = this.getmName().score(keywordListFlag);
		float scoreBack = allExpressionBackScore(keywordListFlag);

		score = addPrecise(addPrecise(scoreFront, scoreMName), scoreBack);
		return score;
	}

	@Override
	public Type getType() {

		return this.mName.getReturnType();
	}

	// return sum score of all ExpressionBack
	private float allExpressionBackScore(List<KeywordFlag> keywordListFlag) {
		// maybe could be solved by lambda
		float score = 0;
		Expression[] mthInvExpressionBack = this.getExpBack();
		for (int i = 0; i < mthInvExpressionBack.length; i++) {
			score += mthInvExpressionBack[i].score(keywordListFlag);
		}
		return score;
	}

	// getDepth
	public int getDepth() {
		return 1 + this.maxNum(this.getExpFront(), this.getExpBack());
	}

	public int maxNum(Expression expF, Expression[] expsB) {
		int depthOfExpF = expF.getDepth();
		for (int i = 0; i < expsB.length; i++) {
			int depthOfExpFI = expsB[i].getDepth();
			if (depthOfExpF < depthOfExpFI) {
				depthOfExpF = depthOfExpFI;
			}
		}
		return depthOfExpF;
	}

	// to String()
	public String toString() {
		return getExpFront() + "." + getmName() + "(" + expBack2String() + ")";
	}

	// translate an array of expression to string
	private String expBack2String() {
		if (getExpBack().length == 0) {
			return "";
		} else {
			String expBack2Str = getExpBack()[0] + "";
			for (int i = 1; i < getExpBack().length; i++) {
				expBack2Str += "," + getExpBack()[i];
			}
			return expBack2Str;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodInv other = (MethodInv) obj;
		if (!Arrays.equals(getExpBack(), other.getExpBack()))
			return false;
		if (getExpFront() == null) {
			if (other.getExpFront() != null)
				return false;
		} else if (!getExpFront().equals(other.getExpFront()))
			return false;
		return true;
	}

	public MName getmName() {
		return mName;
	}

	public void setmName(MName mName) {
		this.mName = mName;
	}

	public Expression getExpFront() {
		return expFront;
	}

	public void setExpFront(Expression expFront) {
		this.expFront = expFront;
	}

	public Expression[] getExpBack() {
		return expBack;
	}

	public void setExpBack(Expression[] expBack) {
		this.expBack = expBack;
	}

}