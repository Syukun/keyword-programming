package codeGenerate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

class MName {
	private String mName;
	private Type returnType;
	private Type[] parameterType;

	// constructor
	public MName(String mName, Type returnType, Type[] parameterType) {
		this.mName = mName;
		this.setReturnType(returnType);
		this.setParameterType(parameterType);
	}

	// score function

	public float score(List<KeywordFlag> keywordListFlag) {
		float score = Expression.DEFSCORE;
		String[] wordsArray = this.mName.split("(?<!^)(?=[A-Z])");
		for (int i = 0; i < wordsArray.length; i++) {
			score = addPrecise(scoreWord(wordsArray[i], keywordListFlag), score);
		}
		return score;
	}

	public float scoreWord(String word, List<KeywordFlag> keywordListFlag) {

		float score = 0;

		if (isInKeywordListFlag(word.toLowerCase(), keywordListFlag)) {
			if (!Expression.used) {
				score = addPrecise(score, Expression.WIK);

			} else {
				Expression.used = false;
			}

		} else {
			score = addPrecise(score, -Expression.WNIK);
		}

		return score;
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
					Expression.used = true;
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

	// return all method names
	public static Vector<MName> allMethodName() {
		Vector<MName> allMethodNameV = new Vector<MName>();
//		 allMethodNameV.add(
//		 new MName("add", new Type("boolean"), new Type[] { new Type("List<String>"),
//		 new Type("String") }));
//		 allMethodNameV.add(new MName("readLine", new Type("String"), new Type[] { new
//		 Type("BufferedReader") }));
//		 allMethodNameV
//		 .add(new MName("valueOf", new Type("String"), new Type[] { new
//		 Type("String"), new Type("Object") }));
//		allMethodNameV
//				.add(new MName("addString", new Type("String"), new Type[] { new Type("String"),new Type("String")}));
		 allMethodNameV.add(
		 new MName("add", new Type("String"), new Type[] { new Type("String"), new
		 Type("String") ,new Type("String")}));
		return allMethodNameV;
	}

	@Override
	public String toString() {
		return mName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MName other = (MName) obj;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		return true;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public Type[] getParameterType() {
		return parameterType;
	}

	public void setParameterType(Type[] parameterType) {
		this.parameterType = parameterType;
	}

	public boolean matchType(Type type, int pos) {
		return this.getParameterType()[pos].equals(type);
	}

	public boolean matchReceiverType(Type type) {
		return this.matchType(type, 0);
	}

}