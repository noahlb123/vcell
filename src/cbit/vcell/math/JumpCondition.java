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
import java.util.Enumeration;
import java.util.Vector;

import org.vcell.util.CommentStringTokenizer;
import org.vcell.util.Compare;
import org.vcell.util.Matchable;

import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;
import cbit.vcell.solver.SimulationSymbolTable;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class JumpCondition extends Equation {
	private Expression inFluxExp = new Expression(0.0);
	private Expression outFluxExp = new Expression(0.0);

/**
 * OdeEquation constructor comment.
 * @param membrane cbit.vcell.math.MembraneSubDomain
 * @param volVar cbit.vcell.math.VolVariable
 */
public JumpCondition(VolVariable volVar) {
	super(volVar, null, null);
}

public JumpCondition(VolumeRegionVariable volRegionVar) {
	super(volRegionVar, null, null);
}

/**
 * Insert the method's description here.
 * Creation date: (9/4/2003 12:32:19 PM)
 * @return boolean
 * @param object cbit.util.Matchable
 */
public boolean compareEqual(Matchable object) {
	JumpCondition equ = null;
	if (!(object instanceof JumpCondition)){
		return false;
	}else{
		equ = (JumpCondition)object;
	}
	if (!compareEqual0(equ)){
		return false;
	}
	if (!Compare.isEqualOrNull(inFluxExp,equ.inFluxExp)){
		return false;
	}
	if (!Compare.isEqualOrNull(outFluxExp,equ.outFluxExp)){
		return false;
	}
	return true;
}


/**
 * Insert the method's description here.
 * Creation date: (10/10/2002 10:41:10 AM)
 * @param sim cbit.vcell.solver.Simulation
 */
void flatten(SimulationSymbolTable simSymbolTable, boolean bRoundCoefficients) throws cbit.vcell.parser.ExpressionException, MathException {
	super.flatten0(simSymbolTable,bRoundCoefficients);
	
	inFluxExp = getFlattenedExpression(simSymbolTable,inFluxExp,bRoundCoefficients);
	outFluxExp = getFlattenedExpression(simSymbolTable,outFluxExp,bRoundCoefficients);
}


/**
 * This method was created by a SmartGuide.
 * @return java.util.Vector
 */
protected Vector<Expression> getExpressions(MathDescription mathDesc) {
	Vector<Expression> list = new Vector<Expression>();
	list.addElement(getInFluxExpression());
	list.addElement(getOutFluxExpression());
	
	if (getRateExpression()!=null)		list.addElement(getRateExpression());
	if (getInitialExpression()!=null)	list.addElement(getInitialExpression());
	if (getExactSolution()!=null)		list.addElement(getExactSolution());
	return list;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.parser.Expression
 */
public Expression getInFluxExpression() {
	return inFluxExp;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.parser.Expression
 */
public Expression getOutFluxExpression() {
	return outFluxExp;
}


/**
 * This method was created by a SmartGuide.
 * @return java.util.Enumeration
 */
public Enumeration<Expression> getTotalExpressions() throws ExpressionException {
	Vector<Expression> vector = new Vector<Expression>();
	Expression lvalueExp = new Expression("InFlux_"+getVariable().getName());
	Expression rvalueExp = new Expression(getInFluxExpression());
	Expression totalExp = Expression.assign(lvalueExp,rvalueExp);
	totalExp.bindExpression(null);
	totalExp.flatten();
	vector.addElement(totalExp);
	lvalueExp = new Expression("OutFlux_"+getVariable().getName());
	rvalueExp = new Expression(getOutFluxExpression());
	totalExp = Expression.assign(lvalueExp,rvalueExp);
	totalExp.bindExpression(null);
	totalExp.flatten();
	vector.addElement(totalExp);
	return vector.elements();
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String getVCML() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("\t"+VCML.JumpCondition+" "+getVariable().getName()+" {\n");
	if (inFluxExp != null){
		buffer.append("\t\t"+VCML.InFlux+"\t"+inFluxExp.infix()+";\n");
	}else{
		buffer.append("\t\t"+VCML.InFlux+"\t"+"0.0;\n");
	}
	if (outFluxExp != null){
		buffer.append("\t\t"+VCML.OutFlux+"\t"+outFluxExp.infix()+";\n");
	}else{
		buffer.append("\t\t"+VCML.OutFlux+"\t"+"0.0;\n");
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
		if (token.equalsIgnoreCase(VCML.InFlux)){
			inFluxExp = new Expression(tokens);
			continue;
		}
		if (token.equalsIgnoreCase(VCML.OutFlux)){
			outFluxExp = new Expression(tokens);
			continue;
		}
		throw new MathFormatException("unexpected identifier "+token);
	}	
		
}


/**
 * This method was created in VisualAge.
 * @param exp cbit.vcell.parser.Expression
 */
public void setInFlux(Expression exp) {
	this.inFluxExp = exp;
}


/**
 * This method was created in VisualAge.
 * @param exp cbit.vcell.parser.Expression
 */
public void setOutFlux(Expression exp) {
	this.outFluxExp = exp;
}


@Override
public void checkValid(MathDescription mathDesc) throws MathException, ExpressionException {
	checkValid_JumpCondition(mathDesc, inFluxExp);
	checkValid_JumpCondition(mathDesc, outFluxExp);
}

private void checkValid_JumpCondition(MathDescription mathDesc, Expression exp) throws MathException, ExpressionException {
	if (exp == null) {
		return;
	}
	Expression newExp = MathUtilities.substituteFunctions(exp, mathDesc);
	String[] symbols = newExp.getSymbols();
	if (symbols == null) {
		return;
	}
	for (String symbol : symbols) {
		Variable variable = mathDesc.getVariable(symbol);
		if (variable instanceof VolVariable				
				|| variable instanceof VolumeRegionVariable) {			
			String varType = "volume";
			if (variable instanceof VolumeRegionVariable) {
				varType = "volume region";
			}
			throw new MathException("Membrane jump condition for Variable '" + getVariable().getName() + "' references " + varType + 
					" variable '" + symbol + "'. Please use " +
					symbol + "_INSIDE or " + symbol + "_OUTSIDE to denote " + varType + " variable " + symbol + "'s solution at the membrane");
		} else if (variable instanceof VolumeRandomVariable) {
			throw new MathException("Membrane jump condition for Variable '" + getVariable().getName() + "' references volume random " +
					"variable '" + symbol + "'. ");
		}
	}
}
}
