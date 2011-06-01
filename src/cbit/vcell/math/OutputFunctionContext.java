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

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import org.vcell.util.Compare;
import org.vcell.util.Matchable;
import org.vcell.util.gui.DialogUtils;

import cbit.vcell.document.SimulationOwner;
import cbit.vcell.geometry.Geometry;
import cbit.vcell.math.AnnotatedFunction.FunctionCategory;
import cbit.vcell.model.ReservedSymbol;
import cbit.vcell.parser.AbstractNameScope;
import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionBindingException;
import cbit.vcell.parser.ExpressionException;
import cbit.vcell.parser.NameScope;
import cbit.vcell.parser.ScopedSymbolTable;
import cbit.vcell.parser.SymbolTableEntry;
import cbit.vcell.simdata.VariableType;
import cbit.vcell.simdata.VariableType.VariableDomain;
import cbit.vcell.solver.SimulationSymbolTable;

public class OutputFunctionContext implements ScopedSymbolTable, Matchable, Serializable, VetoableChangeListener, PropertyChangeListener {
	
	public class OutputFunctionNameScope extends AbstractNameScope  {
		public OutputFunctionNameScope(){
			super();
		}

		@Override
		public NameScope[] getChildren() {
			return null;
		}

		@Override
		public String getName() {
			// return OutputFunctionContext.this.getName();
			return "OutputFunctionsContext";
		}

		@Override
		public NameScope getParent() {
			return null;
		}

		@Override
		public ScopedSymbolTable getScopedSymbolTable() {
			return OutputFunctionContext.this;
		}
	}

	private ArrayList<AnnotatedFunction> outputFunctionsList = new ArrayList<AnnotatedFunction>();
	private SimulationOwner simulationOwner = null;
	protected transient java.beans.PropertyChangeSupport propertyChange;
	protected transient VetoableChangeSupport vetoPropertyChange;
	private OutputFunctionNameScope nameScope = new OutputFunctionNameScope();
	
	public OutputFunctionContext(SimulationOwner argSimOwner) {
		super();
		addVetoableChangeListener(this);
		if (argSimOwner != null) {
			simulationOwner = argSimOwner;
		} else {
			throw new RuntimeException("SimulationOwner cannot be null for outputFunctionContext.");
		}
		simulationOwner.addPropertyChangeListener(this);
	}

	public ArrayList<AnnotatedFunction> getOutputFunctionsList() {
		return outputFunctionsList;
	}

	public boolean compareEqual(Matchable obj){
		if (obj instanceof OutputFunctionContext) {
			if (!Compare.isEqualOrNull(outputFunctionsList, ((OutputFunctionContext)obj).outputFunctionsList)){
				return false;
			}
			return true;
		}
		return false;
	}

	public SimulationOwner getSimulationOwner() {
		return simulationOwner;
	}

	public void refreshDependencies() {
		removeVetoableChangeListener(this);
		addVetoableChangeListener(this);
		simulationOwner.removePropertyChangeListener(this);
		simulationOwner.addPropertyChangeListener(this);
		rebindAll();
	}
	
