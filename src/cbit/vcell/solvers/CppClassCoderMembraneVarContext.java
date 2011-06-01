/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.solvers;


import cbit.vcell.math.*;
import cbit.vcell.parser.*;
import cbit.vcell.solver.*;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class CppClassCoderMembraneVarContext extends CppClassCoderAbstractVarContext {
/**
 * VarContextCppCoder constructor comment.
 * @param name java.lang.String
 */
protected CppClassCoderMembraneVarContext(CppCoderVCell argCppCoderVCell,
												Equation argEquation,
												MembraneSubDomain argMembraneSubDomain,
												SimulationJob argSimulationJob, 
												String argParentClass) throws Exception
{
	super(argCppCoderVCell,argEquation,argMembraneSubDomain,argSimulationJob,argParentClass);
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.model.Feature
 */
public CompartmentSubDomain getInsideCompartment() {
	if (isFlippedInsideOutside((MembraneSubDomain)getSubDomain())) {
		return ((MembraneSubDomain)getSubDomain()).getOutsideCompartment();
	} else {
		return ((MembraneSubDomain)getSubDomain()).getInsideCompartment();
	}
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.math.MembraneSubDomain
 */
public MembraneSubDomain getMembraneSubDomain() {
	return (MembraneSubDomain)getSubDomain();
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.model.Feature
 */
public CompartmentSubDomain getOutsideCompartment() {
	if (isFlippedInsideOutside((MembraneSubDomain)getSubDomain())) {
		return ((MembraneSubDomain)getSubDomain()).getInsideCompartment();
	} else {
		return ((MembraneSubDomain)getSubDomain()).getOutsideCompartment();
	}
}


/**
 * This method was created by a SmartGuide.
 * @param out java.io.PrintWriter
 */
protected void writeConstructor(java.io.PrintWriter out) throws Exception {
	out.println(getClassName()+"::"+getClassName()+"(Feature *Afeature, string& AspeciesName)");
	out.println(": "+getParentClassName()+"(Afeature,AspeciesName)");
	out.println("{");
	try {
		Expression ic = getEquation().getInitialExpression();
		ic.bindExpression(simulationJob.getSimulationSymbolTable());
		double value = ic.evaluateConstant();
		out.println("\tinitialValue = new double;");
		out.println("\t*initialValue = "+value+";");
	}catch (Exception e){
		out.println("\tinitialValue = NULL;");
	}	
	out.println();
	Variable requiredVariables[] = getRequiredVariables();
	for (int i = 0; i < requiredVariables.length; i++){
		Variable var = requiredVariables[i];
		if (var instanceof VolVariable
				|| var instanceof MemVariable
				|| var instanceof VolumeRegionVariable
				|| var instanceof MembraneRegionVariable){
			out.println("\t" + CppClassCoder.getEscapedFieldVariableName_C(var.getName()) + " = NULL;");
		}
	}		  	
	out.println("}");
}


/**
 * This method was created by a SmartGuide.
 * @param printWriter java.io.PrintWriter
 */
public void writeDeclaration(java.io.PrintWriter out) throws Exception {
	out.println("//---------------------------------------------");
	out.println("//  class " + getClassName());
	out.println("//---------------------------------------------");

	out.println("class " + getClassName() + " : public " + getParentClassName());
	out.println("{");
	out.println("public:");
	out.println("\t"+getClassName() + "(Feature *feature, string& speciesName);");
	out.println("\tvirtual void resolveReferences(Simulation *sim);");

	BoundaryConditionType bc = null;
	int dimension = simulationJob.getSimulation().getMathDescription().getGeometry().getDimension();
	if (getEquation() instanceof PdeEquation){
		PdeEquation pdeEqu = (PdeEquation)getEquation();
		if (pdeEqu.getBoundaryXm()!=null){
			bc = getMembraneSubDomain().getBoundaryConditionXm();
			if (bc.isDIRICHLET()){
				out.println("\tvirtual double getXmBoundaryValue(MembraneElement *memElement);");
			}else if (bc.isNEUMANN()){
				out.println("\tvirtual double getXmBoundaryFlux(MembraneElement *memElement);");
			}
		}
		if (pdeEqu.getBoundaryXp()!=null){			
			bc = getMembraneSubDomain().getBoundaryConditionXp();
			if (bc.isDIRICHLET()){
				out.println("\tvirtual double getXpBoundaryValue(MembraneElement *memElement);");
			}else if (bc.isNEUMANN()){
				out.println("\tvirtual double getXpBoundaryFlux(MembraneElement *memElement);");
			}
		}
		if (pdeEqu.getVelocityX() != null) {
			out.println("\tvirtual double getConvectionVelocity_X(MembraneElement *memElement);");
		}
		if (dimension>1){
			if (pdeEqu.getBoundaryYm()!=null){
				bc = getMembraneSubDomain().getBoundaryConditionYm();
				if (bc.isDIRICHLET()){
					out.println("\tvirtual double getYmBoundaryValue(MembraneElement *memElement);");
				}else if (bc.isNEUMANN()){
					out.println("\tvirtual double getYmBoundaryFlux(MembraneElement *memElement);");
				}
			}	
			if (pdeEqu.getBoundaryYp()!=null){
				bc = getMembraneSubDomain().getBoundaryConditionYp();
				if (bc.isDIRICHLET()){
					out.println("\tvirtual double getYpBoundaryValue(MembraneElement *memElement);");
				}else if (bc.isNEUMANN()){
					out.println("\tvirtual double getYpBoundaryFlux(MembraneElement *memElement);");
				}
			}
			if (pdeEqu.getVelocityY() != null) {
				out.println("\tvirtual double getConvectionVelocity_Y(MembraneElement *memElement);");
			}			
		}
		if (dimension==3){	
			if (pdeEqu.getBoundaryZm()!=null){
				bc = getMembraneSubDomain().getBoundaryConditionZm();
				if (bc.isDIRICHLET()){
					out.println("\tvirtual double getZmBoundaryValue(MembraneElement *memElement);");
				}else if (bc.isNEUMANN()){
					out.println("\tvirtual double getZmBoundaryFlux(MembraneElement *memElement);");
				}
			}	
			if (pdeEqu.getBoundaryZp()!=null){
				bc = getMembraneSubDomain().getBoundaryConditionZp();
				if (bc.isDIRICHLET()){
					out.println("\tvirtual double getZpBoundaryValue(MembraneElement *memElement);");
				}else if (bc.isNEUMANN()){
					out.println("\tvirtual double getZpBoundaryFlux(MembraneElement *memElement);");
				}
			}
			if (pdeEqu.getVelocityZ() != null) {
				out.println("\tvirtual double getConvectionVelocity_Z(MembraneElement *memElement);");
			}			
		}	
	}		
	try {
		Expression ic = getEquation().getInitialExpression();
		ic.bindExpression(simulationJob.getSimulationSymbolTable());
		double value = ic.evaluateConstant();
	}catch (Exception e){
		out.println("\tvirtual double getInitialValue(MembraneElement *memElement);");
	}
	out.println("protected:");
	out.println("\tvirtual double getMembraneReactionRate(MembraneElement *memElement);");
	out.println("\tvirtual double getMembraneDiffusionRate(MembraneElement *memElement);");
	out.println();
	out.println("private:");
	Variable requiredVariables[] = getRequiredVariables();
	for (int i = 0; i < requiredVariables.length; i++){
		Variable var = requiredVariables[i];
		String mangledVarName = CppClassCoder.getEscapedFieldVariableName_C(var.getName());
		if (var instanceof VolVariable){
			out.println("\tVolumeVariable *" + mangledVarName + ";");
		}else if (var instanceof MemVariable){
			out.println("\tMembraneVariable *" + mangledVarName + ";");
		}else if (var instanceof MembraneRegionVariable){
			out.println("\tMembraneRegionVariable *" + mangledVarName + ";");
		}else if (var instanceof VolumeRegionVariable){
			out.println("\tVolumeRegionVariable *" + mangledVarName + ";");
		}else if (var instanceof ReservedVariable){
		}else if (var instanceof Constant){
		}else if (var instanceof Function){
		}else if (var instanceof RandomVariable){
			throw new RuntimeException("'" + SolverDescription.FiniteVolume.getDisplayLabel() + "'"
	  				+ " does not support RandomVariable. Please choose either '" 
	  				+ SolverDescription.FiniteVolumeStandalone.getDisplayLabel() + "' or '" + SolverDescription.SundialsPDE.getDisplayLabel() + "'");
		} else {
			throw new Exception("unknown identifier type '" + var.getClass().getName() + "' for identifier: " + var.getName());
		}	
	}		  	
	out.println("};");
}


/**
 * This method was created by a SmartGuide.
 * @param printWriter java.io.PrintWriter
 */
public void writeImplementation(java.io.PrintWriter out) throws Exception {
	out.println("//---------------------------------------------");
	out.println("//  class " + getClassName());
	out.println("//---------------------------------------------");
	writeConstructor(out);
	out.println("");
	writeResolveReferences(out);
	out.println("");
	boolean bFlippedInsideOutside = isFlippedInsideOutside(getMembraneSubDomain());
	writeMembraneFunction(out,"getMembraneReactionRate", getEquation().getRateExpression(),bFlippedInsideOutside);
	out.println("");
	if (getEquation() instanceof PdeEquation){
		PdeEquation pde = (PdeEquation)getEquation();
		writeMembraneFunction(out,"getMembraneDiffusionRate", pde.getDiffusionExpression(), bFlippedInsideOutside);
	}else{
		writeMembraneFunction(out,"getMembraneDiffusionRate", new Expression(0.0), bFlippedInsideOutside);
	}
	out.println("");
	MathDescription mathDesc = simulationJob.getSimulation().getMathDescription();
	int dimension = mathDesc.getGeometry().getDimension();
	if (getEquation() instanceof PdeEquation){
		PdeEquation pde = (PdeEquation)getEquation();
		BoundaryConditionType bc = getMembraneSubDomain().getBoundaryConditionXm();
		if (bc != null && (pde.getBoundaryXm()!=null)){
			if (bc.isDIRICHLET()){
				writeMembraneFunction(out,"getXmBoundaryValue",pde.getBoundaryXm(),bFlippedInsideOutside);
			}else if (bc.isNEUMANN()){
				writeMembraneFunction(out,"getXmBoundaryFlux", pde.getBoundaryXm(),bFlippedInsideOutside);
			}
		}	
		bc = getMembraneSubDomain().getBoundaryConditionXp();
		if (bc != null && (pde.getBoundaryXp()!=null)){
			if (bc.isDIRICHLET()){
				writeMembraneFunction(out,"getXpBoundaryValue",pde.getBoundaryXp(),bFlippedInsideOutside);
			}else if (bc.isNEUMANN()){
				writeMembraneFunction(out,"getXpBoundaryFlux", pde.getBoundaryXp(),bFlippedInsideOutside);
			}
		}
		if (dimension>1){
			bc = getMembraneSubDomain().getBoundaryConditionYm();
			if (bc != null && (pde.getBoundaryYm()!=null)){
				if (bc.isDIRICHLET()){
					writeMembraneFunction(out,"getYmBoundaryValue",pde.getBoundaryYm(),bFlippedInsideOutside);
				}else if (bc.isNEUMANN()){
					writeMembraneFunction(out,"getYmBoundaryFlux", pde.getBoundaryYm(),bFlippedInsideOutside);
				}
			}	
			bc = getMembraneSubDomain().getBoundaryConditionYp();
			if (bc != null && (pde.getBoundaryYp()!=null)){
				if (bc.isDIRICHLET()){
					writeMembraneFunction(out,"getYpBoundaryValue",pde.getBoundaryYp(),bFlippedInsideOutside);
				}else if (bc.isNEUMANN()){
					writeMembraneFunction(out,"getYpBoundaryFlux", pde.getBoundaryYp(),bFlippedInsideOutside);
				}
			}
		}
		if (dimension==3){		
			bc = getMembraneSubDomain().getBoundaryConditionZm();
			if (bc != null && (pde.getBoundaryZm()!=null)){
				if (bc.isDIRICHLET()){
					writeMembraneFunction(out,"getZmBoundaryValue",pde.getBoundaryZm(),bFlippedInsideOutside);
				}else if (bc.isNEUMANN()){
					writeMembraneFunction(out,"getZmBoundaryFlux", pde.getBoundaryZm(),bFlippedInsideOutside);
				}
			}	
			bc = getMembraneSubDomain().getBoundaryConditionZp();
			if (bc != null && (pde.getBoundaryZp()!=null)){
				if (bc.isDIRICHLET()){
					writeMembraneFunction(out,"getZpBoundaryValue",pde.getBoundaryZp(),bFlippedInsideOutside);
				}else if (bc.isNEUMANN()){
					writeMembraneFunction(out,"getZpBoundaryFlux", pde.getBoundaryZp(),bFlippedInsideOutside);
				}
			}
		}		
	}	
	try {
		double value = getEquation().getInitialExpression().evaluateConstant();
	}catch (Exception e){
		writeMembraneFunction(out,"getInitialValue", getEquation().getInitialExpression(),bFlippedInsideOutside);
	}
	out.println("");
}
}
