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
import org.vcell.rest.common.BiomodelSEDMLResource;
import org.vcell.sbml.vcell.SBMLExporter;
import org.vcell.sbml.vcell.StructureSizeSolver;
import org.jlibsedml.SEDMLDocument;
import org.vcell.sedml.SEDMLExporter;
import org.vcell.sbml.vcell.SBMLExporter;
import org.vcell.util.Pair;
import org.vcell.util.PermissionException;
import org.vcell.util.document.User;

import cbit.vcell.biomodel.BioModel;
import cbit.vcell.mapping.SimulationContext;
import cbit.vcell.mapping.StructureMapping;
import cbit.vcell.model.Structure;
import cbit.vcell.solver.SimulationJob;
import cbit.vcell.xml.XMLSource;
import cbit.vcell.xml.XmlHelper;

public class BiomodelSEDMLServerResource extends AbstractServerResource implements BiomodelSEDMLResource {

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
	@Get(BiomodelSEDMLResource.APPLICATION_SEDML_XML)
	public StringRepresentation get_xml() {
		VCellApiApplication application = ((VCellApiApplication)getApplication());
		User vcellUser = application.getVCellUser(getChallengeResponse(),AuthenticationPolicy.ignoreInvalidCredentials);
        String vcml = getBiomodelSEDML(vcellUser);
        
        if (vcml != null){
        	String bioModelID = (String)getRequestAttributes().get(VCellApiApplication.BIOMODELID);
        	setAttribute("Content-Disposition", "attachment; filename=\"VCBioModel_"+bioModelID+".vcml\"");
        	return new StringRepresentation(vcml, BiomodelSEDMLResource.VCDOC_MEDIATYPE);
        }
        throw new RuntimeException("biomodel not found");
	}

	
	private String getBiomodelSEDML(User vcellUser) {
		RestDatabaseService restDatabaseService = ((VCellApiApplication)getApplication()).getRestDatabaseService();
		try {
			//Make temporary resource compatible with restDatabaseService so we can re-use
			BiomodelVCMLServerResource bmsr = new BiomodelVCMLServerResource() {
				@Override
				public Map<String, Object> getRequestAttributes() {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put(VCellApiApplication.BIOMODELID, BiomodelSEDMLServerResource.this.biomodelid);
					return hashMap;
				}
				@Override
				public Request getRequest() {
					// TODO Auto-generated method stub
					return BiomodelSEDMLServerResource.this.getRequest();
				}
			};
			String biomodelVCML = restDatabaseService.query(bmsr,vcellUser);
			BioModel bioModel = XmlHelper.XMLToBioModel(new XMLSource(biomodelVCML));
			SimulationContext[] simContexts = bioModel.getSimulationContexts();
			
			//SBMLExporter sbmlExporter = new SBMLExporter(bioModel, 1, 2, isSpatial);
			
			for (SimulationContext simContext : simContexts) {
				String simContextName = simContext.getName();
				// export all applications that are not spatial stochastic
//				if (!(simContext.getGeometry().getDimension() > 0 && simContext.isStoch())) {
				if (true) {
					// check if structure sizes are set. If not, get a structure from the model, and set its size 
					// (thro' the structureMappings in the geometry of the simContext); invoke the structureSizeEvaluator 
					// to compute and set the sizes of the remaining structures.
					if (!simContext.getGeometryContext().isAllSizeSpecifiedPositive()) {
						Structure structure = simContext.getModel().getStructure(0);
						double structureSize = 1.0;
						StructureMapping structMapping = simContext.getGeometryContext().getStructureMapping(structure); 
						StructureSizeSolver.updateAbsoluteStructureSizes(simContext, structure, structureSize, structMapping.getSizeParameter().getUnitDefinition());
					}
					
					// Export the application itself to SBML, with default overrides
					String sbmlString = null;
					int level = 3;
					int version = 1;
					boolean isSpatial = simContext.getGeometry().getDimension() > 0 ? true : false;
					SimulationJob simJob = null;
					Map<Pair <String, String>, String> l2gMap = null;		// local to global translation map
					
					boolean sbmlExportFailed = false;
					if (bioModel instanceof BioModel) {
						SBMLExporter sbmlExporter = new SBMLExporter(bioModel, level, version, isSpatial);
						sbmlExporter.setSelectedSimContext(simContext);
						sbmlExporter.setSelectedSimulationJob(null);	// no sim job
						sbmlString = sbmlExporter.getSBMLString();
						return sbmlString;
					}
				}
			}
			return "server failure";
			
		} catch (PermissionException e) {
			e.printStackTrace();
			throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED, "permission denied to requested resource");
		} catch (Exception e){
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e.getMessage());
		}
	}


}