	public void rebindAll() {
		try {
			for (int i = 0; i < outputFunctionsList.size(); i++) {
				outputFunctionsList.get(i).getExpression().bindExpression(this);
			}
		} catch (ExpressionBindingException e) {
			e.printStackTrace(System.out);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void addOutputFunction(AnnotatedFunction obsFunction) throws PropertyVetoException {
		if (obsFunction == null){
			return;
		}	
		try {
			obsFunction.getExpression().bindExpression(this);
		} catch (ExpressionBindingException e) {
			e.printStackTrace(System.out);
			throw new RuntimeException(e.getMessage());
		}

		ArrayList<AnnotatedFunction> newFunctionsList = new ArrayList<AnnotatedFunction>(outputFunctionsList);
		newFunctionsList.add(obsFunction);
		setOutputFunctionsList(newFunctionsList);
	}   

	public void removeOutputFunction(AnnotatedFunction obsFunction) throws PropertyVetoException {
		if (obsFunction == null){
			return;
		}	
		if (outputFunctionsList.contains(obsFunction)){
			ArrayList<AnnotatedFunction> newFunctionsList = new ArrayList<AnnotatedFunction>(outputFunctionsList);
			newFunctionsList.remove(obsFunction);
			setOutputFunctionsList(newFunctionsList);
		}
	}         

	public AnnotatedFunction getOutputFunction(String obsFunctionName){
		for (int i=0;i<outputFunctionsList.size();i++){
			AnnotatedFunction function = outputFunctionsList.get(i); 
			if (function.getName().equals(obsFunctionName)){
				return function;
			}
		}
		return null;
	}
	
	public void propertyChange(java.beans.PropertyChangeEvent event) {
		if (event.getSource() == simulationOwner && event.getPropertyName().equals("mathDescription")) {
			rebindAll();
		}
		if (event.getPropertyName().equals("geometry")) {
			Geometry oldGeometry = (Geometry)event.getOldValue();
			Geometry newGeometry = (Geometry)event.getNewValue();
			// changing from ode to pde
			if (oldGeometry.getDimension() == 0 && newGeometry.getDimension() > 0) {
				ArrayList<AnnotatedFunction> newFuncList = new ArrayList<AnnotatedFunction>();
				for (AnnotatedFunction function : outputFunctionsList) {
					try {
						Expression newexp = new Expression(function.getExpression());
						// making sure that output function is not direct function of constant.
						newexp.bindExpression(this);			
						
						// here use math description as symbol table because we allow 
						// new expression itself to be function of constant.
						MathDescription mathDescription = getSimulationOwner().getMathDescription();
						newexp = MathUtilities.substituteFunctions(newexp, mathDescription).flatten();
						VariableType newFuncType = VariableType.VOLUME;
						
						String[] symbols = newexp.getSymbols();
						if (symbols != null) {
							// figure out the function type
							VariableType[] varTypes = new VariableType[symbols.length];
							for (int i = 0; i < symbols.length; i++) {
								Variable var = mathDescription.getVariable(symbols[i]);
								varTypes[i] = VariableType.getVariableType(var);
							}
							// check with flattened expression to find out the variable type of the new expression
							Function flattenedFunctiion = new Function(function.getName(), newexp);
							newFuncType = SimulationSymbolTable.getFunctionVariableType(flattenedFunctiion, symbols, varTypes, true);			
						}
						AnnotatedFunction newFunc = new AnnotatedFunction(function.getName(), function.getExpression(), "", newFuncType, FunctionCategory.OUTPUTFUNCTION);
						newFuncList.add(newFunc);
						newFunc.bind(this);
					} catch (ExpressionException ex) {
						ex.printStackTrace();
						throw new RuntimeException(ex.getMessage());
					}
				}
				try {
					setOutputFunctionsList(newFuncList);
				} catch (PropertyVetoException e) {					
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}
	
	public void setOutputFunctionsList(ArrayList<AnnotatedFunction> outputFunctions) throws java.beans.PropertyVetoException {
		ArrayList<AnnotatedFunction> oldValue = outputFunctionsList;
		fireVetoableChange("outputFunctions", oldValue, outputFunctions);
		outputFunctionsList = outputFunctions;
		firePropertyChange("outputFunctions", oldValue, outputFunctions);
	}

	/**
	 * The addPropertyChangeListener method was generated to support the propertyChange field.
	 */
	public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
		getPropertyChange().addPropertyChangeListener(listener);
	}

	/**
	 * The addVetoableChangeListener method was generated to support the vetoPropertyChange field.
	 */
	public synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
		getVetoPropertyChange().addVetoableChangeListener(listener);
	}

	/**
	 * The firePropertyChange method was generated to support the propertyChange field.
	 */
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * The fireVetoableChange method was generated to support the vetoPropertyChange field.
	 */
	public void fireVetoableChange(String propertyName, Object oldValue, Object newValue)
			throws PropertyVetoException {
				getVetoPropertyChange().fireVetoableChange(propertyName, oldValue, newValue);
			}

	/**
	 * Accessor for the propertyChange field.
	 */
	protected java.beans.PropertyChangeSupport getPropertyChange() {
		if (propertyChange == null) {
			propertyChange = new java.beans.PropertyChangeSupport(this);
		};
		return propertyChange;
	}

	/**
	 * Accessor for the vetoPropertyChange field.
	 */
	protected VetoableChangeSupport getVetoPropertyChange() {
		if (vetoPropertyChange == null) {
			vetoPropertyChange = new java.beans.VetoableChangeSupport(this);
		};
		return vetoPropertyChange;
	}

	/**
	 * The hasListeners method was generated to support the vetoPropertyChange field.
	 */
	public synchronized boolean hasListeners(String propertyName) {
		return getVetoPropertyChange().hasListeners(propertyName);
	}

	/**
	 * The removePropertyChangeListener method was generated to support the propertyChange field.
	 */
	public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
		getPropertyChange().removePropertyChangeListener(listener);
	}

	/**
	 * The removeVetoableChangeListener method was generated to support the vetoPropertyChange field.
	 */
	public synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
		getVetoPropertyChange().removeVetoableChangeListener(listener);
	}

	public void vetoableChange(java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException {
		if (evt.getSource() == this && evt.getPropertyName().equals("outputFunctions")){
			ArrayList<AnnotatedFunction> newOutputFnsList = (ArrayList<AnnotatedFunction>)evt.getNewValue();
			if(newOutputFnsList == null){
				return;
			}
			//
			// while adding a function: check that names are not duplicated and that no common names are mathSymbols
			//
			HashSet<String> namesSet = new HashSet<String>();
			for (int i=0;i<newOutputFnsList.size();i++){
				String fnName = newOutputFnsList.get(i).getName();
				if (namesSet.contains(fnName)){
					throw new PropertyVetoException(" Cannot define multiple output functions with same name '"+fnName+"'.",evt);
				} 
				namesSet.add(fnName);
				// now see if the symbol is a math symbol - cannot use that name for new output function.
				try {
					SymbolTableEntry ste  = getEntry(fnName);
					if (ste != null) {
						if (!(ste instanceof AnnotatedFunction)) {
							throw new PropertyVetoException(" '"+fnName+"' conflicts with existing symbol. Cannot define function with name '" + fnName +".",evt);
						}
					}
				} catch (ExpressionBindingException e) {
					e.printStackTrace(System.out);
				}
			}

			// while deleting an output function: check if it present in expression of other output functions.
			ArrayList<AnnotatedFunction> oldOutputFnsList = (ArrayList<AnnotatedFunction>)evt.getOldValue();
			if (oldOutputFnsList.size() > newOutputFnsList.size()) {
				// if 'newOutputFnList' is smaller than 'oldOutputFnList', one of the functions has been removed, find the missing one
				AnnotatedFunction missingFn = null;
				for (int i = 0; i < oldOutputFnsList.size(); i++) {
					if (!newOutputFnsList.contains(oldOutputFnsList.get(i))) {
						missingFn = oldOutputFnsList.get(i);
					}
				}
				// use this missing output fn (to be deleted) to determine if it is used in any other output fn expressions. 
				if (missingFn != null) {
					// find out if the missing fn is used in other functions in outputFnsList
					Vector<String> referencingOutputFnsVector = new Vector<String>();
					for (int i = 0; i < newOutputFnsList.size(); i++) {
						AnnotatedFunction function = newOutputFnsList.get(i);
						if (function.getExpression().hasSymbol(missingFn.getName())) {
							referencingOutputFnsVector.add(function.getName());
						}
					}
					// if there are any output fns referencing the given 'missingFn', list them all in error msg.
					if (referencingOutputFnsVector.size() > 0) {
						String msg = "Output Function '" + missingFn.getName() + "' is used in expression of other output function(s): ";
						for (int i = 0; i < referencingOutputFnsVector.size(); i++) {
							msg = msg + "'" + referencingOutputFnsVector.elementAt(i) + "'";
							if (i < referencingOutputFnsVector.size()-1) {
								msg = msg + ", "; 
							} else {
								msg = msg + ". ";
							}
						}
						msg = msg + "\n\nCannot delete '" + missingFn.getName() + "'.";
						throw new PropertyVetoException(msg,evt);
					}
				}
			}
		}
	}
	
//	public abstract void validateNamingConflicts(String symbolDescription, Class<?> newSymbolClass, String newSymbolName, PropertyChangeEvent e)  throws PropertyVetoException ;

	public void getEntries(Map<String, SymbolTableEntry> entryMap) {	
		// add all valid entries (variables) from mathdescription
		MathDescription mathDescription = simulationOwner.getMathDescription();
		if (mathDescription != null) {
			Enumeration<Variable> varEnum = mathDescription.getVariables();
			while(varEnum.hasMoreElements()) {
				Variable var = varEnum.nextElement();
				if (!(var instanceof PseudoConstant) && !(var instanceof Constant)) {
					entryMap.put(var.getName(), var);
				} 
			}
		}
		entryMap.put(ReservedVariable.TIME.getName(), ReservedVariable.TIME);
		int dimension = mathDescription.getGeometry().getDimension();
		if (dimension > 0) {
			entryMap.put(ReservedVariable.X.getName(), ReservedVariable.X);
			if (dimension > 1) {
				entryMap.put(ReservedVariable.Y.getName(), ReservedVariable.Y);
				if (dimension > 2) {
					entryMap.put(ReservedVariable.Z.getName(), ReservedVariable.Z);
				}
			}
		}
		// then add list of output functions.
		for (SymbolTableEntry ste : outputFunctionsList) {
			entryMap.put(ste.getName(), ste);
		}
	}


	public void getLocalEntries(Map<String, SymbolTableEntry> entryMap) {
		getEntries(entryMap);		
	}


	public SymbolTableEntry getLocalEntry(String identifier) throws ExpressionBindingException {
		return getEntry(identifier);
	}


	public NameScope getNameScope() {
		return nameScope;
	}

	public SymbolTableEntry getEntry(java.lang.String identifierString) throws ExpressionBindingException {
		//
		// use MathDescription as the primary SymbolTable, just replace the Constants with the overrides.
		//
		SymbolTableEntry ste = null;
		MathDescription mathDescription = simulationOwner.getMathDescription();
		if (mathDescription != null) {
			ste = mathDescription.getEntry(identifierString);
			if (ste != null && !(ste instanceof PseudoConstant) && !(ste instanceof Constant)) {
				return ste;
			}
		}
		// see if it is an output function.
		ste = getOutputFunction(identifierString);
		return ste;
	}

	// check if the new expression is valid for outputFunction of functionType 
	public void validateExpression(Function outputFunction, VariableType functionType, Expression exp) throws ExpressionException {
		String[] symbols = exp.getSymbols();
		if (symbols == null || symbols.length == 0) {
			return;
		}
		MathDescription mathDescription = getSimulationOwner().getMathDescription();
		boolean bSpatial = mathDescription.isSpatial();
		if (bSpatial) {
			Expression newexp = new Expression(exp);
			// making sure that output function is not direct function of constant.
			newexp.bindExpression(this);			
			
			// here use math description as symbol table because we allow 
			// new expression itself to be function of constant.
			newexp = MathUtilities.substituteFunctions(newexp, mathDescription).flatten();
			symbols = newexp.getSymbols();
			if (symbols != null) {
				// making sure that new expression is defined in the same domain
				VariableType[] varTypes = new VariableType[symbols.length];
				VariableDomain functionVariableDomain = functionType.getVariableDomain();
				for (int i = 0; i < symbols.length; i++) {
					if (ReservedVariable.fromString(symbols[i]) != null) {
						varTypes[i] = functionType;
					} else {
						Variable var = mathDescription.getVariable(symbols[i]);
						varTypes[i] = VariableType.getVariableType(var);
						if (!varTypes[i].getVariableDomain().equals(VariableDomain.VARIABLEDOMAIN_UNKNOWN) && !varTypes[i].getVariableDomain().equals(functionVariableDomain)) {
							boolean bVolume = functionVariableDomain.equals(VariableDomain.VARIABLEDOMAIN_VOLUME);
							String errMsg = "'" + outputFunction.getName() + "' directly or indirectly references "
								+  (bVolume ? "membrane" : "volume") + " variable '" + symbols[i] + "'. " 
								+ functionVariableDomain.getName() + " output functions should only reference " + functionVariableDomain.getName() + " variables.";
							throw new ExpressionException(errMsg);
						}
					}
				}
				// check with flattened expression to find out the variable type of the new expression
				Function flattenedFunctiion = new Function(outputFunction.getName(), newexp);
				VariableType newVarType = SimulationSymbolTable.getFunctionVariableType(flattenedFunctiion, symbols, varTypes, bSpatial);
				if (!newVarType.getVariableDomain().equals(functionVariableDomain)) {
					String errMsg = "The expression for '" + outputFunction.getName() + "' includes at least one " 
						+ newVarType.getVariableDomain().getName() + " variable. Please make sure that only " + functionVariableDomain.getName() + " variables are " +
								"referenced in " + functionVariableDomain.getName() + " output functions.";
					throw new ExpressionException(errMsg);
				}
			}
		}
	}
}
