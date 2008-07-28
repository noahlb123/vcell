package cbit.vcell.model.gui;

import cbit.vcell.units.VCUnitException;
/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import cbit.vcell.parser.Expression;
import cbit.vcell.parser.SymbolTableEntry;
import cbit.vcell.client.PopupGenerator;
import cbit.vcell.geometry.*;
import cbit.vcell.model.ModelQuantity;
import cbit.vcell.model.ReactionStep;
import cbit.vcell.model.FluxReaction;
import cbit.vcell.model.ReservedSymbol;
import cbit.vcell.model.Species;
import cbit.vcell.model.SpeciesContext;
import cbit.vcell.mapping.ReactionSpec;
import cbit.vcell.parser.ExpressionException;
import cbit.util.BeanUtils;
import cbit.vcell.model.Kinetics;
import cbit.vcell.model.Kinetics.KineticsParameter;
import cbit.vcell.model.Kinetics.KineticsProxyParameter;
/**
 * Insert the type's description here.
 * Creation date: (2/23/01 10:52:36 PM)
 * @author: 
 */
public class ParameterTableModel extends javax.swing.table.AbstractTableModel implements java.beans.PropertyChangeListener {
	private final int NUM_COLUMNS = 5;
	private final int COLUMN_NAME = 0;
	private final int COLUMN_DESCRIPTION = 1;
	private final int COLUMN_IS_LOCAL = 2;
	private final int COLUMN_VALUE = 3;
	private final int COLUMN_UNITS = 4;
	private String LABELS[] = { "Name", "Description", "Local", "Expression", "Units" };
	protected transient java.beans.PropertyChangeSupport propertyChange;
	private cbit.vcell.model.Kinetics fieldKinetics = null;
/**
 * ReactionSpecsTableModel constructor comment.
 */
public ParameterTableModel() {
	super();
	addPropertyChangeListener(this);
}
/**
 * The addPropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
	getPropertyChange().addPropertyChangeListener(listener);
}
/**
 * The addPropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void addPropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener) {
	getPropertyChange().addPropertyChangeListener(propertyName, listener);
}
/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.beans.PropertyChangeEvent evt) {
	getPropertyChange().firePropertyChange(evt);
}
/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.lang.String propertyName, int oldValue, int newValue) {
	getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
}
/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue) {
	getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
}
/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.lang.String propertyName, boolean oldValue, boolean newValue) {
	getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
}
/**
 * Insert the method's description here.
 * Creation date: (2/24/01 12:24:35 AM)
 * @return java.lang.Class
 * @param column int
 */
public Class getColumnClass(int column) {
	switch (column){
		case COLUMN_NAME:{
			return String.class;
		}
		case COLUMN_UNITS:{
			return String.class;
		}
		case COLUMN_VALUE:{
			return cbit.vcell.parser.ScopedExpression.class;
		}
		case COLUMN_DESCRIPTION:{
			return String.class;
		}
		case COLUMN_IS_LOCAL: {
			return Boolean.class;
		}
		default:{
			return Object.class;
		}
	}
}
/**
 * getColumnCount method comment.
 */
public int getColumnCount() {
	return NUM_COLUMNS;
}
/**
 * Insert the method's description here.
 * Creation date: (2/24/01 12:24:35 AM)
 * @return java.lang.Class
 * @param column int
 */
public String getColumnName(int column) {
	if (column<0 || column>=NUM_COLUMNS){
		throw new RuntimeException("ParameterTableModel.getColumnName(), column = "+column+" out of range ["+0+","+(NUM_COLUMNS-1)+"]");
	}
	return LABELS[column];
}
/**
 * Gets the geometry property (cbit.vcell.geometry.Geometry) value.
 * @return The geometry property value.
 * @see #setGeometry
 */
public cbit.vcell.model.Kinetics getKinetics() {
	return fieldKinetics;
}
/**
 * getValueAt method comment.
 */
private cbit.vcell.model.Parameter getParameter(int row) {
	if (row<0 || row>=getRowCount()){
		throw new RuntimeException("ParameterTableModel.getValueAt(), row = "+row+" out of range ["+0+","+(getRowCount()-1)+"]");
	}
	int index = row;
	if (index<getKinetics().getKineticsParameters().length){
		return getKinetics().getKineticsParameters()[index];
	}
	index = index - getKinetics().getKineticsParameters().length;
	if (index<getKinetics().getProxyParameters().length){
		return getKinetics().getProxyParameters()[index];
	}
	index = index - getKinetics().getProxyParameters().length;
	if (index<getKinetics().getUnresolvedParameters().length){
		return getKinetics().getUnresolvedParameters()[index];
	}
	return null;
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
 * getRowCount method comment.
 */
public int getRowCount() {
	if (getKinetics()==null){
		return 0;
	}else{
		return getKinetics().getKineticsParameters().length + getKinetics().getUnresolvedParameters().length + getKinetics().getProxyParameters().length;
	}
}
/**
 * getValueAt method comment.
 */
public Object getValueAt(int row, int col) {
	if (col<0 || col>=NUM_COLUMNS){
		throw new RuntimeException("ParameterTableModel.getValueAt(), column = "+col+" out of range ["+0+","+(NUM_COLUMNS-1)+"]");
	}
	cbit.vcell.model.Parameter parameter = getParameter(row);
	switch (col){
		case COLUMN_NAME:{
			return getKinetics().getReactionStep().getNameScope().getSymbolName(parameter);
			//return parameter.getName();
		}
		case COLUMN_UNITS:{
			if (parameter.getUnitDefinition() != null){
				return parameter.getUnitDefinition().getSymbol();
			}else{
				return "??";
			}
		}
		case COLUMN_VALUE:{
			Expression exp = parameter.getExpression();
			if (exp!=null){
				return new cbit.vcell.parser.ScopedExpression(parameter.getExpression(),parameter.getNameScope(),parameter.isExpressionEditable());
			}else{
				return "defined in application"; // new cbit.vcell.parser.ScopedExpression(parameter.getExpression(),parameter.getNameScope(),parameter.isExpressionEditable());
			}
		}
		case COLUMN_DESCRIPTION:{
			return parameter.getDescription();
		}
		case COLUMN_IS_LOCAL: {
			if (parameter instanceof KineticsParameter) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
		default:{
			return null;
		}
	}
}
/**
 * The hasListeners method was generated to support the propertyChange field.
 */
public synchronized boolean hasListeners(java.lang.String propertyName) {
	return getPropertyChange().hasListeners(propertyName);
}
/**
 * Insert the method's description here.
 * Creation date: (2/24/01 12:27:46 AM)
 * @return boolean
 * @param rowIndex int
 * @param columnIndex int
 */
public boolean isCellEditable(int rowIndex, int columnIndex) {
	cbit.vcell.model.Parameter parameter = getParameter(rowIndex);
	if (columnIndex == COLUMN_NAME){
		return parameter.isNameEditable();
	}else if (columnIndex == COLUMN_UNITS){
		return parameter.isUnitEditable();
	}else if (columnIndex == COLUMN_DESCRIPTION){
		return false;
	}else if (columnIndex == COLUMN_VALUE){
		return parameter.isExpressionEditable();
	}else if (columnIndex == COLUMN_IS_LOCAL) {
		// if the parameter is reaction rate param or a ReservedSymbol in the model, it should not be editable
		if (parameter == getKinetics().getAuthoritativeParameter()) {
			return false;
		} else if (parameter instanceof KineticsProxyParameter) { 
			KineticsProxyParameter kpp = (KineticsProxyParameter)parameter; 
			SymbolTableEntry ste = kpp.getTarget();
			if ((ste instanceof ReservedSymbol) || (ste instanceof SpeciesContext) || (ste instanceof ModelQuantity)) {
				return false;
			}
		}
		return true;
	}else{
		return false;
	}
}
/**
	 * This method gets called when a bound property is changed.
	 * @param evt A PropertyChangeEvent object describing the event source 
	 *   and the property that has changed.
	 */
public void propertyChange(java.beans.PropertyChangeEvent evt) {
	if (evt.getSource() == this && evt.getPropertyName().equals("kinetics")) {
		cbit.vcell.model.Kinetics oldKinetics = (Kinetics)evt.getOldValue();
		cbit.vcell.model.Kinetics newKinetics = (Kinetics)evt.getNewValue();
		if (oldKinetics != null){
			oldKinetics.removePropertyChangeListener(this);
			for (int i = 0; i < oldKinetics.getKineticsParameters().length; i++){
				oldKinetics.getKineticsParameters()[i].removePropertyChangeListener(this);
			}
			for (int i = 0; i < oldKinetics.getProxyParameters().length; i++){
				oldKinetics.getProxyParameters()[i].removePropertyChangeListener(this);
			}
		}
		if (newKinetics != null){
			newKinetics.addPropertyChangeListener(this);
			for (int i = 0; i < newKinetics.getKineticsParameters().length; i++){
				newKinetics.getKineticsParameters()[i].addPropertyChangeListener(this);
			}
			for (int i = 0; i < newKinetics.getProxyParameters().length; i++){
				newKinetics.getProxyParameters()[i].addPropertyChangeListener(this);
			}
		}
		fireTableDataChanged();
	}
	if (evt.getSource() instanceof cbit.vcell.model.Kinetics && 
			(evt.getPropertyName().equals("kineticsParameters") ||  evt.getPropertyName().equals("proxyParameters"))) {
		cbit.vcell.model.Parameter oldParams[] = (cbit.vcell.model.Parameter[])evt.getOldValue();
		cbit.vcell.model.Parameter newParams[] = (cbit.vcell.model.Parameter[])evt.getNewValue();
		for (int i = 0; oldParams!=null && i < oldParams.length; i++){
			oldParams[i].removePropertyChangeListener(this);
		}
		for (int i = 0; newParams!=null && i < newParams.length; i++){
			newParams[i].addPropertyChangeListener(this);
		}
		fireTableDataChanged();
	}
	if (evt.getSource() instanceof cbit.vcell.model.Parameter) {
		fireTableDataChanged();
	}
}
/**
 * The removePropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
	getPropertyChange().removePropertyChangeListener(listener);
}
/**
 * The removePropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void removePropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener) {
	getPropertyChange().removePropertyChangeListener(propertyName, listener);
}
/**
 * Sets the geometry property (cbit.vcell.geometry.Geometry) value.
 * @param geometry The new value for the property.
 * @see #getGeometry
 */
public void setKinetics(cbit.vcell.model.Kinetics kinetics) {
	cbit.vcell.model.Kinetics oldValue = fieldKinetics;
	fieldKinetics = kinetics;
	firePropertyChange("kinetics", oldValue, kinetics);
}
public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	if (rowIndex<0 || rowIndex>=getRowCount()){
		throw new RuntimeException("ParameterTableModel.setValueAt(), row = "+rowIndex+" out of range ["+0+","+(getRowCount()-1)+"]");
	}
	if (columnIndex<0 || columnIndex>=NUM_COLUMNS){
		throw new RuntimeException("ParameterTableModel.setValueAt(), column = "+columnIndex+" out of range ["+0+","+(NUM_COLUMNS-1)+"]");
	}
	cbit.vcell.model.Parameter parameter = getParameter(rowIndex);
//	try {
		switch (columnIndex){
			case COLUMN_NAME:{
				try {
					if (aValue instanceof String){
						String newName = (String)aValue;
						if (!parameter.getName().equals(newName)){
							if (parameter instanceof Kinetics.KineticsParameter){
								getKinetics().renameParameter(parameter.getName(),newName);
							}else if (parameter instanceof Kinetics.KineticsProxyParameter){
								parameter.setName(newName);
							}
							fireTableRowsUpdated(rowIndex,rowIndex);
						}
					}
				}catch (ExpressionException e){
					e.printStackTrace(System.out);
					cbit.vcell.client.PopupGenerator.showErrorDialog("Error changing parameter name:\n"+e.getMessage());
				}catch (java.beans.PropertyVetoException e){
					e.printStackTrace(System.out);
					cbit.vcell.client.PopupGenerator.showErrorDialog("Error changing parameter name:\n"+e.getMessage());
				}
				break;
			}
			case COLUMN_IS_LOCAL: {
				if (aValue.equals(Boolean.TRUE)) {
					// check box has been changed from <unset> to <set> (<false> to <true>) : change param from global to local  
					if ( (parameter instanceof KineticsProxyParameter) && 
						( (((KineticsProxyParameter)parameter).getTarget() instanceof ReservedSymbol) ||
						(((KineticsProxyParameter)parameter).getTarget() instanceof SpeciesContext) ||
						(((KineticsProxyParameter)parameter).getTarget() instanceof ModelQuantity) ) ) {
							PopupGenerator.showErrorDialog("Parameter : \'" + parameter.getName() + "\' is a " + ((KineticsProxyParameter)parameter).getTarget().getClass() + " in the model; cannot convert it to a local kinetic parameter.");
					} else {
						getKinetics().convertParameterType(parameter, true);
					}
				} else {
					// check box has been changed from <set> to <unset> (<true> to <false>) : change param from local to global  
					if (parameter == getKinetics().getAuthoritativeParameter()) {
						PopupGenerator.showErrorDialog("Parameter : \'" + parameter.getName() + "\' is a reaction rate parameter; cannot convert it to a model level (global) parameter.");
					} else {
						getKinetics().convertParameterType(parameter, false);
					}
				}
				fireTableRowsUpdated(rowIndex,rowIndex);
				break;
			}
			case COLUMN_VALUE:{
				try {
					if (aValue instanceof cbit.vcell.parser.ScopedExpression){
						Expression exp = ((cbit.vcell.parser.ScopedExpression)aValue).getExpression();
						if (parameter instanceof Kinetics.KineticsParameter){
							getKinetics().setParameterValue((Kinetics.KineticsParameter)parameter,exp);
						}else if (parameter instanceof Kinetics.KineticsProxyParameter){
							parameter.setExpression(exp);
						}
					}else if (aValue instanceof String) {
						String newExpressionString = (String)aValue;
						if (parameter instanceof Kinetics.KineticsParameter){
							getKinetics().setParameterValue((Kinetics.KineticsParameter)parameter,new Expression(newExpressionString));
						}else if (parameter instanceof Kinetics.KineticsProxyParameter){
							parameter.setExpression(new Expression(newExpressionString));
						}
					}
					getKinetics().resolveUndefinedUnits();
					fireTableRowsUpdated(rowIndex,rowIndex);
				}catch (java.beans.PropertyVetoException e){
					e.printStackTrace(System.out);
					cbit.vcell.client.PopupGenerator.showErrorDialog("Error:\n"+e.getMessage());
				}catch (ExpressionException e){
					e.printStackTrace(System.out);
					cbit.vcell.client.PopupGenerator.showErrorDialog("Expression error:\n"+e.getMessage());
				}
				break;
			}
			case COLUMN_UNITS:{
				try {
					if (aValue instanceof String && parameter instanceof Kinetics.KineticsParameter && ((Kinetics.KineticsParameter)parameter).getRole()==Kinetics.ROLE_UserDefined){
						String newUnitString = (String)aValue;
						Kinetics.KineticsParameter kineticsParm = (Kinetics.KineticsParameter)parameter;
						if (!kineticsParm.getUnitDefinition().getSymbol().equals(newUnitString)){
							kineticsParm.setUnitDefinition(cbit.vcell.units.VCUnitDefinition.getInstance(newUnitString));
							getKinetics().resolveUndefinedUnits();
							fireTableRowsUpdated(rowIndex,rowIndex);
						}
					}
				}catch (VCUnitException e){
					e.printStackTrace(System.out);
					cbit.vcell.client.PopupGenerator.showErrorDialog("Error changing parameter unit:\n"+e.getMessage());
				}
				break;
			}
		}
//	}catch (java.beans.PropertyVetoException e){
//		e.printStackTrace(System.out);
//	}
}
}
