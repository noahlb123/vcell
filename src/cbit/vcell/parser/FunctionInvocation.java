/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.parser;

public class FunctionInvocation {
	private String functionName = null;
	private int functionId;
	private Expression[] arguments = null;
	private Expression functionExpression = null;
	
	FunctionInvocation(ASTFuncNode funcNode){
		functionName = funcNode.getName();
		functionId = funcNode.getFunction();
		int numChildren = funcNode.jjtGetNumChildren();
		arguments = new Expression[numChildren];
		for (int i=0;i<numChildren;i++){
			arguments[i] = new Expression((SimpleNode)funcNode.jjtGetChild(i).copyTree());
		}
		functionExpression = new Expression((SimpleNode)funcNode.copyTree());
	}

	public String getFunctionName() {
		return functionName;
	}

	public int getFunctionId() {
		return functionId;
	}

	public Expression[] getArguments(){
		return arguments;
	}

	public Expression getFunctionExpression() {
		return functionExpression;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof FunctionInvocation){
			FunctionInvocation fi = (FunctionInvocation)obj;
			return functionExpression.equals(fi.functionExpression);
		}
		return false;
	}

	public int hashCode(){
		return functionExpression.hashCode();
	}

}
