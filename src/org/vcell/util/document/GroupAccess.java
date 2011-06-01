/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.util.document;

import java.math.BigDecimal;
import java.io.*;

import org.vcell.util.Matchable;

/**
 * This class was generated by a SmartGuide.
 * 
 */
public abstract class GroupAccess implements Serializable, Matchable {
	
	private java.math.BigDecimal    groupid		= null;
	
	public static final BigDecimal GROUPACCESS_ALL  = new BigDecimal(0);
	public static final BigDecimal GROUPACCESS_NONE = new BigDecimal(1);
/**
 * Insert the method's description here.
 * Creation date: (11/15/2001 3:36:17 PM)
 */
protected GroupAccess(java.math.BigDecimal parmGroupid) {
		groupid = parmGroupid;
	}
/**
 * Insert the method's description here.
 * Creation date: (11/15/2001 4:51:40 PM)
 * @return java.math.BigDecimal
 */
public static final java.math.BigDecimal calculateHash(KeyValue[] userRefs,boolean[] isHiddenFromOwner) {
	//
	// Calculate hash for given set of
	// GroupTable.userRef and GroupTable.isHiddenFromOwner
	// userRefs[x] and isHiddenFromOwner[x] must be related to each other
	//
	if(userRefs == null || isHiddenFromOwner == null || (userRefs.length != isHiddenFromOwner.length)){
		throw new IllegalArgumentException("Improper arguments for calulateHash");
	}
	//
	// Create Strings to generate hash by concatenating userRef("12345") and isHiddenFromOwner("true","false")
	//
	String[] userRefsVals = new String[userRefs.length];
	for(int c = 0;c < userRefs.length;c+= 1){
		userRefsVals[c] = userRefs[c].toString()+isHiddenFromOwner[c];
	}
	//
	// Sort generated Strings so we have a standard regardless of the order
	// passed in userRef[]
	//
	java.util.Arrays.sort(userRefsVals);
	//
	java.util.zip.CRC32 hashCRC = new java.util.zip.CRC32();
	hashCRC.reset();
	//
	// Calculate hash from sorted strings
	//
	for(int c = 0;c < userRefsVals.length;c+= 1){
		byte[] keyStringBytes = userRefsVals[c].getBytes();
		for(int i = 0;i < keyStringBytes.length;i+= 1){
			hashCRC.update(keyStringBytes[i]);
		}
	}
	return new java.math.BigDecimal(hashCRC.getValue());
}
/**
 * This method was created in VisualAge.
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean compareEqual(Matchable obj) {
	if (obj == this){
		return true;
	}
	if (obj instanceof GroupAccess){
		if (((GroupAccess)obj).groupid.equals(this.groupid)){
			return true;
		}
	}
	return false;
}
/**
 * Insert the method's description here.
 * Creation date: (1/10/2002 4:31:39 PM)
 * @return java.lang.String
 */
public abstract String getDescription();
/**
 * This method was created in VisualAge.
 * @return int
 */
public java.math.BigDecimal getGroupid() {
	return groupid;
}
/**
 * This method was created in VisualAge.
 * @return boolean
 */
public abstract boolean isMember(User user);
}
