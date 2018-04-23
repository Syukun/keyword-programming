package codeGenerate;

class DuplicateFlag {
	private boolean flag;
	private Expression expR;
	private Expression[] expsP;
	
	// constructor
	public DuplicateFlag(boolean flag,Expression expR,Expression[] expP) {
		this.setFlag(flag);
		this.setExpR(expR);
		this.setExpsP(expP);
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Expression getExpR() {
		return expR;
	}

	public void setExpR(Expression expR) {
		this.expR = expR;
	}

	public Expression[] getExpsP() {
		return expsP;
	}

	public void setExpsP(Expression[] expsP) {
		this.expsP = expsP;
	}
	
	
}
