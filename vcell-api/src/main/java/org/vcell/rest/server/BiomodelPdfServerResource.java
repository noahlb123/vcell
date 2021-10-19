package org.vcell.rest.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.restlet.Request;
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
import org.vcell.model.rbm.RbmNetworkGenerator;
import org.vcell.rest.VCellApiApplication;
import org.vcell.rest.VCellApiApplication.AuthenticationPolicy;
import org.vcell.solver.nfsim.NFsimXMLWriter;
import org.vcell.util.PermissionException;
import org.vcell.util.document.User;

import com.lowagie.text.Chapter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Section;

import org.vcell.rest.common.BiomodelPdfResource;

import cbit.vcell.biomodel.BioModel;
import cbit.vcell.mapping.SimulationContext;
import cbit.vcell.messaging.server.SimulationTask;
import cbit.vcell.publish.PDFWriter;
import cbit.vcell.solver.NFsimSimulationOptions;
import cbit.vcell.solver.Simulation;
import cbit.vcell.solver.SimulationJob;
import cbit.vcell.xml.XMLSource;
import cbit.vcell.xml.XmlHelper;

public class BiomodelPdfServerResource extends AbstractServerResource implements BiomodelPdfResource {

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
	@Get(BiomodelPdfResource.APPLICATION_Pdf_XML)
	public StringRepresentation get_xml() {
		VCellApiApplication application = ((VCellApiApplication)getApplication());
		User vcellUser = application.getVCellUser(getChallengeResponse(),AuthenticationPolicy.ignoreInvalidCredentials);
        String vcml = getBiomodelPdf(vcellUser);
        
        if (vcml != null){
        	String bioModelID = (String)getRequestAttributes().get(VCellApiApplication.BIOMODELID);
        	setAttribute("Content-Disposition", "attachment; filename=\"VCBioModel_"+bioModelID+".vcml\"");
        	return new StringRepresentation(vcml, BiomodelPdfResource.VCDOC_MEDIATYPE);
        }
        throw new RuntimeException("biomodel not found");
	}

	
	private String getBiomodelPdf(User vcellUser) {
		RestDatabaseService restDatabaseService = ((VCellApiApplication)getApplication()).getRestDatabaseService();
		try {
			//Make temporary resource compatible with restDatabaseService so we can re-use
			BiomodelVCMLServerResource bmsr = new BiomodelVCMLServerResource() {
				@Override
				public Map<String, Object> getRequestAttributes() {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put(VCellApiApplication.BIOMODELID, BiomodelPdfServerResource.this.biomodelid);
					return hashMap;
				}
				@Override
				public Request getRequest() {
					// TODO Auto-generated method stub
					return BiomodelPdfServerResource.this.getRequest();
				}
			};
			
			String resultString = "Server Error";
			int sedmlLevel = 1;
			int sedmlVersion = 2;
			String biomodelVCML = restDatabaseService.query(bmsr,vcellUser);
			BioModel bioModel = XmlHelper.XMLToBioModel(new XMLSource(biomodelVCML));
			SimulationContext chosenSimContext = bioModel.getSimulationContext(0);
			Simulation selectedSim = chosenSimContext.getSimulations(0);
			SimulationTask simTask = new SimulationTask(new SimulationJob(selectedSim, 0, null),0);
			long randomSeed = 0;	// a fixed seed will allow us to run reproducible simulations
			//long randomSeed = System.currentTimeMillis();
			NFsimSimulationOptions nfsimSimulationOptions = new NFsimSimulationOptions();
			// we get the data we need from the math description
			boolean bUseLocationMarks = true;
			Element root = NFsimXMLWriter.writeNFsimXML(simTask, randomSeed, nfsimSimulationOptions, bUseLocationMarks);
			Document doc = new Document();
			doc.setRootElement(root);
			XMLOutputter xmlOut = new XMLOutputter();
			resultString = xmlOut.outputString(doc);
			
			PDFWriter PdfWriter = new PDFWriter();
			
			
			return resultString;
			
		} catch (PermissionException e) {
			e.printStackTrace();
			throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED, "permission denied to requested resource");
		} catch (Exception e){
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e.getMessage());
		}
	}


}
