package cbit.vcell.microscopy;

import org.vcell.util.DataAccessException;

import cbit.function.DefaultScalarFunction;
import cbit.vcell.VirtualMicroscopy.ROI;
import cbit.vcell.client.server.VCDataManager;
import cbit.vcell.client.task.ClientTaskStatusSupport;
import cbit.vcell.opt.ImplicitObjectiveFunction;
import cbit.vcell.opt.OptimizationResultSet;
import cbit.vcell.opt.OptimizationSolverSpec;
import cbit.vcell.opt.OptimizationSpec;
import cbit.vcell.opt.Parameter;
import cbit.vcell.opt.SimpleReferenceData;
import cbit.vcell.opt.solvers.OptSolverCallbacks;
import cbit.vcell.opt.solvers.PowellOptimizationSolver;
import cbit.vcell.simdata.DataSetControllerImpl;
import cbit.vcell.solver.VCSimulationDataIdentifier;
import cbit.vcell.solver.ode.ODESolverResultSet;
import cbit.vcell.solver.ode.ODESolverResultSetColumnDescription;

public class FRAPOptimization {
	
	static double FTOL = 1.0e-6;
	public static double epsilon = 1e-8;
	static double penalty = 1E4;
	public static double largeNumber = 1E8; 
		
	//This function generates average intensity under different ROIs according to each time points for EXPERIMENTAL data.
	//the results returns double[roi length][time points with prebleach removed]. 
	public static double[][] dataReduction(FRAPData argFrapData,int argStartRecoveryIndex, ROI[] expRois, double[] normFactor) 
	{ 
		int roiLen = expRois.length;
		int numRefTimePoints = argFrapData.getImageDataset().getSizeT();
		// data set which is normalized with prebleach time points
		double[][] baseData = new double[roiLen][numRefTimePoints];
        // data set which is normalized and removed time points in prebleach
		double[][] newData = null;
		double[] avgBkIntensity = argFrapData.getAvgBackGroundIntensity();
		
		newData = new double[roiLen][numRefTimePoints-argStartRecoveryIndex];
		
		for(int i = 0; i < roiLen; i++)
		{
			baseData[i] = FRAPDataAnalysis.getAverageROIIntensity(argFrapData, expRois[i], normFactor, avgBkIntensity);
			//remove prebleach
			for(int j = 0; j < numRefTimePoints - argStartRecoveryIndex; j++)
			{
				newData[i][j]  = baseData[i][j+argStartRecoveryIndex];
			}
		}
		return newData;
	}

	//This function generates average intensity under different ROIs according to each time points for REFERENCE data.
	//the results returns double[roi length][time points].
	public static double[][] dataReduction(
			VCDataManager vcDataManager,VCSimulationDataIdentifier vcSimdataID, double[] rawSimTimePoints,
			ROI[] expRois, ClientTaskStatusSupport progressListener, boolean isRefSim) throws Exception{ 

		if(progressListener != null){
			progressListener.setMessage("Reading data, generating ROI averages");
		}
		int roiLen = expRois.length;
		double[] simTimes = rawSimTimePoints;
		double[][] newData = new double[roiLen][simTimes.length];
		double[] simData = null;
		if(isRefSim)
		{
			for (int j = 0; j < simTimes.length; j++) {
				simData = vcDataManager.getSimDataBlock(vcSimdataID, FRAPStudy.SPECIES_NAME_PREFIX_MOBILE,simTimes[j]).getData();
				for(int i = 0; i < roiLen; i++){
					newData[i][j] = AnnotatedImageDataset.getAverageUnderROI(simData, expRois[i].getPixelsXYZ(), null,0.0);
				}
				if(progressListener != null){
					progressListener.setProgress((int)(((double)(j+1))/((double)simTimes.length) *100));
				}
			}
		}
		else
		{
			for (int j = 0; j < simTimes.length; j++) {
				simData = vcDataManager.getSimDataBlock(vcSimdataID, FRAPStudy.SPECIES_NAME_PREFIX_COMBINED,simTimes[j]).getData();
				for(int i = 0; i < roiLen; i++){
					newData[i][j] = AnnotatedImageDataset.getAverageUnderROI(simData, expRois[i].getPixelsXYZ(), null,0.0);
				}
				if(progressListener != null){
					progressListener.setProgress((int)(((double)(j+1))/((double)simTimes.length) *100));
				}
			}
		}
		return newData;
	}
	
