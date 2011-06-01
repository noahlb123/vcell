/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.model;
import cbit.vcell.parser.ExpressionException;
import java.util.*;

import org.vcell.util.Matchable;
import org.vcell.util.document.KeyValue;


public class Feature extends Structure
{
	public Membrane membrane = null;

public Feature(KeyValue key, String name) throws java.beans.PropertyVetoException {
	super(key);
	setName(name);
}


public Feature(String name) throws java.beans.PropertyVetoException {
	super(null);
	setName(name);
}


/**
 * This method was created in VisualAge.
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean compareEqual(Matchable obj) {
	Feature f = null;
	if (!(obj instanceof Feature)){
		return false;
	}
	f = (Feature)obj;

	if (!compareEqual0(f)){
		return false;
	}
	if ((getMembrane()!=null && f.getMembrane()==null) ||
		(getMembrane()==null && f.getMembrane()!=null)){
		return false;
	}
	if (getMembrane()!=null && !getMembrane().getName().equals(f.getMembrane().getName())){
		return false;
	}
	return true;
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 * @param structure cbit.vcell.model.Structure
 */
public boolean enclosedBy(Structure parentStructure){
	if (parentStructure == this){
		return true;
	}	
	if (getMembrane() != null){
		return getMembrane().enclosedBy(parentStructure);
	}	
	return false;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.model.Feature
 */
public Membrane getMembrane() {
	return membrane;
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.model.Structure
 */
public Structure getParentStructure() {
	return getMembrane();
}


/**
 * This method was created in VisualAge.
 * @return int
 */
public int getPriority() {
	int priority = 1;
	Feature feature = this;
	while (feature.membrane!=null){
		feature = feature.membrane.getOutsideFeature();
		priority++;
	}	
	return priority;
}

/**
 * Insert the method's description here.
 * Creation date: (12/8/2006 5:16:07 PM)
 */
/*public boolean isInnerMostFeature()
{
	Structure[] structures = this.getModel().getStructures();
	for(int i=0; i<structures.length; i++)
	{
		if(structures[i] instanceof Membrane)
		{
			if(((Membrane)structures[i]).getOutsideFeature().compareEqual(this))
				return false;
		}
	}
	return true;
}*/


/**
 * This method was created by a SmartGuide.
 * @param feature cbit.vcell.model.Feature
 */
public void setMembrane(Membrane membrane) {
	this.membrane = membrane;
	if (membrane != null && membrane.getInsideFeature()!=this){
		membrane.setInsideFeature(this);
	}
}


/**
 * This method was created in VisualAge.
 * @param structure cbit.vcell.model.Structure
 */
public void setParentStructure(Structure structure) throws ModelException {
	if (structure instanceof Membrane){
		Membrane membrane = (Membrane)structure;
		//
		// check for cyclic parenthood
		//
		Structure s = membrane.getParentStructure();
		while (s!=null){
			if (s == this){
				throw new ModelException("cannot make parent relationship cyclic");
			}
			s = s.getParentStructure();
		}
		setMembrane(membrane);
	}else{
		throw new ModelException("parent structure must be a Membrane");
	}
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String toString() {
	StringBuffer sb = new StringBuffer();
	
	sb.append("Feature@"+Integer.toHexString(hashCode())+"(name="+getName());
	
	if (membrane != null){ 
		sb.append(", membrane='"+membrane.getName()+"'");
	}
	sb.append(")");

	return sb.toString();
}



@Override
public int getDimension() {
	return 3;
}
}
