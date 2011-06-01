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

import cbit.vcell.parser.*;
import cbit.vcell.math.*;
import cbit.vcell.server.*;
/**
 * This type was created in VisualAge.
 */
public class MathMappingTest {
/**
 * This method was created in VisualAge.
 * @return cbit.vcell.mapping.MathMapping
 */
public static MathMapping getExample(int dimension) throws Exception {
	SimulationContext simContext = SimulationContextTest.getExample(dimension);
	GeometryContext geoContext = simContext.getGeometryContext();
	cbit.vcell.model.Model model = geoContext.getModel();
	ReactionContext reactionContext = simContext.getReactionContext();
	SpeciesContextSpec scs;
	double Is = 0.12;
	double Ih = 1.0;
	double R = 500.0;
	double r = 170.0;
	double Iw = 0.015;
	double I1h = 0.84;
	double I1w = 0.8;
	double dI = 0.025;
	double RI_tot = 0.01;
	double RCact_tot = 0.01;
	double RCinh_tot = 0.01;
	double B_tot = 1400;
	double dact = 1.2;
	double dinh = 1.5;
	double K = 100;
	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("IP3"));
//	scs.setInitialCondition(new Expression("Is + "*(1+" + Ih + "*exp(-(" + R + "-sqrt(x*x+y*y+z*z))/" + R + "/" + Iw + "))+(x<-" + r + ")*(" + I1h + "*exp(-(" + R + "-sqrt(x*x+y*y+z*z))/" + R + "/" + Iw + "))*exp(-pow((sqrt(y*y+z*z)/" + R + "/" + I1w + "),4));");
	scs.getInitialConditionParameter().setExpression(new Expression(Is+"+ exp(-(" + R + "- sqrt(x*x+y*y+z*z))/"+R+"/"+Iw+") * ("+Is+"*"+Ih+" + (x<-" + r + ")*(" + I1h + " * exp(-pow(sqrt(y*y+z*z)/" + R + "/" + I1w + ",4))));"));
//	scs.setConstant(true);

	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("Ca_ER"));
	scs.setConstant(true);

/*
	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("Ract"));
	scs.setInitialConditionExpression(RCact_tot + "*" + dact + "/(Ca_init+" + dact + ");", null);

	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("RactCa"));
	scs.setInitialConditionExpression(RCact_tot + "*Ca_init/(Ca_init+" + dact + ");", null);

	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("Rinh"));
	scs.setInitialConditionExpression(RCinh_tot + "*" + dinh + "/(Ca_init+" + dinh + ");", null);

	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("RinhCa"));
	scs.setInitialConditionExpression(RCinh_tot + "*Ca_init/(Ca_init+" + dinh + ");", null);

	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("B"));
	scs.setInitialConditionExpression(B_tot + "*" + K + "/(Ca_init+" + K + ");", null);

	scs = reactionContext.getSpeciesContextSpec(model.getSpeciesContext("CaB"));
	scs.setInitialConditionExpression(B_tot + "*Ca_init/(Ca_init+" + K + ");", null);
*/
	MathMapping mathMapping = null;
	MathDescription mathDesc = null;
	java.util.Date startDate = null;
	java.util.Date endDate = null;

	cbit.vcell.parser.Expression.resetCounters();
	startDate = new java.util.Date();
	mathMapping = new MathMapping(simContext);
	mathDesc = mathMapping.getMathDescription();
	endDate = new java.util.Date();
	System.out.println("\ntime for math mapping = "+((endDate.getTime()-startDate.getTime())/1000.0)+" seconds");
	cbit.vcell.parser.Expression.showCounters();
	
	cbit.vcell.parser.Expression.resetCounters();
	startDate = new java.util.Date();
	mathMapping = new MathMapping(simContext);
	mathDesc = mathMapping.getMathDescription();
	endDate = new java.util.Date();
	System.out.println("\ntime for math mapping = "+((endDate.getTime()-startDate.getTime())/1000.0)+" seconds");
	cbit.vcell.parser.Expression.showCounters();
	
	cbit.vcell.parser.Expression.resetCounters();
	startDate = new java.util.Date();
	mathMapping = new MathMapping(simContext);
	mathDesc = mathMapping.getMathDescription();
	endDate = new java.util.Date();
	System.out.println("\ntime for math mapping = "+((endDate.getTime()-startDate.getTime())/1000.0)+" seconds");
	cbit.vcell.parser.Expression.showCounters();
	
	cbit.vcell.parser.Expression.resetCounters();
	startDate = new java.util.Date();
	mathMapping = new MathMapping(simContext);
	mathDesc = mathMapping.getMathDescription();
	endDate = new java.util.Date();
	System.out.println("\ntime for math mapping = "+((endDate.getTime()-startDate.getTime())/1000.0)+" seconds");
	cbit.vcell.parser.Expression.showCounters();
	
	return mathMapping;
}
/**
 * This method was created in VisualAge.
 * @param args java.lang.String[]
 */
public static void main(String args[]) {
	try {
		
		SimulationContext simContext = SimulationContextTest.getExampleElectrical(0);
				
		System.out.println("");
		System.out.println("============================ SimContext ==================================");
		System.out.println("");
		System.out.println(simContext.getVCML());


		System.out.println("");
		System.out.println("========================= Math Description ================================");
		System.out.println("");
		MathMapping mathMapping = new MathMapping(simContext);
		MathDescription mathDesc = mathMapping.getMathDescription();
		System.out.print(mathDesc.getVCML_database());
		System.out.println("===========================================================================");

		//
		// check for warnings
		//
		if (mathDesc.getWarning()!=null){
			System.out.println("\n\nMathDescription has warnings:");
			System.out.println(mathDesc.getWarning()+"\n\n");
		}
		//
		// run profiling
		//
		//getExample(2);
		//System.out.flush();
		
	}catch (Exception e){
		e.printStackTrace(System.out);
		return;
	}		
}
}
