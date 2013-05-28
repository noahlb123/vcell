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
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class VCML {
	//Added by frm 7/4/01
	public final static String VolumeRegionsMapSubvolume		= "VolumeRegionsMapSubvolume";
	public final static String MembraneRegionsMapVolumeRegion	= "MembraneRegionsMapVolumeRegion";
	public final static String VolumeElementsMapVolumeRegion	= "VolumeElementsMapVolumeRegion";
	//
	public final static String GeometryReference		= "GeometryRef";
	public final static String Geometry 				= "CartesianDomain";
	public final static String Image					= "Image";
	public final static String ImageSubVolume			= "ImageCompartment";
	public final static String AnalyticSubVolume		= "Compartment";
	public final static String SubVolume				= "Compartment";

	public final static String Version					= "Version";
	public final static String MathDescription			= "MathDescription";
	public final static String Simulation				= "Simulation";
	public final static String Name 					= "Name";
	public final static String VolumeVariable 			= "VolumeVariable";
	public final static String StochVolVariable 		= "StochasticVolumeVariable"; //stoch
	public final static String VolumeParticleVariable 		= "VolumeParticleVariable"; //stoch
	public final static String MembraneParticleVariable 	= "MembraneParticleVariable"; //stoch
	public final static String MembraneVariable 		= "MembraneVariable";
	public final static String FilamentVariable 		= "FilamentVariable";
	public final static String VolumeRegionVariable 	= "VolumeRegionVariable";
	public final static String MembraneRegionVariable	= "MembraneRegionVariable";
	public final static String FilamentRegionVariable	= "FilamentRegionVariable";
	public final static String VarIniCount_Old 			= "VariableInitialCondition"; //stoch
	public final static String VarIniCount 			    = "VariableInitialCount"; //stoch
	public final static String VarIniPoissonExpectedCount 	= "VariableInitialPoissonExpectedCount"; //stoch
	public final static String MembraneElements 		= "MembraneElements";
	public final static String ContourElements 			= "ContourElements";
	public final static String Task				 		= "Task";
	public final static String Constant		 			= "Constant";
	public final static String ConstantArraySpec		= "ConstantArraySpec";
	public final static String CompartmentSubDomain 	= "CompartmentSubDomain";
	public final static String MembraneSubDomain 		= "MembraneSubDomain";
	public final static String FilamentSubDomain 		= "FilamentSubDomain";
	public final static String Mesh						= "Mesh";
	public final static String MeshSpecification		= "MeshSpecification";
	public final static String CartesianMesh 			= "CartesianMesh";
	public final static String Dimension				= "Dimension";
	public final static String Output					= "Output";
	public final static String Unsteady					= "Unsteady";
	public final static String Steady					= "Steady";
	public final static String Size 					= "Size";
	public final static String Origin					= "Origin";
	public final static String Extent					= "Extent";
	public final static String Handle					= "Handle";
	public final static String Priority					= "Priority";
	public final static String PdeEquation				= "PdeEquation";
	public final static String OdeEquation				= "OdeEquation";
	public final static String JumpCondition			= "JumpCondition";
	public final static String VolumeRegionEquation		= "VolumeRegionEquation";
	public final static String MembraneRegionEquation	= "MembraneRegionEquation";
	public final static String FilamentRegionEquation	= "FilamentRegionEquation";
	public final static String UniformRate				= "UniformRate";
	public final static String VolumeRate				= "VolumeRate";
	public final static String MembraneRate				= "MembraneRate";
	public final static String FilamentRate				= "FilamentRate";
	public final static String Initial	 				= "Initial";
	public final static String Diffusion 				= "Diffusion";
	public final static String Rate		 				= "Rate";
	public final static String Exact		 			= "Exact";
	public final static String Constructed				= "Constructed";
	public final static String BoundaryXm				= "BoundaryXm";
	public final static String BoundaryXp				= "BoundaryXp";
	public final static String BoundaryYm				= "BoundaryYm";
	public final static String BoundaryYp				= "BoundaryYp";
	public final static String BoundaryZm				= "BoundaryZm";
	public final static String BoundaryZp				= "BoundaryZp";
	// For boundary Value/Spec
	public final static String BoundaryConditionSpec	= "Boundary";			// BoundaryConditionSpec
	public final static String BoundaryConditionValue	= "BoundaryValue";		// BoundaryConditionValue		
	
	public final static String VelocityX				= "VelocityX";
	public final static String VelocityY				= "VelocityY";
	public final static String VelocityZ				= "VelocityZ";
	public final static String GradientX				= "GradientX";
	public final static String GradientY				= "GradientY";
	public final static String GradientZ				= "GradientZ";
	public final static String Dirichlet				= "Dirichlet";
	public final static String Neumann					= "Neumann";
	public final static String InFlux					= "InFlux";
	public final static String OutFlux					= "OutFlux";
	public final static String Value					= "Value";
	public final static String Time						= "Time";
	public final static String BeginBlock 				= "{";
	public final static String EndBlock 				= "}";
	public final static String EndExpression 			= ";";
	public final static String Function 				= "Function";
	public final static String VolFunction				= "VolFunction";
	public final static String MemFunction				= "MemFunction";
	public final static String MathOverrides			= "MathOverrides";
	public final static String SolverTaskDescription	= "SolverTaskDescription";
	public final static String TaskType					= "TaskType";
	public final static String TaskType_Unsteady		= "Unsteady";
	public final static String TaskType_Steady			= "Steady";
	public final static String MaxTime					= "MaxTime";	
	public final static String StartingTime				= "StartingTime";	
	public final static String EndingTime				= "EndingTime";	
	public final static String TimeStep					= "TimeStep";	
	public final static String ErrorTolerance			= "ErrorTolerance";
	public final static String UseSymbolicJacobian		= "UseSymbolicJacobian";
	public final static String AbsoluteErrorTolerance	= "AbsoluteErrorTol";	
	public final static String RelativeErrorTolerance	= "RelativeErrorTol";
	public final static String DefaultTimeStep			= "DefaultTimeStep";
	public final static String MinimumTimeStep			= "MinimumTimeStep";
	public final static String MaximumTimeStep			= "MaximumTimeStep";
	//following 4 VCML tags are added on Dec 5th, 2006 to store simulation info. for stochastic simulation
	public final static String StochSimOptions			= "StochSimOptions"; //stoch
	public final static String UseCustomSeed			= "UseCustomSeed"; //stoch
	public final static String CustomSeed				= "CustomSeed"; //stoch
	public final static String NumOfTrials				= "NumOfTrials"; //stoch
	//follwing 4 VCML tags are added on July 18th, 2007 to store simulation info. for stochastic hybrid simulation
	public final static String Epsilon 					= "Epsilon"; //stoch
	public final static String Lambda					= "Lambda"; //stoch
	public final static String MSRTolerance				= "MSRTolerance"; //stoch
	public final static String SDETolerance				= "SDETolerance"; //stoch
	
	public final static String Action 					= "Effect"; //stoch
	public final static String JumpProcess 				= "JumpProcess"; //stoch
	public final static String ProbabilityRate 			= "ProbabilityRate"; //stoch

	public final static String OutputOptions			= "OutputOptions";
	public final static String KeepEvery				= "KeepEvery";
	public final static String KeepAtMost				= "KeepAtMost";
	public final static String OutputTimes              = "OutputTimes";
	public final static String OutputTimeStep			= "OutputTimeStep";
	public final static String StopAtSpatiallyUniform 	= "StopAtSpatiallyUniform";
	public final static String RunParameterScanSerially = "RunParameterScanSerially";
	
	public final static String SensitivityParameter		= "SensitivityParameter";
	public final static String TimeBounds				= "TimeBounds";
	public final static String SolverDescription		= "SolverDescription";
	public final static String ReactionSpec				= "ReactionStep";
	public final static String ReactionMapping			= "ReactionMapping";
	public final static String ReactionMappingIncluded	= "Included";
	public final static String ReactionMappingExcluded	= "Excluded";
	public final static String ReactionMappingFast		= "Fast";
	public final static String ReactionMappingMolecularOnly	= "MolecularOnly";
	public final static String ReactionMappingCurrentOnly	= "CurrentOnly";
	
	public final static String FastInvariant			= "FastInvariant";
	public final static String FastRate					= "FastRate";
	public final static String FastSystem				= "FastSystem";
	public final static String FastIndependent			= "FastIndependent";
	public final static String FastDependent			= "FastDependent";
	
	public final static String Event = "Event";
	public final static String UseValuesFromTriggerTime = "UseValuesFromTriggerTime";
	public final static String Trigger = "Trigger";
	public final static String Delay = "Delay";
	public final static String Duration = "Duration";
	public final static String EventAssignment = "EventAssignment";
	
	public final static String VolumeRandomVariable = "VolumeRandomVariable";
	public final static String MembraneRandomVariable = "MembraneRandomVariable";
	public final static String RandomVariable_Seed = "IntegerSeed";
	public final static String GaussianDistribution = "GaussianDistribution";
	public final static String GaussianDistribution_Mean = "Mean";
	public final static String GaussianDistribution_StandardDeviation = "StandardDeviation";
	public final static String UniformDistribution = "UniformDistribution";	
	public final static String UniformDistribution_Minimum = "Minimum";
	public final static String UniformDistribution_Maximum = "Maximum";
	
	public final static String ParticleProperties 		= "ParticleProperties"; // particle
	public final static String ParticleInitialCount_old		= "ParticleInitial"; // particle
	public final static String ParticleInitialCount			= "ParticleInitialCount"; // particle
	public final static String ParticleCount			= "ParticleCount"; // particle
	public final static String ParticleLocationX		= "ParticleLocationX"; // particle
	public final static String ParticleLocationY		= "ParticleLocationY"; // particle
	public final static String ParticleLocationZ		= "ParticleLocationZ"; // particle
	public final static String ParticleDiffusion		= "ParticleDiffusion"; // particle
	public final static String ParticleDriftX			= "ParticleDriftX"; // particle
	public final static String ParticleDriftY			= "ParticleDriftY"; // particle
	public final static String ParticleDriftZ			= "ParticleDriftZ"; // particle
	public final static String ParticleJumpProcess 		= "ParticleJumpProcess"; // particle
	public final static String MacroscopicRateConstant	= "MacroscopicRateConstant"; // particle
	public final static String InteractionRadius    	= "InteractionRadius"; // particle
	public final static String CreateParticle			= "CreateParticle"; // particle
	public final static String DestroyParticle			= "DestroyParticle"; // particle
	public final static String SelectedParticle			= "SelectedParticle"; // particle
	
	public final static String ParticleInitialConcentration	= "ParticleInitialConcentration"; // particle
	public final static String ParticleDistribution		= "ParticleDistribution"; // particle
	
	public final static String SmoldynSimulationOptions	= "SmoldynSimulationOptions";
	public final static String SmoldynSimulationOptions_randomSeed	= "RandomSeed";
	public final static String SmoldynSimulationOptions_accuracy	= "Accuracy";
	public final static String SmoldynSimulationOptions_gaussianTableSize	= "gaussianTableSize";
	public final static String SmoldynSimulationOptions_boxSize	= "BoxSize";
	public final static String SmoldynSimulationOptions_useHighResolutionSample	= "UseHighResolutionSample";
	public final static String SmoldynSimulationOptions_saveParticleLocations	= "saveParticleLocations";
	
	public final static String SundialsSolverOptions	= "SundialsSolverOptions";
	public final static String SundialsSolverOptions_maxOrderAdvection	= "MaxOrderAdvection";
	
	public final static String PostProcessingBlock = "PostProcessing";
	public final static String ExplicitDataGenerator = "Explicit";
	public final static String ProjectionDataGenerator = "Projection";
	public final static String ProjectionAxis = "Axis";
	public final static String ProjectionOperation = "Operation";
	public final static String ConvolutionDataGenerator = "Convolution";
	public final static String Kernel = "Kernel";
	public final static String KernelGaussian = "Gaussian";
	public final static String KernelGaussianSigmaXY = "SigmaXY";
	public final static String KernelGaussianSigmaZ = "SigmaZ";
	
	public final static String ChomboSolverSpec = "ChomboSolverSpec";
	public final static String MaxBoxSize = "MaxBoxSize";
	public final static String FillRatio = "FillRatio";
	public final static String MeshRefinement = "MeshRefinement";
	public final static String RefinementLevel = "RefinementLevel";
	public final static String ChomboBox = "ChomboBox";
	public final static String NumOfLevels = "NumOfLevels";
	public final static String NumOfBoxes = "NumOfBoxes";
	public final static String RefineRatio = "RefineRatio";		
}
