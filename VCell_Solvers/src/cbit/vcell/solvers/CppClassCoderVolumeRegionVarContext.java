package cbit.vcell.solvers;
/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import java.util.*;

import org.vcell.expression.ExpressionFactory;
import org.vcell.expression.IExpression;

import cbit.vcell.math.*;
import cbit.vcell.parser.*;
import cbit.vcell.simulation.*;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class CppClassCoderVolumeRegionVarContext extends CppClassCoderAbstractVarContext {
	protected MembraneSubDomain membraneSubDomainsOwned[] = new MembraneSubDomain[0];

/**
 * VarContextCppCoder constructor comment.
 * @param name java.lang.String
 */
protected CppClassCoderVolumeRegionVarContext(CppCoderVCell argCppCoderVCell,
												Equation argEquation,
												CompartmentSubDomain argVolumeSubDomain,
												Simulation argSimulation, 
												String argParentClass) throws Exception
{
	super(argCppCoderVCell,argEquation,argVolumeSubDomain,argSimulation,argParentClass);
	
	Vector membraneSubDomainOwnedList = new Vector();
	MembraneSubDomain membranes[] = argSimulation.getMathDescription().getMembraneSubDomains(argVolumeSubDomain);
	for (int i = 0; i < membranes.length; i++){
		//
		// determine membrane "owner" for reasons of code generation (owner compartment is that which has a greater priority ... now this is arbitrary)
		//
		CompartmentSubDomain inside = membranes[i].getInsideCompartment();
		CompartmentSubDomain outside = membranes[i].getOutsideCompartment();
		CompartmentSubDomain membraneOwner = null;
		if (inside.getPriority() > outside.getPriority()){
			membraneOwner = inside;
		}else if (inside.getPriority() < outside.getPriority()){
			membraneOwner = outside;
		}else{ // inside.getPriority() == outside.getPriority()
			throw new RuntimeException("CompartmentSubDomains '"+inside.getName()+"' and '"+outside.getName()+"' have same priority ("+inside.getPriority()+")");
		}
		if (membraneOwner == argVolumeSubDomain){
			membraneSubDomainOwnedList.add(membranes[i]);
		}
	}
	this.membraneSubDomainsOwned = (MembraneSubDomain[])cbit.util.BeanUtils.getArray(membraneSubDomainOwnedList,MembraneSubDomain.class);
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.model.Feature
 */
public CompartmentSubDomain getCompartmentSubDomain() {
	return (CompartmentSubDomain)getSubDomain();
}


/**
 * Insert the method's description here.
 * Creation date: (6/22/2004 3:07:51 PM)
 * @return cbit.vcell.math.Variable[]
 */
protected Variable[] getRequiredVariables() throws Exception {

	//
	// 
	//
	Variable requiredVariables[] = super.getRequiredVariables();
	if (getEquation() instanceof PdeEquation){
		for (int i = 0;membraneSubDomainsOwned!=null && i < membraneSubDomainsOwned.length; i++){
			JumpCondition jumpCondition = membraneSubDomainsOwned[i].getJumpCondition((VolVariable)getEquation().getVariable());
			Enumeration enumJC = jumpCondition.getRequiredVariables(getSimulation().getMathDescription());
			requiredVariables = (Variable[])cbit.util.BeanUtils.addElements(requiredVariables,(Variable[])cbit.util.BeanUtils.getArray(enumJC,Variable.class));
		}
	}
	Vector uniqueVarList = new Vector();
	for (int i = 0; i < requiredVariables.length; i++){
		Variable var = requiredVariables[i];
		if (var instanceof InsideVariable){
			InsideVariable insideVar = (InsideVariable)var;
			VolVariable volVar = (VolVariable)getSimulation().getVariable(insideVar.getVolVariableName());
			if (!uniqueVarList.contains(volVar)){
				uniqueVarList.addElement(volVar);
			}	
		}else if (var instanceof OutsideVariable){
			OutsideVariable outsideVar = (OutsideVariable)var;
			VolVariable volVar = (VolVariable)getSimulation().getVariable(outsideVar.getVolVariableName());
			if (!uniqueVarList.contains(volVar)){
				uniqueVarList.addElement(volVar);
			}
		}else{
			if (!uniqueVarList.contains(var)){
				uniqueVarList.addElement(var);
			}
		}
	}

	return (Variable[])cbit.util.BeanUtils.getArray(uniqueVarList,Variable.class);
}


/**
 * This method was created by a SmartGuide.
 * @param out java.io.PrintWriter
 */
protected void writeConstructor(java.io.PrintWriter out) throws Exception {
	out.println(getClassName()+"::"+getClassName()+"(Feature *Afeature,CString AspeciesName)");
	out.println(": "+getParentClassName()+"(Afeature,AspeciesName)");
	out.println("{");
	try {
		IExpression ic = getEquation().getInitialExpression();
		ic.bindExpression(getSimulation());
		double value = ic.evaluateConstant();
		out.println("   initialValue = new double;");
		out.println("   *initialValue = "+value+";");
	}catch (Exception e){
		out.println("   initialValue = NULL;");
	}	
	out.println("");
	Variable requiredVariables[] = getRequiredVariables();
	for (int i = 0; i < requiredVariables.length; i++){
		Variable var = requiredVariables[i];
		if (var instanceof VolVariable || var instanceof MemVariable ||
			var instanceof VolumeRegionVariable || var instanceof MembraneRegionVariable){
			out.println("   var_"+var.getName()+" = NULL;");
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
	out.println(" public:");
	out.println("    "+getClassName() + "(Feature *feature, CString speciesName);");
	out.println("    virtual boolean resolveReferences(Simulation *sim);");

	try {
		IExpression ic = getEquation().getInitialExpression();
		ic.bindExpression(getSimulation());
		double value = ic.evaluateConstant();
	}catch (Exception e){
		out.println("    virtual double getInitialValue(long volumeIndex);");
	}
	out.println("    virtual double getUniformRate(VolumeRegion *region);");
	out.println("    virtual double getReactionRate(long volumeIndex);");
	out.println("    virtual void getFlux(MembraneElement *element,double *inFlux, double *outFlux);");
	out.println(" private:");
	Variable requiredVariables[] = getRequiredVariables();
	for (int i = 0; i < requiredVariables.length; i++){
		Variable var = requiredVariables[i];
		if (var instanceof VolVariable){
			out.println("    VolumeVariable      *var_"+var.getName()+";");
		}else if (var instanceof MemVariable){
			out.println("    MembraneVariable    *var_"+var.getName()+";");
		}else if (var instanceof MembraneRegionVariable){
			out.println("    MembraneRegionVariable    *var_"+var.getName()+";");
		}else if (var instanceof VolumeRegionVariable){
			out.println("    VolumeRegionVariable    *var_"+var.getName()+";");
		}else if (var instanceof ReservedVariable){
		}else if (var instanceof Constant){
		}else if (var instanceof Function){
		}else{
			throw new Exception("unknown identifier type '"+var.getClass().getName()+"' for identifier: "+var.getName());
		}	
	}		  	
	out.println("};");
}


/**
 * This method was created by a SmartGuide.
 * @param out java.io.PrintWriter
 */
protected void writeGetFlux(java.io.PrintWriter out, String functionName) throws Exception {
	//
	// Explanation of PRIORITIES and INSIDE/OUTSIDE wrt Code Generation:
	// -----------------------------------------------------------------
	//
	// due to code generation requirements, the compartment with the higher priority must be the "inside" compartment
	// and the "inside" compartment is where the flux is defined in the C++ library.
	//
	//
	// The math description specifies "inside" and "outside" compartments locally for each membrane
	//
	//     MembraneSubDomain inside_compartment outside_compartment {
	//         ...
	//     }
	//
	// which can contradict the priority-based determination of inside-outside.
	//
	// in these cases:
	//   1) the "influx" and "outflux" expressions must be reversed, and
	//   2) the var_INSIDE and var_OUTSIDE variable definitions must be exchanged (substituted)
	//
	
	out.println("void "+getClassName()+"::"+functionName+"(MembraneElement *element,double *inFlux, double *outFlux)");
	out.println("{");
	
	if (getEquation() instanceof PdeEquation){

		//
		// if zero or one membranes, write out single inFlux/outFlux expression
		//
		if (membraneSubDomainsOwned.length==0){
			out.println("   *inFlux = 0.0;");
			out.println("   *outFlux = 0.0;");
		}else if (membraneSubDomainsOwned.length==1){
			boolean bFlipInsideOutside = (membraneSubDomainsOwned[0].getOutsideCompartment() == getCompartmentSubDomain());
			out.println("   // for this membrane, MathDescription defines inside='"+membraneSubDomainsOwned[0].getInsideCompartment().getName()+"', outside='"+membraneSubDomainsOwned[0].getOutsideCompartment().getName()+"'");
			out.println("   // '"+membraneSubDomainsOwned[0].getInsideCompartment().getName()+"' has priority="+membraneSubDomainsOwned[0].getInsideCompartment().getPriority()+", "+
			                  "'"+membraneSubDomainsOwned[0].getOutsideCompartment().getName()+"' has priority="+membraneSubDomainsOwned[0].getOutsideCompartment().getPriority());
			if (bFlipInsideOutside){
				out.println("   // **** relative priorities CONTRADICT MathDescription convension (insidePriority < outsidePriority) ... must flip definitions");
			}else{
				out.println("   // :-)  Priorities are consistent (insidePriority > outsidePriority)");
			}
			out.println("");
			
			JumpCondition jumpCondition = membraneSubDomainsOwned[0].getJumpCondition((VolVariable)getVariable());
			IExpression inFluxExp = jumpCondition.getInFluxExpression();
			IExpression outFluxExp = jumpCondition.getOutFluxExpression();
			IExpression inFluxExp_substituted = getSimulation().substituteFunctions(inFluxExp).flatten();
			IExpression outFluxExp_substituted = getSimulation().substituteFunctions(outFluxExp).flatten();
			//
			// get totalExpression (composite expression to combine symbols)
			// then write out dependencies
			//
			IExpression totalExpression = ExpressionFactory.add(inFluxExp_substituted, outFluxExp_substituted);
			writeMembraneFunctionDeclarations(out,"element",totalExpression,bFlipInsideOutside,"   ");
			if (bFlipInsideOutside){
				out.println("   *inFlux = "+outFluxExp_substituted.infix_C()+";  // *****  flux convension reversed, uses 'outFlux' from MathDescription");
				out.println("   *outFlux = "+inFluxExp_substituted.infix_C()+";  // *****  flux convension reversed, uses 'inFlux' from MathDescription");
			}else{
				out.println("   *inFlux = "+inFluxExp_substituted.infix_C()+";");
				out.println("   *outFlux = "+outFluxExp_substituted.infix_C()+";");
			}
		}else if (membraneSubDomainsOwned.length>1){
			//
			// must choose which membrane at runtime
			//
			out.println("   Feature *outsideFeature = element->region->getRegionOutside()->getFeature();");
			out.println("   int outsideHandle = outsideFeature->getHandle();");
			out.println("   Feature *insideFeature = element->region->getRegionInside()->getFeature();");
			out.println("   int insideHandle = insideFeature->getHandle();");
			//out.println("   printf(\"getFlux(index=%ld, insideHandle=%d, outsideHandle=%ld), MembraneElement outside feature = '%s'\\n\",element->index,insideHandle,outsideHandle,outsideFeature->getName());");
			out.println("   switch(outsideHandle){");
			for (int i = 0; i < membraneSubDomainsOwned.length; i++){
				cbit.vcell.geometry.GeometrySpec geoSpec = getSimulation().getMathDescription().getGeometry().getGeometrySpec();
				boolean bFlipInsideOutside = (membraneSubDomainsOwned[i].getOutsideCompartment() == getCompartmentSubDomain());
				cbit.vcell.geometry.SubVolume outsideSubVolume = null;
				if (bFlipInsideOutside){
					outsideSubVolume = geoSpec.getSubVolume(membraneSubDomainsOwned[i].getInsideCompartment().getName());
				}else{
					outsideSubVolume = geoSpec.getSubVolume(membraneSubDomainsOwned[i].getOutsideCompartment().getName());
				}
				out.println("      case "+outsideSubVolume.getHandle()+": {  // for outside subVolume '"+outsideSubVolume.getName()+"'");
				out.println("         // for this membrane, MathDescription defines inside='"+membraneSubDomainsOwned[i].getInsideCompartment().getName()+"', outside='"+membraneSubDomainsOwned[i].getOutsideCompartment().getName()+"'");
				out.println("         // '"+membraneSubDomainsOwned[i].getInsideCompartment().getName()+"' has priority="+membraneSubDomainsOwned[i].getInsideCompartment().getPriority()+", "+
				                        "'"+membraneSubDomainsOwned[i].getOutsideCompartment().getName()+"' has priority="+membraneSubDomainsOwned[i].getOutsideCompartment().getPriority());
				if (bFlipInsideOutside){
					out.println("         // **** relative priorities CONTRADICT MathDescription convension (insidePriority < outsidePriority) ... must flip definitions");
				}else{
					out.println("         // :-)  Priorities are consistent (insidePriority > outsidePriority)");
				}
				out.println("");
				
				JumpCondition jumpCondition = membraneSubDomainsOwned[i].getJumpCondition((VolVariable)getVariable());
				IExpression inFluxExp = jumpCondition.getInFluxExpression();
				IExpression outFluxExp = jumpCondition.getOutFluxExpression();
				IExpression inFluxExp_substituted = getSimulation().substituteFunctions(inFluxExp).flatten();
				IExpression outFluxExp_substituted = getSimulation().substituteFunctions(outFluxExp).flatten();
				
				//
				// get totalExpression (composite expression to combine symbols)
				// then write out dependencies
				//
				IExpression totalExpression = ExpressionFactory.add(inFluxExp_substituted, outFluxExp_substituted);
				writeMembraneFunctionDeclarations(out,"element",totalExpression,bFlipInsideOutside,"         ");
				if (bFlipInsideOutside){
					out.println("         *inFlux = "+outFluxExp_substituted.infix_C()+";  // *****  flux convension reversed, uses 'outFlux' from MathDescription");
					out.println("         *outFlux = "+inFluxExp_substituted.infix_C()+";  // *****  flux convension reversed, uses 'inFlux' from MathDescription");
				}else{
					out.println("         *inFlux = "+inFluxExp_substituted.infix_C()+";");
					out.println("         *outFlux = "+outFluxExp_substituted.infix_C()+";");
				}
				out.println("         break;");
				out.println("      }");
			}
			out.println("      default: {");
			out.println("         printf(\"getFlux(index=%ld, insideHandle=%d, outsideHandle=%ld), MembraneElement outside feature = '%s'\\n\",element->index,insideHandle,outsideHandle,outsideFeature->getName());");
			out.println("         throw \"failed to match feature handle in "+getClassName()+"::"+functionName+"\";");
			out.println("      }");
			out.println("    }");
		}
	}else{
		out.println("   *inFlux = 0.0;");
		out.println("   *outFlux = 0.0;");
	}
	out.println("}");
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
	writeVolumeRegionFunction(out,"getUniformRate", ((VolumeRegionEquation)getEquation()).getUniformRateExpression());
	out.println("");
	writeVolumeFunction(out,"getReactionRate", ((VolumeRegionEquation)getEquation()).getVolumeRateExpression());
	out.println("");
	writeGetFlux(out,"getFlux");
	out.println("");
	try {
		double value = getEquation().getInitialExpression().evaluateConstant();
	}catch (Exception e){
		writeVolumeFunction(out,"getInitialValue", getEquation().getInitialExpression());
	}
	out.println("");
}
}