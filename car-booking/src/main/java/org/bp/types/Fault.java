package org.bp.types;

public class Fault  extends Exception {

    protected int code;
    protected String text;
    
    public Fault(int code, String text){
    	this.code = code;
    	this.text = text;
    }
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}

