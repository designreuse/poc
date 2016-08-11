/**
 * 
 */
package com.pcs.saffron.manager.bean;

import com.pcs.saffron.cipher.data.point.Point;


/**
 * @author pcseg310
 *
 */
public class ConfigPoint extends Point {

    private static final long serialVersionUID = -8703350482177294191L;
	private String precedence;
	private String expression;
	private String dataType;
	
	
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getPrecedence() {
		return precedence;
	}
	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	

}