	 //remove prebleach time points
	public static double[] timeReduction(double[] argTimeStamps, int argStartRecoveryIndex )
	{
		double[] newTimeStamps = new double[argTimeStamps.length - argStartRecoveryIndex];
		for(int i = 0; i < (argTimeStamps.length - argStartRecoveryIndex); i++)
		{
			newTimeStamps[i] = argTimeStamps[i+argStartRecoveryIndex]-argTimeStamps[argStartRecoveryIndex];
		}
		return newTimeStamps;
	}
	
	public static double getValueFromParameters_oneDiffRate(double diffData, double mobileFrac, double bleachWhileMonitoringRate, double  firstPostBleach, double timePoint)
	{
		double imMobileFrac = 1 - mobileFrac;
		double result = (mobileFrac * diffData + imMobileFrac * firstPostBleach) * Math.exp(-(bleachWhileMonitoringRate * timePoint));
		
		return result;
	}
	
	public static double getValueFromParameters_twoDiffRates(double mFracFast, double fastData, double mFracSlow, double slowData, double bleachWhileMonitoringRate, double  firstPostBleach, double timePoint)
	{
		double immobileFrac = 1 - mFracFast - mFracSlow;
		double result = (mFracFast * fastData + mFracSlow * slowData + immobileFrac * firstPostBleach) * Math.exp(-(bleachWhileMonitoringRate * timePoint));
		
		return result;
	}
	
	public static double getErrorByNewParameters_oneDiffRate(double refDiffRate, double[] newParams, double[][] refData, double[][] expData, double[] refTimePoints, double[] expTimePoints, int roiLen, /*double refTimeInterval,*/ boolean[] errorOfInterest) throws Exception
	{
		// trying 3 parameters
		double error = 0;
		double diffRate = 0;
		double[][] diffData = null;
		double mobileFrac = 1;
		double bleachWhileMonitoringRate = 0;
		if(newParams != null && newParams.length > 0)
		{
			diffRate = newParams[FRAPModel.INDEX_PRIMARY_DIFF_RATE];
			mobileFrac = Math.min(newParams[FRAPModel.INDEX_PRIMARY_FRACTION], 1);
			bleachWhileMonitoringRate = newParams[FRAPModel.INDEX_BLEACH_MONITOR_RATE];
						
			diffData = FRAPOptimization.getValueByDiffRate(refDiffRate,
                    diffRate,
                    refData,
                    expData,
                    refTimePoints,
                    expTimePoints,
                    roiLen);
			//get diffusion initial condition for immobile part
			double[] firstPostBleach = new double[roiLen];
			if(diffData != null)
			{
				for(int i = 0; i < roiLen; i++)
				{
					firstPostBleach[i] = diffData[i][0];
				}
			}
			//compute error against exp data
			if(errorOfInterest != null)
			{
				for(int i=0; i<roiLen; i++)
				{
					if(errorOfInterest[i])
					{
						for(int j=0; j<expTimePoints.length; j++)
						{
							double difference = expData[i][j] - FRAPOptimization.getValueFromParameters_oneDiffRate(diffData[i][j], mobileFrac, bleachWhileMonitoringRate, firstPostBleach[i], expTimePoints[j]);
//							double difference = expData[i][j]- (mobileFrac * diffData[i][j] + imMobileFrac * firstPostBleach[i]) * Math.exp(-(bleachWhileMonitoringRate*expTimePoints[j]));
							error = error + difference * difference;
						}
					}
				}
			}
			return error;
		}
		else
		{
			throw new Exception("Cannot perform optimization because there is no parameters to be evaluated.");
		}
	}
	
