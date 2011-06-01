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
import java.io.Serializable;

import org.vcell.util.Matchable;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class BoundaryConditionType implements Serializable, Matchable {
	private final static int DIRICHLET = 1;
	private final static int NEUMANN = 2;
	private final static int PERIODIC = 3;

	private final static String OLD_DIRICHLET_STRING = "Dirichlet";
	private final static String OLD_NEUMANN_STRING = "Neumann";
	public final static String DIRICHLET_STRING = "Value";
	public final static String NEUMANN_STRING = "Flux";
	public final static String PERIODIC_STRING = "Periodic";
	
	private int type = DIRICHLET;

/**
 * Insert the method's description here.
 * Creation date: (5/24/00 4:10:51 PM)
 */
private BoundaryConditionType(int aType) {
	this.type = aType;
}

public BoundaryConditionType(BoundaryConditionType bct) {
	this.type = bct.type;
}

/**
 * Insert the method's description here.
 * Creation date: (5/24/00 4:10:33 PM)
 * @param bcTypeString java.lang.String
 * @exception java.lang.IllegalArgumentException The exception description.
 */
public BoundaryConditionType(String bcTypeString) throws java.lang.IllegalArgumentException {
	if (bcTypeString==null){
		throw new IllegalArgumentException("null argument");
	}
	if (bcTypeString.equalsIgnoreCase(DIRICHLET_STRING)){
		this.type = DIRICHLET;
	}else if (bcTypeString.equalsIgnoreCase(OLD_DIRICHLET_STRING)){
		this.type = DIRICHLET;
	}else if (bcTypeString.equalsIgnoreCase(NEUMANN_STRING)){
		this.type = NEUMANN;
	}else if (bcTypeString.equalsIgnoreCase(OLD_NEUMANN_STRING)){
		this.type = NEUMANN;
	}else if (bcTypeString.equalsIgnoreCase(PERIODIC_STRING)) {
		this.type = PERIODIC;
	}else{
		throw new IllegalArgumentException("'"+bcTypeString+"' is not a valid BoundaryConditionType");
	}
}


/**
 * This method was created in VisualAge.
 * @return boolean
 * @param object java.lang.Object
 */
public boolean compareEqual(Matchable object) {
	if (object == null){
		return false;
	}
	BoundaryConditionType bct = null;
	if (!(object instanceof BoundaryConditionType)){
		return false;
	}else{
		bct = (BoundaryConditionType)object;
	}
	//
	// compare boundaryTypes
	//
	if (bct.type != type){
		return false;
	}
	return true;
}


/**
 * Insert the method's description here.
 * Creation date: (4/2/01 3:34:43 PM)
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean equals(Object obj) {
	if (this == obj){
		return true;
	}
	if (obj == null){
		return false;
	}
	if (!(obj instanceof BoundaryConditionType)){
		return false;
	}
	BoundaryConditionType bct = (BoundaryConditionType)obj;
	//
	// compare boundaryTypes
	//
	if (bct.type != type){
		return false;
	}
	return true;
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.math.BoundaryCondition
 * @param bcString java.lang.String
 */
public static BoundaryConditionType fromString(String bcString) {
	if (bcString==null){
		return null;
	}
	return new BoundaryConditionType(bcString);
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.math.BoundaryCond
 */
public static BoundaryConditionType getDIRICHLET() {
	return new BoundaryConditionType(DIRICHLET);
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.math.BoundaryCond
 */
public static BoundaryConditionType getNEUMANN() {
	return new BoundaryConditionType(NEUMANN);
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.math.BoundaryCond
 */
public static BoundaryConditionType getPERIODIC() {
	return new BoundaryConditionType(PERIODIC);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/00 10:29:22 PM)
 * @return java.lang.String
 */
public String getUnits() {
	if (isDIRICHLET()){
		return "uM";
	}
	if (isNEUMANN()){
		return "uM um/sec";
	}		
	return null;
}


/**
 * Insert the method's description here.
 * Creation date: (4/4/2001 12:18:02 AM)
 * @return int
 */
public int hashCode() {
	return toString().hashCode();
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 */
public boolean isDIRICHLET() {
	return type==DIRICHLET;
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 */
public boolean isNEUMANN() {
	return type==NEUMANN;
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 */
public boolean isPERIODIC() {
	return type==PERIODIC;
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String toString() {
	if (isDIRICHLET()){
		return DIRICHLET_STRING;
	}
	if (isNEUMANN()){
		return NEUMANN_STRING;
	}
	if (isPERIODIC()) {
		return PERIODIC_STRING;
	}
	return null;
}
}
