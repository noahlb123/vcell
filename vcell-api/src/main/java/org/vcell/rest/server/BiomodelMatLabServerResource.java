package org.vcell.rest.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.wadl.MethodInfo;
import org.restlet.ext.wadl.ParameterInfo;
import org.restlet.ext.wadl.ParameterStyle;
import org.restlet.ext.wadl.RepresentationInfo;
import org.restlet.ext.wadl.RequestInfo;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.vcell.rest.VCellApiApplication;
import org.vcell.rest.VCellApiApplication.AuthenticationPolicy;
import org.vcell.rest.common.BiomodelMatLabResource;
import org.vcell.util.PermissionException;
import org.vcell.util.VCAssert;
import org.vcell.util.document.User;

import cbit.vcell.biomodel.BioModel;
import cbit.vcell.mapping.MathMapping;
import cbit.vcell.mapping.SimulationContext;
import cbit.vcell.matlab.MatlabOdeFileCoder;
import cbit.vcell.math.MathDescription;
import cbit.vcell.solver.Simulation;
import cbit.vcell.xml.XMLSource;
import cbit.vcell.xml.XmlHelper;

public class BiomodelMatLabServerResource extends AbstractServerResource implements BiomodelMatLabResource {

	private String biomodelid;
	
    @Override
    protected RepresentationInfo describe(MethodInfo methodInfo,
            Class<?> representationClass, Variant variant) {
        RepresentationInfo result = new RepresentationInfo(variant);
        result.setReference("biomodel");
        return result;
    }

    /**
     * Retrieve the account identifier based on the URI path variable
     * "accountId" declared in the URI template attached to the application
     * router.
     */
    @Override
    protected void doInit() throws ResourceException {
        String simTaskIdAttribute = getAttribute(VCellApiApplication.BIOMODELID);

        if (simTaskIdAttribute != null) {
            this.biomodelid = simTaskIdAttribute;
            setName("Resource for biomodel \"" + this.biomodelid + "\"");
            setDescription("The resource describing the simulation task id \"" + this.biomodelid + "\"");
        } else {
            setName("simulation task resource");
            setDescription("The resource describing a simulation task");
        }
    }
	

	@Override
	protected void describeGet(MethodInfo info) {
		super.describeGet(info);
		RequestInfo requestInfo = new RequestInfo();
        List<ParameterInfo> parameterInfos = new ArrayList<ParameterInfo>();
        parameterInfos.add(new ParameterInfo("biomodelid",false,"string",ParameterStyle.TEMPLATE,"VCell biomodel id"));
 		requestInfo.setParameters(parameterInfos);
		info.setRequest(requestInfo);
	}

	@Override
	@Get(BiomodelMatLabResource.APPLICATION_MATLAB_XML)
	public StringRepresentation get_xml() {
		VCellApiApplication application = ((VCellApiApplication)getApplication());
		User vcellUser = application.getVCellUser(getChallengeResponse(),AuthenticationPolicy.ignoreInvalidCredentials);
        String vcml = getBiomodelMatLab(vcellUser);
        
        if (vcml != null){
        	String bioModelID = (String)getRequestAttributes().get(VCellApiApplication.BIOMODELID);
        	setAttribute("Content-Disposition", "attachment; filename=\"VCBioModel_"+bioModelID+".vcml\"");
        	return new StringRepresentation(vcml, BiomodelMatLabResource.VCDOC_MEDIATYPE);
        }
        throw new RuntimeException("biomodel not found");
	}

	
	private String getBiomodelMatLab(User vcellUser) {
		RestDatabaseService restDatabaseService = ((VCellApiApplication)getApplication()).getRestDatabaseService();
		try {
			//Make temporary resource compatible with restDatabaseService so we can re-use
			BiomodelVCMLServerResource bmsr = new BiomodelVCMLServerResource() {
				@Override
				public Map<String, Object> getRequestAttributes() {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put(VCellApiApplication.BIOMODELID, BiomodelMatLabServerResource.this.biomodelid);
					return hashMap;
				}
				@Override
				public Request getRequest() {
					// TODO Auto-generated method stub
					return BiomodelMatLabServerResource.this.getRequest();
				}
			};
			String biomodelVCML = restDatabaseService.query(bmsr,vcellUser);
			BioModel bioModel = XmlHelper.XMLToBioModel(new XMLSource(biomodelVCML));
			SimulationContext simulationContext = bioModel.getSimulationContext(0);
			MathMapping mathMapping = simulationContext.createNewMathMapping();
			MathDescription mathDesc = mathMapping.getMathDescription();
			VCAssert.assertValid(mathDesc);
			VCAssert.assertFalse(mathDesc.isSpatial(),"spatial");
			VCAssert.assertFalse(mathDesc.isNonSpatialStoch(),"stochastic");
			Simulation sim = new Simulation(mathDesc);
			
			/*MatlabOdeFileCoder coder = new MatlabOdeFileCoder(sim);
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw = new java.io.PrintWriter(sw);
			String functionName = exportFile.getName();
			if (functionName.endsWith(".m")){
				functionName = functionName.substring(0,functionName.length()-2);
			}
			coder.write_V6_MFile(pw,functionName,simulationContext.getOutputFunctionContext());
			pw.flush();
			pw.close();
			String resultString = sw.getBuffer().toString();*/
			return "server error";
		} catch (PermissionException e) {
			e.printStackTrace();
			throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED, "permission denied to requested resource");
		} catch (Exception e){
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e.getMessage());
		}
	}


}
