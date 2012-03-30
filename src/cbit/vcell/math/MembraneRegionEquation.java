/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.math;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import org.vcell.util.CommentStringTokenizer;
import org.vcell.util.Compare;
import org.vcell.util.Matchable;

import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;
/**
 * This class was generated by a SmartGuide.
 * 
 */
@SuppressWarnings("serial")
public class MembraneRegionEquation extends Equation {
	private Expression membraneRateExpression = new Expression(0.0);
	private Expression uniformRateExpression = new Expression(0.0);

/**
 * OdeEquation constructor comment.
 * @param subDomain cbit.vcell.math.SubDomain
 * @param var cbit.cell.math.Variable
 * @param initialExp cbit.vcell.parser.Expression
 * @param rateExp cbit.vcell.parser.Expression
 */
public MembraneRegionEquation(MembraneRegionVariable var, Expression initialExp) {
	super(var, initialExp, null);
}


/**
 * Insert the method's description here.
 * Creation date: (9/4/2003 12:32:19 PM)
 * @return boolean
 * @param object cbit.util.Matchable
 */
public boolean compareEqual(Matchable object) {
	MembraneRegionEquation equ = null;
	if (!(object instanceof MembraneRegionEquation)){
		return false;
	}else{
		equ = (MembraneRegionEquation)object;
	}
	if (!compareEqual0(equ)){
		return false;
	}
	if (!Compare.isEqualOrNull(membraneRateExpression,equ.membraneRateExpression)){
		return false;
	}
	if (!Compare.isEqualOrNull(uniformRateExpression,equ.uniformRateExpression)){
		return false;
	}
	return true;
}


/**
 * Insert the method's description here.
 * Creation date: (10/10/2002 10:41:10 AM)
 * @param sim cbit.vcell.solver.Simulation
 */
void flatten(MathSymbolTable simSymbolTable, boolean bRoundCoefficients) throws cbit.vcell.parser.ExpressionException, MathException {
	super.flatten0(simSymbolTable,bRoundCoefficients);
	
	membraneRateExpression = getFlattenedExpression(simSymbolTable,membraneRateExpression,bRoundCoefficients);
	uniformRateExpression = getFlattenedExpression(simSymbolTable,uniformRateExpression,bRoundCoefficients);
}


/**
 * This method was created by a SmartGuide.
 * @return java.util.Vector
 */
public Vector<Expression> getExpressions(MathDescription mathDesc){
	Vector<Expression> list = new Vector<Expression>();
	list.addElement(getUniformRateExpression());
	list.addElement(getMembraneRateExpression());
	
	if (getRateExpression()!=null)		list.addElement(getRateExpression());
	if (getInitialExpression()!=null)	list.addElement(getInitialExpression());
	if (getExactSolution()!=null)		list.addElement(getExactSolution());
	return list;
}


/**
 * Insert the method's description here.
 * Creation date: (7/9/01 2:05:09 PM)
 * @return cbit.vcell.parser.Expression
 */
public cbit.vcell.parser.Expression getMembraneRateExpression() {
	return membraneRateExpression;
}


/**
 * This method was created by a SmartGuide.
 * @return java.util.Enumeration
 */
public Enumeration<Expression> getTotalExpressions() throws ExpressionException {
	Vector<Expression> vector = new Vector<Expression>();
	Expression lvalueExp = new Expression("UniformRate_"+getVariable().getName()+";");
	Expression rvalueExp = new Expression(getUniformRateExpression());
	Expression totalExp = Expression.assign(lvalueExp,rvalueExp);
	totalExp.bindExpression(null);
	totalExp.flatten();
	vector.addElement(totalExp);
	lvalueExp = new Expression("MembraneRate_"+getVariable().getName()+";");
	rvalueExp = new Expression(getMembraneRateExpression());
	totalExp = Expression.assign(lvalueExp,rvalueExp);
	totalExp.bindExpression(null);
	totalExp.flatten();
	vector.addElement(totalExp);
	vector.addElement(getTotalInitialExpression());
	Expression solutionExp = getTotalSolutionExpression();
	if (solutionExp!=null){
		vector.addElement(solutionExp);
	}	
	return vector.elements();
}


/**
 * Insert the method's description here.
 * Creation date: (7/9/01 2:05:09 PM)
 * @return cbit.vcell.parser.Expression
 */
public Expression getUniformRateExpression() {
	return uniformRateExpression;
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String getVCML() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("\t"+VCML.MembraneRegionEquation+" "+getVariable().getName()+" {\n");
	if (getUniformRateExpression() != null){
		buffer.append("\t\t"+VCML.UniformRate+" "+getUniformRateExpression().infix()+";\n");
	}else{
		buffer.append("\t\t"+VCML.UniformRate+" "+"0.0;\n");
	}
	if (getMembraneRateExpression() != null){
		buffer.append("\t\t"+VCML.MembraneRate+" "+getMembraneRateExpression().infix()+";\n");
	}else{
		buffer.append("\t\t"+VCML.MembraneRate+" "+"0.0;\n");
	}
	if (initialExp != null){
		buffer.append("\t\t"+VCML.Initial+"\t "+initialExp.infix()+";\n");
	}
	switch (solutionType){
		case UNKNOWN_SOLUTION:{
			if (initialExp == null){
				buffer.append("\t\t"+VCML.Initial+"\t "+"0.0;\n");
			}
			break;
		}
		case EXACT_SOLUTION:{
			buffer.append("\t\t"+VCML.Exact+" "+exactExp.infix()+";\n");
			break;
		}
	}				
		
	buffer.append("\t}\n");
	return buffer.toString();		
}


/**
 * This method was created by a SmartGuide.
 * @param tokens java.util.StringTokenizer
 * @exception java.lang.Exception The exception description.
 */
public void read(CommentStringTokenizer tokens) throws MathFormatException, ExpressionException {
	String token = null;
	token = tokens.nextToken();
	if (!token.equalsIgnoreCase(VCML.BeginBlock)){
		throw new MathFormatException("unexpected token "+token+" expecting "+VCML.BeginBlock);
	}			
	while (tokens.hasMoreTokens()){
		token = tokens.nextToken();
		if (token.equalsIgnoreCase(VCML.EndBlock)){
			break;
		}			
		if (token.equalsIgnoreCase(VCML.Initial)){
			initialExp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
			continue;
		}
		if (token.equalsIgnoreCase(VCML.UniformRate)){
			Expression exp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
			setUniformRateExpression(exp);
			continue;
		}
		if (token.equalsIgnoreCase(VCML.MembraneRate)){
			Expression exp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
			setMembraneRateExpression(exp);
			continue;
		}
		if (token.equalsIgnoreCase(VCML.Exact)){
			exactExp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
			solutionType = EXACT_SOLUTION;
			continue;
		}
		throw new MathFormatException("unexpected identifier "+token);
	}	
		
}


/**
 * Insert the method's description here.
 * Creation date: (7/9/01 2:05:09 PM)
 * @param newMembraneRateExpression cbit.vcell.parser.Expression
 */
public void setMembraneRateExpression(Expression newMembraneRateExpression) {
	membraneRateExpression = newMembraneRateExpression;
}


/**
 * Insert the method's description here.
 * Creation date: (7/9/01 2:05:09 PM)
 * @param newMembraneRateExpression cbit.vcell.parser.Expression
 */
public void setUniformRateExpression(Expression newUniformRateExpression) {
	uniformRateExpression = newUniformRateExpression;
}


@Override
public void checkValid(MathDescription mathDesc, SubDomain subDomain) throws MathException, ExpressionException {
	checkInitialCondition(mathDesc);
	ArrayList<Expression> expList = new ArrayList<Expression>();
	expList.add(getUniformRateExpression());
	expList.add(getMembraneRateExpression());
	expList.add(getRateExpression());
	expList.add(getExactSolution());
	checkValid_Membrane(mathDesc, expList, (MembraneSubDomain)subDomain);
}
}
