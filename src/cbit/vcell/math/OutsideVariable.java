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

import org.vcell.util.Compare;
import org.vcell.util.Matchable;

/**
 * This class was generated by a SmartGuide.
 * 
 */
public class OutsideVariable extends Variable {
	private String volVariableName = null;
/**
 * VolumeVariable constructor comment.
 * @param name java.lang.String
 */
public OutsideVariable(String name, String volVariableName) {
	super(name);
	this.volVariableName = volVariableName;
}
/**
 * This method was created in VisualAge.
 * @return boolean
 * @param obj Matchable
 */
public boolean compareEqual(Matchable obj) {
	if (!(obj instanceof OutsideVariable)){
		return false;
	}
	if (!compareEqual0(obj)){
		return false;
	}
	OutsideVariable v = (OutsideVariable)obj;
	if (!Compare.isEqual(volVariableName,v.volVariableName)){
		return false;
	}
	
	return true;
}
/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String getVolVariableName() {
	return volVariableName;
}
@Override
public String getVCML() throws MathException {
	throw new MathException("VCML not supported " + this.getClass().getName());
}
}
