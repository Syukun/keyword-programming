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
//		varV.add(new Var("array", new Type("List<String>")));
//		varV.add(new Var("src", new Type("BufferedReader")));
//		varV.add(new Var("toString", new Type("String")));
//		varV.add(new Var("line",new Type("String")));
		varV.add(new Var("a",new Type("String")));
		varV.add(new Var("b",new Type("String")));
		varV.add(new Var("c",new Type("String")));
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
	
	// generate Var Expressions in all possible types
	// and only remain those with max scores.
	// T represents method or variable that need consider type
	public static Vector<ExpressionPlusType> generateMaxScoreT(int depth, String keywords){
		Vector<String> types = new Vector<String>();
		Vector<Expression> varV = Var.generate(depth);
		Vector<ExpressionPlusType> expsMaxScoreTV = new Vector<ExpressionPlusType>();
		int SIZE_varV = varV.size();
		for(int i = 0; i < SIZE_varV; i++) {
			Expression varI = varV.get(i);
			String typeI = varI.getType().toString();
			int index_type = types.indexOf(typeI);
			if(index_type>=0) {
				Var.getExpsMaxScoreT(varI,expsMaxScoreTV,keywords,index_type);
			}else {
				Vector<Expression> varNewT = new Vector<Expression>();
				varNewT.add(varI);
				expsMaxScoreTV.add(new ExpressionPlusType(typeI,varNewT));
				types.add(typeI);
			}
		}
		
		return expsMaxScoreTV;
	}
	
	// compare the score of var with the expression of old one
	// and remain the max score or add that var expression when the score is equal
	public static Vector<ExpressionPlusType> getExpsMaxScoreT(Expression var,Vector<ExpressionPlusType> expsMaxScoreTV ,String keywords,int index_type){
		// Set which contains : type = type of var  
		//					   Vector of expression is all expression in that type with the max score.
		ExpressionPlusType expsT_i = expsMaxScoreTV.get(index_type);
		// new one
		float scoreVar = var.score(keywords);
		
		Expression exp0 = expsT_i.getExps().get(0);
		float scoreExp0 = exp0.score(keywords); 
		
		if(scoreVar > scoreExp0) {
			expsT_i.getExps().clear();
			expsT_i.getExps().add(var);
		}else if(scoreVar == scoreExp0) {
			expsT_i.getExps().add(var);
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