	public static double getErrorByNewParameters_twoDiffRates(double refDiffRate, double[] newParams, double[][] refData, double[][] expData, double[] refTimePoints, double[] expTimePoints, int roiLen, /*double refTimeInterval,*/ boolean[] errorOfInterest) throws Exception
	{
		double error = 0;
		// trying 5 parameters
		double diffFastRate = newParams[FRAPModel.INDEX_PRIMARY_DIFF_RATE];
		double mFracFast = newParams[FRAPModel.INDEX_PRIMARY_FRACTION];
		double monitoringRate = newParams[FRAPModel.INDEX_BLEACH_MONITOR_RATE];
		double diffSlowRate = newParams[FRAPModel.INDEX_SECONDARY_DIFF_RATE];
		double mFracSlow = newParams[FRAPModel.INDEX_SECONDARY_FRACTION];
		
		
		double[][] fastData = null;
		double[][] slowData = null;
				
		if(newParams != null && newParams.length > 0)
		{
			fastData = FRAPOptimization.getValueByDiffRate(refDiffRate,
                    diffFastRate,
                    refData,
                    expData,
                    refTimePoints,
                    expTimePoints,
                    roiLen);
			
			slowData = FRAPOptimization.getValueByDiffRate(refDiffRate,
                    diffSlowRate,
                    refData,
                    expData,
                    refTimePoints,
                    expTimePoints,
                    roiLen);
			
			//get diffusion initial condition for immobile part
			double[] firstPostBleach = new double[roiLen];
			if(fastData != null)
			{
				for(int i = 0; i < roiLen; i++)
				{
					firstPostBleach[i] = fastData[i][0];
				}
			}
			//compute error against exp data
			for(int i=0; i<roiLen; i++)
			{
				if(errorOfInterest != null && errorOfInterest[i])
				{
					for(int j=0; j<expTimePoints.length; j++)
					{
						double newValue = getValueFromParameters_twoDiffRates(mFracFast, fastData[i][j], mFracSlow, slowData[i][j], monitoringRate, firstPostBleach[i], expTimePoints[j]);
//						double newValue = (mFracFast * fastData[i][j] + mFracSlow * slowData[i][j] + immobileFrac * firstPostBleach[i]) * Math.exp(-(monitoringRate * expTimePoints[j]));
						double difference = expData[i][j] - newValue;
						error = error + difference * difference;
					}
				}
			}
			//add penalty for wrong parameter set
			if(mFracFast + mFracSlow > 1)
			{
				double mFracError = (mFracFast + mFracSlow - 1);
				error = error + (mFracError + mFracError * mFracError) * penalty;
			}
			if(diffSlowRate > diffFastRate)
			{
				double rateError = diffSlowRate - diffFastRate;
				error = error + (rateError + rateError * rateError) * penalty;
			}
			//System.out.println("error:" + error);
			return error;
		}
		else
		{
			throw new Exception("Cannot perform optimization because there is no parameters to be evaluated.");
		}
	}
	
