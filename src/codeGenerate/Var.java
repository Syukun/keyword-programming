package codeGenerate;

import java.util.List;
import java.util.Vector;

//class VAR
class Var extends Expression {
	private String v;
	private Type type;

	// Constructor
	public Var(String v, Type type) {
		this.v = v;
		this.type = type;
	}

	// generate Function
	public static Vector<Expression> generate(int depth) {
		Vector<Expression> varV = new Vector<Expression>();
		varV.add(new Var("array", new Type("List<String>")));
		varV.add(new Var("src", new Type("BufferedReader")));
		varV.add(new Var("toString", new Type("String")));
		varV.add(new Var("line",new Type("String")));
		// ...
		return varV;
	}

	// score function for whole word
	public float score(List<KeywordFlag> keywordListFlag) {
		float score = addPrecise(DEFSCORE, LMVAR);
		String[] wordsArray = this.v.split("(?<!^)(?=[A-Z])");
		for (int i = 0; i < wordsArray.length; i++) {
			score = addPrecise(scoreWord(wordsArray[i], keywordListFlag), score);
		}
		return score;
	}

	// score function for a single word
	public float scoreWord(String word, List<KeywordFlag> keywordListFlag) {

		float score = 0;
		if (isInKeywordListFlag(word, keywordListFlag)) {
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
	
	
	public static Vector<ExpressionPlusType> generateMaxScoreT(int depth, String keywords,Vector<String> types){
		Vector<Expression> varV = Var.generate(depth);
		Vector<ExpressionPlusType> expsMaxScoreTV = new Vector<ExpressionPlusType>();
		int SIZE = varV.size();
		for(int i = 0; i < SIZE; i++) {
			Expression varI = varV.get(i);
			String typeI = varI.getType().toString();
			if(types.contains(typeI)) {
				// this should be modified
				//expsMaxScoreTV.transMaxScore(varI,keywords);
				//Var.expsMaxScoreT(varI,expsMaxScoreTV,keywords);
			}else {
				Vector<Expression> varMaxT = new Vector<Expression>();
				varMaxT.add(varI);
				expsMaxScoreTV.add(new ExpressionPlusType(typeI,varMaxT));
				types.add(typeI);
			}
		}
		
		return expsMaxScoreTV;
	}
	
	// this is wrong right now
	public static Vector<ExpressionPlusType> expsMaxScoreT(Expression var,Vector<ExpressionPlusType> expsMaxScoreTV ,String keywords){
		String type = var.getType().toString();
		Vector<Expression> varVT = new Vector<Expression>();
		float scoreVar = var.score(keywords);
		int SIZE = expsMaxScoreTV.size();
		for(int i = 0; i < SIZE ; i ++) {
			ExpressionPlusType expsTI = expsMaxScoreTV.get(i);
			if(expsTI.getType() == type) {
				// the score of current max score in the vector
				float scoreVec = expsTI.getExps().get(0).score(keywords);
				if(scoreVar>scoreVec) {
					varVT.add(var);
					expsTI.getExps().clear();
					expsTI.setExps(varVT);
				}if(scoreVar == scoreVec) {
					expsTI.getExps().add(var);
				}
				break;
			}
		}
		return expsMaxScoreTV;
	}
//	getDepth
	public int getDepth() {
		return 1;
	}

	public String toString() {
		return v;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Var other = (Var) obj;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		return true;
	}

	// type function return type of variable
	@Override
	public Type getType() {
		return type;
	}

}