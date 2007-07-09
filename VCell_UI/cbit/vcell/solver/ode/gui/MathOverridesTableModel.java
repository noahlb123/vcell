package cbit.vcell.solver.ode.gui;
/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import org.vcell.expression.ExpressionFactory;
import org.vcell.expression.IExpression;

import cbit.vcell.math.Constant;
/**
 * Insert the type's description here.
 * Creation date: (10/22/2000 11:46:26 AM)
 * @author: 
 */
public class MathOverridesTableModel extends javax.swing.table.AbstractTableModel implements cbit.vcell.simulation.MathOverridesListener {
	private String[] fieldKeys = new String[0];
	protected transient java.beans.PropertyChangeSupport propertyChange;
	private cbit.vcell.simulation.MathOverrides fieldMathOverrides = null;
	private boolean fieldModified = false;
	private boolean fieldEditable = false;
	public final static int COLUMN_PARAMETER = 0;
	public final static int COLUMN_DEFAULT = 1;
	public final static int COLUMN_ACTUAL = 2;
	public final static int COLUMN_SCAN = 3;
	private String[] columnNames = new String[] {"Parameter Name", "Default Value", "Change Value", "Scan"};

/**
 * MathOverridesTableModel constructor comment.
 */
public MathOverridesTableModel() {
	super();
	fieldMathOverrides = null;
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
 * 
 * @param event cbit.vcell.solver.MathOverridesEvent
 */
public void constantAdded(cbit.vcell.simulation.MathOverridesEvent event) {
	fireTableDataChanged();
}


/**
 * 
 * @param event cbit.vcell.solver.MathOverridesEvent
 */
public void constantChanged(cbit.vcell.simulation.MathOverridesEvent event) {
	fireTableDataChanged();
}


/**
 * 
 * @param event cbit.vcell.solver.MathOverridesEvent
 */
public void constantRemoved(cbit.vcell.simulation.MathOverridesEvent event) {
	fireTableDataChanged();
}


/**
 * Insert the method's description here.
 * Creation date: (9/23/2005 5:06:23 PM)
 */
private void editScanValues(String name, int r) throws org.vcell.expression.DivideByZeroException, org.vcell.expression.ExpressionException {
	cbit.vcell.client.desktop.simulation.ParameterScanPanel panel = new cbit.vcell.client.desktop.simulation.ParameterScanPanel();
	cbit.vcell.simulation.ConstantArraySpec spec = null;
	if (getMathOverrides().isScan(name)) {
		spec = getMathOverrides().getConstantArraySpec(name);
	} else {
		spec = cbit.vcell.simulation.ConstantArraySpec.createIntervalSpec(name, 0, getMathOverrides().getDefaultExpression(name).evaluateConstant(), 2, false);
	}
	panel.setConstantArraySpec(spec);
	int confirm = org.vcell.util.gui.DialogUtils.showComponentOKCancelDialog(null, panel, "Scan values for parameter '" + fieldKeys[r]);
	if (confirm == javax.swing.JOptionPane.OK_OPTION) {
		panel.applyValues();
		getMathOverrides().putConstantArraySpec(panel.getConstantArraySpec());
	}
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
		case COLUMN_PARAMETER:{
			return String.class;
		}
		case COLUMN_DEFAULT:{
			return String.class;
		}
		case COLUMN_ACTUAL:{
			return String.class;
		}
		case COLUMN_SCAN:{
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
	return columnNames.length;
}


/**
 * getColumnCount method comment.
 */
public String getColumnName(int column) {
	try {
		return columnNames[column];
	} catch (Throwable exc) {
		System.out.println("WARNING - no such column index: " + column);
		handleException(exc);
		return null;
	}
}


/**
 * Gets the editable property (boolean) value.
 * @return The editable property value.
 * @see #setEditable
 */
public boolean getEditable() {
	return fieldEditable;
}


/**
 * Gets the mathOverrides property (cbit.vcell.solver.MathOverrides) value.
 * @return The mathOverrides property value.
 * @see #setMathOverrides
 */
public cbit.vcell.simulation.MathOverrides getMathOverrides() {
	return fieldMathOverrides;
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
	return (fieldKeys.length);
}


/**
 * getValueAt method comment.
 */
public Object getValueAt(int row, int column) {
	try {
		switch (column) {
			case COLUMN_PARAMETER: {
				return fieldKeys[row];
			} 
			case COLUMN_DEFAULT: {
				return getMathOverrides().getDefaultExpression(fieldKeys[row]).infix();
			} 
			case COLUMN_ACTUAL: {
				if (getMathOverrides().isScan(fieldKeys[row])) {
					return getMathOverrides().getConstantArraySpec(fieldKeys[row]);
				} else {
					return getMathOverrides().getActualExpression(fieldKeys[row], 0).infix();
				}
			}
			case COLUMN_SCAN: {
				return new Boolean(getMathOverrides().isScan(fieldKeys[row]));
			}
			default: {
				throw new Exception();
			}
		}
	} catch (Throwable exc) {
		// we don't check for coordinates, but do it this way so we can get a stacktrace if the wrong cell is being asked for
		// also, this way there is no overhead since try/catch is free unless exception is thrown
		System.out.println("WARNING - no such cell: " + row + ", " + column);
		handleException(exc);
		return null;
	}
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	exception.printStackTrace(System.out);
}


/**
 * The hasListeners method was generated to support the propertyChange field.
 */
public synchronized boolean hasListeners(java.lang.String propertyName) {
	return getPropertyChange().hasListeners(propertyName);
}


/**
 * getColumnCount method comment.
 */
public boolean isCellEditable(int r, int c) {
	if (!getEditable()) {
		return false;
	} else if (c == COLUMN_ACTUAL) {
		if (getMathOverrides().isScan(fieldKeys[r])) {
			return false;
		} else {
			return true;
		}
	} else if (c == COLUMN_SCAN) {
		return true;
	} else {
		return false;
	}
}


/**
 * Insert the method's description here.
 * Creation date: (8/7/2001 1:16:31 PM)
 * @return boolean
 * @param row int
 */
public boolean isDefaultValue(int row) {
	try {
		return getMathOverrides().isDefaultExpression(fieldKeys[row]);
	} catch (Throwable exc) {
		handleException(exc);
		return true;
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
 * Sets the editable property (boolean) value.
 * @param editable The new value for the property.
 * @see #getEditable
 */
public void setEditable(boolean editable) {
	boolean oldValue = fieldEditable;
	fieldEditable = editable;
	firePropertyChange("editable", new Boolean(oldValue), new Boolean(editable));
}


/**
 * Sets the mathOverrides property (cbit.vcell.solver.MathOverrides) value.
 * @param mathOverrides The new value for the property.
 * @see #getMathOverrides
 */
public void setMathOverrides(cbit.vcell.simulation.MathOverrides mathOverrides) {
	cbit.vcell.simulation.MathOverrides oldValue = fieldMathOverrides;
	if (oldValue!=null){
		oldValue.removeMathOverridesListener(this);
	}
	fieldMathOverrides = mathOverrides;
	if (fieldMathOverrides!=null){
		fieldMathOverrides.addMathOverridesListener(this);
	}
	fieldKeys = new String[0];
	if (mathOverrides != null) {
		if (getEditable()) {
			// show all
			fieldKeys = mathOverrides.getAllConstantNames();
		} else {
			// summary, show only overriden ones
			fieldKeys = mathOverrides.getOverridenConstantNames();
		}
		sort(fieldKeys, 0, fieldKeys.length-1);
	}
	firePropertyChange("mathOverrides", oldValue, mathOverrides);
	//  Or should this only be fireTableDataChanged()???
	fireTableDataChanged();
}


/**
 * Sets the modified property (boolean) value.
 * @param modified The new value for the property.
 * @see #getModified
 */
public void setModified(boolean modified) {
	boolean oldValue = fieldModified;
	fieldModified = modified;
	firePropertyChange("modified", new Boolean(oldValue), new Boolean(modified));
}


/**
 * getValueAt method comment.
 */
public void setValueAt(Object object, int r, int c) {
	try {
		String name = (String) getValueAt(r,0);
		if (c == COLUMN_ACTUAL) {
			if (object instanceof cbit.vcell.simulation.ConstantArraySpec) {
				editScanValues(name, r);
			} else if (object instanceof String) {
				IExpression expression = ExpressionFactory.createExpression((String) object);
				Constant constant = new Constant(name, expression);
				getMathOverrides().putConstant(constant);
				fireTableCellUpdated(r, c);
				this.fireTableDataChanged();
				setModified(true);
			}
		} else if (c == COLUMN_SCAN) {
			if (((Boolean)object).booleanValue()) {
				// setting scan values
				editScanValues(name, r);
			} else {
				// resetting to default
				setValueAt(getValueAt(r, COLUMN_DEFAULT), r, COLUMN_ACTUAL);
			}
		}
	} catch (Throwable exc) {
		// we do it this way so that we properly deal with duplicate events
		class Runner implements Runnable {
			private String message = "";
			public void setMessage(String newMessage) {
				message = newMessage;
			}
			public void run() {
				cbit.vcell.client.PopupGenerator.showErrorDialog(message + "\nOld value was restored.");
			}
		};
		Runner rr = new Runner();
		rr.setMessage(exc.getMessage());
		javax.swing.SwingUtilities.invokeLater(rr);
		
	}
}


private static void sort(String arr[], int left, int right) {
	if (left < right) {
		swap(arr, left, (left + right) / 2);
		int last = left;
		for (int i = left + 1; i <= right; i++) {
			if (arr[i].compareTo(arr[left]) < 0) {
				swap(arr, ++last, i);
			}
		}
		swap(arr, left, last);
		sort(arr, left, last - 1);
		sort(arr, last + 1, right);
	}
}


private static void swap(String arr[], int i, int j) {
    String tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
}
}