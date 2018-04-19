package codeGenerate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class KeywordFlag {
	private boolean flag;
	private String keyword;

	// constructor
	public KeywordFlag(boolean flag, String keyword) {
		this.setFlag(flag);
		this.setKeyword(keyword);
	}

	public KeywordFlag() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Translate keywords to List of Keywords with Flag e.g "add line" =>
	 * "(false,add),(false,line)
	 */
	public List<KeywordFlag> toKeywordFlag(String keywords) {
		List<KeywordFlag> keywordListFlag = new ArrayList<KeywordFlag>();
		List<String> keywordList = Arrays.asList(keywords.toLowerCase().split(" "));
		for (String keyword : keywordList) {
			keywordListFlag.add(new KeywordFlag(false, keyword));
		}
		return keywordListFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}