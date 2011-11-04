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

import java.io.*;

import org.vcell.util.document.VCellSoftwareVersion;
import org.vcell.util.document.Version;
import org.vcell.util.document.VersionInfo;
import org.vcell.util.document.VersionableType;

/**
 * This class was generated by a SmartGuide.
 * 
 */
public class ModelInfo implements Serializable,VersionInfo {
	private Version version = null;
	private VCellSoftwareVersion softwareVersion = null;
/**
 * This method was created in VisualAge.
 * @param argVersion cbit.sql.Version
 */
public ModelInfo(Version argVersion,VCellSoftwareVersion softwareVersion) {
	super();
	this.version = argVersion;
	this.softwareVersion = softwareVersion;
}
/**
 * Insert the method's description here.
 * Creation date: (1/25/01 12:24:41 PM)
 * @return boolean
 * @param object java.lang.Object
 */
public boolean equals(Object object) {
	if (object instanceof ModelInfo){
		if (!getVersion().getVersionKey().equals(((ModelInfo)object).getVersion().getVersionKey())){
			return false;
		}
		return true;
	}
	return false;
}
/**
 * This method was created in VisualAge.
 * @return cbit.sql.Version
 */
public Version getVersion() {
	return version;
}
/**
 * Insert the method's description here.
 * Creation date: (1/25/01 12:28:06 PM)
 * @return int
 */
public int hashCode() {
	return getVersion().getVersionKey().hashCode();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 */
public String toString() {
		return "ModelInfo(Version="+version+")";
}
public VersionableType getVersionType() {
	return VersionableType.Model;
}
public VCellSoftwareVersion getSoftwareVersion() {
	return softwareVersion;
}
}