	public static double[][] getValueByDiffRate(double refDiffRate, double newDiffRate, double[][] refData, double[][] expData, double[] refTimePoints, double[] expTimePoints, int roiLen/*, double refTimeInterval*/) throws Exception
	{
		double[][] result = new double[roiLen][expTimePoints.length];
		int preTimeIndex = 0;
		int postTimeIndex = 0;
		int idx = 0;
		for(int j = 0; j < expTimePoints.length; j++)
		{	
			// find corresponding time points in reference data 
			double estimateTime = (newDiffRate/refDiffRate) * expTimePoints[j];
			for( ;idx < refTimePoints.length; idx ++)
			{
				if(estimateTime < (refTimePoints[idx] + FRAPOptimization.epsilon))
				{
					break;
				}
			}
			postTimeIndex = idx;
			
			if(postTimeIndex <= 0)//negtive newDiffRate will cause array index out of bound exception
			{
				preTimeIndex = 0;
				postTimeIndex = preTimeIndex;
			}
			else if(postTimeIndex > 0 && postTimeIndex < refTimePoints.length )
			{
				if((estimateTime > (refTimePoints[postTimeIndex] - FRAPOptimization.epsilon)) &&  (estimateTime  < (refTimePoints[postTimeIndex] + FRAPOptimization.epsilon)))
				{
					preTimeIndex = postTimeIndex;
				}
				else 
				{
					preTimeIndex = postTimeIndex - 1;
				}
			}
			else if(postTimeIndex >= refTimePoints.length)  
			{
				preTimeIndex = refTimePoints.length -1;
				postTimeIndex = preTimeIndex;
			}
			double preTimeInRefData = refTimePoints[preTimeIndex];
			double postTimeInRefData = refTimePoints[postTimeIndex];
			double proportion = 0;
			
			if((postTimeInRefData-preTimeInRefData) != 0)
			{
				proportion = ((estimateTime-preTimeInRefData)/(postTimeInRefData-preTimeInRefData));
			}
			
			for(int i = 0; i < roiLen; i++) 
			{
				//get data from reference data according to the estimate time.
				if(preTimeIndex == postTimeIndex)
				{
					result[i][j] = refData[i][preTimeIndex];
				}
				else 
				{
					double preDataValue = refData[i][preTimeIndex];
					double postDataValue = refData[i][postTimeIndex];
					double estimateDataValue = preDataValue + proportion *(postDataValue-preDataValue);
					result[i][j] = estimateDataValue;
				}
			}
		}
		return result;
	}
	
	public static void estimate(FRAPOptData argOptData, Parameter[] inParams, String[] outParaNames, double[] outParaVals, boolean[] eoi) throws Exception
	{
		/*PowellSolver  solver = new PowellSolver();
		LookupTableObjectiveFunction func = new LookupTableObjectiveFunction(argOptData);
		
		//best point found. we have only one dimension here.
		double[] p = new double[1];
		p[0] = iniDiffGuess;
		//current direction set
		double[][] xi = new double[1][1];
		for (int i=0;i<1;i++)
		{
		   for (int j=0;j<1;j++)
		   {
		       xi[i][j]=(i == j ? 1.0 : 0.0);
		   }
		}
		//run powell with initial guess, initial direction set and objective function
		double minError = solver.powell(1, p, xi, FTOL, func);
		parameters[FRAPOptData.idxOptDiffRate] = p[0];
		parameters[FRAPOptData.idxMinError] = minError;*/
		
		// create optimization solver 
		PowellOptimizationSolver optSolver = new PowellOptimizationSolver();
		// create optimization spec
		OptimizationSpec optSpec = new OptimizationSpec();
		DefaultScalarFunction scalarFunc = new LookupTableObjectiveFunction(argOptData, eoi); // add opt function 
		optSpec.setObjectiveFunction(new ImplicitObjectiveFunction(scalarFunc));
		// create solver spec 
		OptimizationSolverSpec optSolverSpec = new OptimizationSolverSpec(OptimizationSolverSpec.SOLVERTYPE_POWELL, FRAPOptimization.FTOL);
		// create solver call back
		OptSolverCallbacks optSolverCallbacks = new OptSolverCallbacks();
		// create optimization result set
		OptimizationResultSet optResultSet = null;
		for (int i = 0; i < inParams.length; i++) { //add parameters
			optSpec.addParameter(inParams[i]);
		}
		optResultSet = optSolver.solve(optSpec, optSolverSpec, optSolverCallbacks);
		//if the parameters are 5, we have to go over again to see if we get the best answer.
		if(inParams.length == 5)//5 parameters
		{
			OptimizationSpec optSpec2 = new OptimizationSpec();
			optSpec2.setObjectiveFunction(new ImplicitObjectiveFunction(scalarFunc));
			Parameter[] inParamsFromResult = generateInParamSet(inParams, optResultSet.getParameterValues());
			for (int i = 0; i < inParamsFromResult.length; i++) { //add parameters
				optSpec2.addParameter(inParamsFromResult[i]);
			}
			OptimizationResultSet tempOptResultSet = optSolver.solve(optSpec2, optSolverSpec, optSolverCallbacks);
			if(optResultSet.getObjectiveFunctionValue() > tempOptResultSet.getObjectiveFunctionValue())
			{
				optResultSet = tempOptResultSet;
			}
		}
		//System.out.println("obj function value:"+optResultSet.getObjectiveFunctionValue());
		//System.out.println("");
		// copy results to output parameters
		String[] names = optResultSet.getParameterNames();
		double[] values = optResultSet.getParameterValues();
		for (int i = 0; i < names.length; i++) 
		{
			outParaNames[i] = names[i];
			outParaVals[i] = values[i];
		}
	}
	
