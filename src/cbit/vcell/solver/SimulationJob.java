/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.solver;
import cbit.vcell.field.FieldDataIdentifierSpec;

public class SimulationJob implements java.io.Serializable {
	private Simulation sim = null;
	private int jobIndex = -1;				// expect non-negative value.
	private FieldDataIdentifierSpec[] fieldDataIdentifierSpecs = null;
	private transient SimulationSymbolTable simulationSymbolTable = null;
	
/**
 * Insert the method's description here.
 * Creation date: (10/7/2005 4:50:05 PM)
 * @param masterSim cbit.vcell.solver.Simulation
 * @param jobIndex int
 */
public SimulationJob(Simulation argSim, int jobIndex, FieldDataIdentifierSpec[] argFDIS) {
	if (jobIndex<0){
		throw new RuntimeException("unexpected simulation jobIndex < 0");
	}
	sim = argSim;
	fieldDataIdentifierSpecs = argFDIS;
	this.jobIndex = jobIndex;
}

/**
 * Insert the method's description here.
 * Creation date: (10/14/2005 12:09:29 PM)
 * @return java.lang.String
 */
public static String createSimulationJobID(String simulationID, int jobIndex) {
	return simulationID+"_"+jobIndex+"_";
}


/**
 * Insert the method's description here.
 * Creation date: (9/20/2006 9:49:07 AM)
 * @return cbit.vcell.field.FieldDataIdentifier[]
 */
public FieldDataIdentifierSpec[] getFieldDataIdentifierSpecs() {
	return fieldDataIdentifierSpecs;
}


/**
 * Insert the method's description here.
 * Creation date: (10/7/2005 4:52:17 PM)
 * @return int
 */
public int getJobIndex() {
	return jobIndex;
}


/**
 * Insert the method's description here.
 * Creation date: (10/14/2005 12:09:29 PM)
 * @return java.lang.String
 */
public String getSimulationJobID() {
	return createSimulationJobID(sim.getSimulationID(),jobIndex);
}


/**
 * Insert the method's description here.
 * Creation date: (10/14/2005 11:45:42 AM)
 * @return cbit.vcell.server.VCDataIdentifier
 */
public VCSimulationDataIdentifier getVCDataIdentifier() {
	return new VCSimulationDataIdentifier(sim.getSimulationInfo().getAuthoritativeVCSimulationIdentifier(), jobIndex);
}


/**
 * Insert the method's description here.
 * Creation date: (10/7/2005 4:48:57 PM)
 * @return cbit.vcell.solver.Simulation
 */
public Simulation getSimulation() {
	return sim;
}


public final SimulationSymbolTable getSimulationSymbolTable() {
	if (simulationSymbolTable == null) {
		simulationSymbolTable = new SimulationSymbolTable(sim, jobIndex);
	}
	return simulationSymbolTable;
}
}
