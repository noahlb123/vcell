/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.export.server;

import java.util.*;
import cbit.vcell.export.server.*;
import java.io.*;
/**
 * Insert the type's description here.
 * Creation date: (10/19/2001 3:55:31 PM)
 * @author: Ion Moraru
 */
public class ExportLog implements Serializable {
	private ExportLogEntry[] exportLogEntries = new ExportLogEntry[0];
	private String simulationID = null;
/**
 * Insert the method's description here.
 * Creation date: (10/19/2001 3:56:22 PM)
 * @param exportLogFile java.io.File
 */
public ExportLog(org.vcell.util.document.KeyValue argSimulationRef,ExportLogEntry[] argExportLogEntries){

	this.simulationID = argSimulationRef.toString();
	this.exportLogEntries = argExportLogEntries;
}
/**
 * Insert the method's description here.
 * Creation date: (10/19/2001 3:56:22 PM)
 * @param exportLogFile java.io.File
 */
public ExportLog(File exportLogFile) throws FileNotFoundException, IOException {
	// if it seems it's the right stuff, try to read it
	if (exportLogFile != null && exportLogFile.exists() && exportLogFile.isFile() && exportLogFile.getName().endsWith(".export")) {
		LineNumberReader reader = new LineNumberReader(new FileReader(exportLogFile));
		Vector entries = new Vector();
		String line = reader.readLine();
		while (line != null) {
			entries.add(new ExportLogEntry(line));
			line = reader.readLine();
		}
		setExportLogEntries((ExportLogEntry[])org.vcell.util.BeanUtils.getArray(entries, ExportLogEntry.class));
		String fileName = exportLogFile.getName();
		String prefix = "SimID_";
		String suffix = ".export";
		if (fileName.indexOf(prefix)<0){
			throw new RuntimeException("unexpected filename = '"+fileName+"', should contain '"+prefix+"'");
		}
		if (fileName.indexOf(suffix)<0){
			throw new RuntimeException("unexpected filename = '"+fileName+"', should contain '"+suffix+"'");
		}
		this.simulationID = fileName.substring(fileName.lastIndexOf(prefix)+prefix.length(),fileName.lastIndexOf(suffix));
	}
}
/**
 * Insert the method's description here.
 * Creation date: (10/19/2001 4:35:12 PM)
 * @return cbit.vcell.export.server.ExportLogEntry[]
 */
public ExportLogEntry[] getExportLogEntries() {
	return exportLogEntries;
}
/**
 * Insert the method's description here.
 * Creation date: (10/24/01 3:34:22 PM)
 * @return java.lang.String
 */
private String getSimulationID() {
	return simulationID;
}
/**
 * Insert the method's description here.
 * Creation date: (10/29/2001 8:12:06 PM)
 * @return java.lang.String
 */
public String getSimulationIdentifier() {
	return "SimID_" + getSimulationID();
}
/**
 * Insert the method's description here.
 * Creation date: (10/24/01 4:22:59 PM)
 * @return cbit.sql.KeyValue
 */
public org.vcell.util.document.KeyValue getSimulationKey() {
	return new org.vcell.util.document.KeyValue(getSimulationID());
}
/**
 * Insert the method's description here.
 * Creation date: (10/19/2001 4:35:12 PM)
 * @param newExportLogEntries cbit.vcell.export.server.ExportLogEntry[]
 */
private void setExportLogEntries(ExportLogEntry[] newExportLogEntries) {
	exportLogEntries = newExportLogEntries;
}
}
