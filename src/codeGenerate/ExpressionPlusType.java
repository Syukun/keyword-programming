package codeGenerate;

import java.util.Vector;

public class ExpressionPlusType {
	private String type;
	private Vector<Expression> exps;
	public ExpressionPlusType(String type,Vector<Expression> exps) {
		this.setType(type);
		this.setExps(exps);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Vector<Expression> getExps() {
		return exps;
	}
	public void setExps(Vector<Expression> exps) {
		this.exps = exps;
	}

}