	private static Parameter[] generateInParamSet(Parameter[] inputParams, double newValues[])
	{
		Parameter[] result = new Parameter[inputParams.length];
		Parameter fastRate = inputParams[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX];
		Parameter slowRate = inputParams[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX];
		Parameter fastMobileFrac = inputParams[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX];
		Parameter slowMobileFrac = inputParams[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX];
		Parameter bwmRate = inputParams[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX];
		if(newValues[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX] < fastRate.getLowerBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX] = fastRate.getLowerBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX] > fastRate.getUpperBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX] = fastRate.getUpperBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX] < slowRate.getLowerBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX] = slowRate.getLowerBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX] > slowRate.getUpperBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX] = slowRate.getUpperBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX] < fastMobileFrac.getLowerBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX] = fastMobileFrac.getLowerBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX] > fastMobileFrac.getUpperBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX] = fastMobileFrac.getUpperBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX] < slowMobileFrac.getLowerBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX] = slowMobileFrac.getLowerBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX] > slowMobileFrac.getUpperBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX] = slowMobileFrac.getUpperBound();
		}
		if(newValues[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX] < bwmRate.getLowerBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX] = bwmRate.getLowerBound();
		}	
		if(newValues[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX] > bwmRate.getUpperBound())
		{
			newValues[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX] = bwmRate.getUpperBound();
		}
		
		result[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX] = new Parameter(fastRate.getName(), 
				                                                                    fastRate.getLowerBound(), 
				                                                                    fastRate.getUpperBound(),
				                                                                    fastRate.getScale(),
				                                                                    newValues[FRAPOptData.TWODIFFRATES_FAST_DIFFUSION_RATE_INDEX]);
		result[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX] = new Parameter(slowRate.getName(), 
																					slowRate.getLowerBound(), 
																					slowRate.getUpperBound(),
																					slowRate.getScale(),
																					newValues[FRAPOptData.TWODIFFRATES_SLOW_DIFFUSION_RATE_INDEX]);
		result[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX] = new Parameter(fastMobileFrac.getName(), 
																					fastMobileFrac.getLowerBound(), 
																					fastMobileFrac.getUpperBound(),
																					fastMobileFrac.getScale(),
																					newValues[FRAPOptData.TWODIFFRATES_FAST_MOBILE_FRACTION_INDEX]);
		result[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX] = new Parameter(slowMobileFrac.getName(), 
																					slowMobileFrac.getLowerBound(), 
																					slowMobileFrac.getUpperBound(),
																					slowMobileFrac.getScale(),
																					newValues[FRAPOptData.TWODIFFRATES_SLOW_MOBILE_FRACTION_INDEX]);
       result[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX] = new Parameter(bwmRate.getName(),
    		   																	   bwmRate.getLowerBound(),
    		   																	   bwmRate.getUpperBound(),
    		   																	   bwmRate.getScale(),
    		   																	   newValues[FRAPOptData.TWODIFFRATES_BLEACH_WHILE_MONITOR_INDEX]);
 		return result;
	}
	//for exp data mainly, can be used for sim and opt data as well
	public static SimpleReferenceData doubleArrayToSimpleRefData(double[][] origData, double[] timePoints, int startingIndex, boolean[] selectedROIs) /*throws Exception*/
	{
		if(origData != null && timePoints != null && selectedROIs != null)
		{
			int numSelectedROITypes = 0;
			for(int i=0; i<selectedROIs.length; i++)
			{
				if(selectedROIs[i])
				{
					numSelectedROITypes ++;
				}
			}
			
			String[] columnNames = new String[numSelectedROITypes+1];
			double[] weights = new double[numSelectedROITypes+1];
			double[][] data = new double[numSelectedROITypes+1][];
			//set time
			columnNames[0] = "t";
			weights[0] = 1.0;
			double[] truncatedTimes = new double[timePoints.length-startingIndex];
			System.arraycopy(timePoints, startingIndex, truncatedTimes, 0, truncatedTimes.length);
			data[0] = truncatedTimes;
			//set data
			int colCounter =  0;//already take "t" colume into account, "t" column is at the column index 0.
			for (int j = 0; j < FRAPData.VFRAP_ROI_ENUM.values().length; j++) {
				if(!selectedROIs[j])//skip unselected ROIs
				{
					continue;
				}
				colCounter ++; 
				columnNames[colCounter] = FRAPData.VFRAP_ROI_ENUM.values()[j].name();
				weights[colCounter] = 1.0;
//				double[] allTimesData = origData[j];
//				double[] truncatedTimesData = new double[truncatedTimes.length];
//				System.arraycopy(allTimesData, startingIndex, truncatedTimesData, 0, truncatedTimes.length);
				data[colCounter] = origData[j]; //original data has been truncated from dimension reduced exp data function.
			}
			return new SimpleReferenceData(columnNames, weights, data);
			
		}
		return null;
	}
	
	//used by opt or sim data, no truncation of time/data. @param startingIndex is used to shift opt/sim times to compare with exp times
	public static ODESolverResultSet doubleArrayToSolverResultSet(double[][] origData, double[] timePoints, double timePointOffset, boolean[] selectedROIs) /*throws Exception*/
	{
		if(origData != null && timePoints != null && selectedROIs != null)
		{
			int numSelectedROITypes = 0;
			for(int i=0; i<selectedROIs.length; i++)
			{
				if(selectedROIs[i])
				{
					numSelectedROITypes ++;
				}
			}
			
			ODESolverResultSet newOdeSolverResultSet = new ODESolverResultSet();
			newOdeSolverResultSet.addDataColumn(new ODESolverResultSetColumnDescription("t"));
			for (int j = 0; j < selectedROIs.length; j++) {
				if(!selectedROIs[j]){continue;}
				String currentROIName = FRAPData.VFRAP_ROI_ENUM.values()[j].name();
				String name = currentROIName;
				newOdeSolverResultSet.addDataColumn(new ODESolverResultSetColumnDescription(name));
			}
			
			//set time
			for (int j = 0; j < timePoints.length; j++) {
				double[] row = new double[numSelectedROITypes+1];
				row[0] = timePoints[j] + timePointOffset;
				newOdeSolverResultSet.addRow(row);
			}
			//set data
			int columncounter = 0;
			for (int j = 0; j < selectedROIs.length; j++) {
				if(!selectedROIs[j]){continue;}
					double[] values = origData[j];
					for (int k = 0; k < values.length; k++) {
						newOdeSolverResultSet.setValue(k, columncounter+1, values[k]);
					}
				columncounter++;
			}
			
			return newOdeSolverResultSet;
		}
		return null;
	}
	
}
