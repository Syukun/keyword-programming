package codeGenerate;

import java.math.BigDecimal;
import java.util.ArrayList;
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

	// generate Method with duplicating
	public static Vector<Expression> generate(int depth) {
		Vector<Expression> methodsOfDep = new Vector<Expression>();
		Vector<Expression> methodsOfDepM1 = Expression.generate(depth - 1);
		Vector<MName> allMName = MName.allMethodName();
		if (depth < 2) {
		} else {
			// mname is an element of all Method Names
			for (MName mname : allMName) {
				// expOfDepM1 is an element of all expressions generated from depth minus one
				for (Expression expOfDepM1 : methodsOfDepM1) {
					if (mname.matchReceiverType(expOfDepM1.getType())) {
						// vector of all parameter expressions
						// MName * Vector<Expression[]> * Int -> Vector<Expression[]>
						Vector<Expression[]> expsParaV = new Vector<Expression[]>();
						expsParaV.add(new Expression[0]);
						Vector<Expression[]> expsParameterV = generateParameterExps(expsParaV,mname, methodsOfDepM1, 1);
						for (Expression[] expParameter : expsParameterV) {
							MethodInv methodInvOfDep = new MethodInv(expOfDepM1, mname, expParameter);
							methodsOfDep.add(methodInvOfDep);
						}
					}

				}
			}
		}

		return methodsOfDep;
	}

//	generate all possible parameter expressions
//	which rank-th of mname's parameter is settled
	private static Vector<Expression[]> generateParameterExps(Vector<Expression[]> expsParaV
			,MName mname, Vector<Expression> methodsOfDepM1, int rank) {

		if(rank == mname.getParameterType().length) {
			return expsParaV;
		}else {
			Vector<Expression[]> expParaV_auxi = new Vector<Expression[]>();
			for(Expression[] expPara : expsParaV) {
				expParaV_auxi.addAll(addForeach(expPara,mname,methodsOfDepM1,rank));
			}
			return generateParameterExps(expParaV_auxi,mname,methodsOfDepM1,rank+1);
		}
	}
//	Expression[] -> Vector<Expression[]>
	public static Vector<Expression[]> addForeach(Expression[] expParaM1,
			MName mname,Vector<Expression> methodsOfDepM1,int rank){
		Vector<Expression[]> expParV = new Vector<Expression[]>();
		int paraLength = expParaM1.length;
		for(Expression exp : methodsOfDepM1) {
			if(mname.matchType(exp.getType(), rank)) {
				Expression[] expPara= new Expression[paraLength + 1];
				for(int i = 0 ; i < paraLength;i++) {
					expPara[i] = expParaM1[i];
				}
				expPara[paraLength] = exp;
				expParV.add(expPara);
			}
		}
		return expParV;
		
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

	// to String()
	public String toString() {
		return getExpFront() + "." + getmName() + "(" + expBack2String() + ")";
	}

	// translate an array of expression to string
	private String expBack2String() {
		if (getExpBack().length == 0) {
			return "";
		}else {
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