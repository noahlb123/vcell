/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.mapping;
import java.util.List;

import org.vcell.util.Issue;
import org.vcell.util.Issue.IssueCategory;
import org.vcell.util.IssueContext;
import org.vcell.util.Matchable;

import cbit.vcell.geometry.CompartmentSubVolume;
import cbit.vcell.geometry.Geometry;
import cbit.vcell.geometry.SubVolume;
import cbit.vcell.geometry.SurfaceClass;
import cbit.vcell.model.Feature;
import cbit.vcell.model.ModelUnitSystem;
import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class FeatureMapping extends StructureMapping {

//	public static boolean bTotalVolumeCorrectionBug = false;
//	public static boolean bTotalVolumeCorrectionBugExercised = false;

/**
 * FeatureMapping constructor comment.
 * @param feature cbit.vcell.model.Feature
 * @param geoContext cbit.vcell.mapping.GeometryContext
 * @exception java.lang.Exception The exception description.
 */
public FeatureMapping(FeatureMapping featureMapping, SimulationContext argSimulationContext,Geometry newGeometry, ModelUnitSystem argModelUnitSystem) {
	super(featureMapping, argSimulationContext,newGeometry, argModelUnitSystem);
}


/**
 * FeatureMapping constructor comment.
 * @param feature cbit.vcell.model.Feature
 * @param geoContext cbit.vcell.mapping.GeometryContext
 * @exception java.lang.Exception The exception description.
 */
public FeatureMapping(Feature feature, SimulationContext argSimulationContext, ModelUnitSystem argModelUnitSystem) {
	super(feature, argSimulationContext, argModelUnitSystem);
	double volume = 50000.0;
	try {
		setParameters(new StructureMappingParameter[] {
				new StructureMappingParameter(DefaultNames[ROLE_Size], new Expression(volume), ROLE_Size, modelUnitSystem.getVolumeUnit()),
				new StructureMappingParameter(DefaultNames[ROLE_VolumePerUnitArea], null, ROLE_VolumePerUnitArea, modelUnitSystem.getLengthUnit()),
				new StructureMappingParameter(DefaultNames[ROLE_VolumePerUnitVolume], null, ROLE_VolumePerUnitVolume, modelUnitSystem.getInstance_DIMENSIONLESS()),			
		});
	}catch (java.beans.PropertyVetoException e){
		e.printStackTrace(System.out);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created in VisualAge.
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean compareEqual(Matchable obj) {

	FeatureMapping fm = null;
	if (!(obj instanceof FeatureMapping)){
		return false;
	}
	fm = (FeatureMapping)obj;

	if (!compareEqual0(fm)){
		return false;
	}
	
	return true;
}

/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.model.Feature
 */
public Feature getFeature() {
	return (Feature)getStructure();
}

/**
 * This method was created by a SmartGuide.
 * @return double
 */
public StructureMappingParameter getVolumePerUnitVolumeParameter() {
	return getParameterFromRole(ROLE_VolumePerUnitVolume);
}

/**
 * This method was created by a SmartGuide.
 * @return double
 */
public StructureMappingParameter getVolumePerUnitAreaParameter() {
	return getParameterFromRole(ROLE_VolumePerUnitArea);
}

/**
 * TotalConservationCorrection is the term that takes local units (micro-molar) to either volume normalized micro-molar or area normalized molecules/sq-um
 * @return cbit.vcell.parser.Expression
 */
public Expression getNormalizedConcentrationCorrection(SimulationContext simulationContext, UnitFactorProvider unitFactorProvider) throws ExpressionException, MappingException {
	if (getGeometryClass() instanceof CompartmentSubVolume){
		if (simulationContext.getGeometryContext().isAllSizeSpecifiedPositive()) {
			//
			// everything mapped to micro-molar : just need size
			//
			Expression exp = new Expression(getSizeParameter(),simulationContext.getNameScope());
			return exp;
		} else {
			throw new MappingException("\nIn non-spatial application '" + simulationContext.getName() + "', " +
					"size of structure '" + getStructure().getName() + "' must be assigned a " +
					"positive value if referenced in the model.\n\nPlease go to 'Structure Mapping' tab to check the size.");
		}
	}else if (getGeometryClass() instanceof SubVolume){
		//
		// everything mapped to micro-molar : just need volume fraction
		//
		Expression exp = new Expression(getVolumePerUnitVolumeParameter(),simulationContext.getNameScope());
		return exp;
	}else if (getGeometryClass() instanceof SurfaceClass){
		//
		// everything mapped to molecules/sq-um : need volume/area fraction and KMOLE
		//
		Expression unitFactor = unitFactorProvider.getUnitFactor(modelUnitSystem.getMembraneSubstanceUnit().divideBy(modelUnitSystem.getVolumeSubstanceUnit()));
		Expression exp = Expression.mult(new Expression(getVolumePerUnitAreaParameter(),simulationContext.getNameScope()),unitFactor);
		return exp;
	}else{
		throw new RuntimeException("structure "+getStructure().getName()+" not mapped");
	}
}

/**
 * TotalVolumeCorrection is the term that takes local units to volume normalized micro-Molar
 * @return cbit.vcell.parser.Expression
 * @throws MappingException 
 */
@Override
public Expression getStructureSizeCorrection(SimulationContext simulationContext, UnitFactorProvider unitFactorProvider) throws ExpressionException, MappingException {
	return getNormalizedConcentrationCorrection(simulationContext, unitFactorProvider);
}

/**
 * The hasListeners method was generated to support the vetoPropertyChange field.
 */
public synchronized boolean hasListeners(java.lang.String propertyName) {
	return getVetoPropertyChange().hasListeners(propertyName);
}


/**
 * Insert the method's description here.
 * Creation date: (2/19/2002 1:20:30 PM)
 */
public void refreshDependencies() {
	super.refreshDependencies();
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String toString() {
	return getClass().getName()+"@"+Integer.toHexString(hashCode())+" "+getFeature().getName();
}


@Override
public StructureMappingParameter getUnitSizeParameter() {
	if (getGeometryClass() instanceof SubVolume){
		return getVolumePerUnitVolumeParameter();
	}else if (getGeometryClass() instanceof SurfaceClass){
		return getVolumePerUnitAreaParameter();
	}
	return null;
}

public void gatherIssues(IssueContext issueContext, List<Issue> issueVector) {
	super.gatherIssues(issueContext, issueVector);
	if (simulationContext.getGeometry().getDimension() == 0) {
		if (!simulationContext.getGeometryContext().isAllSizeSpecifiedPositive()) {
			String tooltip = "\nIn non-spatial application, size of structure '" + getStructure().getName() + 
					"' must be assigned a positive value if referenced in the model.";
			String message = "In non-spatial application, size of structure '" + getStructure().getName() + "' must be assigned a positive value.";
			issueVector.add(new Issue(this, issueContext, IssueCategory.StructureMappingSizeParameterNotSet, message, Issue.SEVERITY_WARNING));
		}
	}
}

}
