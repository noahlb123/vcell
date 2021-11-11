package org.vcell.rest.server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.wadl.MethodInfo;
import org.restlet.ext.wadl.ParameterInfo;
import org.restlet.ext.wadl.ParameterStyle;
import org.restlet.ext.wadl.RepresentationInfo;
import org.restlet.ext.wadl.RequestInfo;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.vcell.rest.VCellApiApplication;
import org.vcell.rest.VCellApiApplication.AuthenticationPolicy;
import org.vcell.rest.common.BiomodelDiagramResource;
import org.vcell.util.ObjectNotFoundException;
import org.vcell.util.PermissionException;
import org.vcell.util.document.User;

import cbit.image.ThumbnailImage;
import cbit.vcell.biomodel.BioModel;
import cbit.vcell.mapping.SimulationContext;
import cbit.vcell.publish.ITextWriter;
import cbit.vcell.xml.XMLSource;
import cbit.vcell.xml.XmlHelper;

public class BiomodelThumbnailServerResource extends BiomodelDiagramServerResource {

	@Override
	@Get("image/png")
	public ByteArrayRepresentation get_png() {
		VCellApiApplication application = ((VCellApiApplication)getApplication());
		User vcellUser = application.getVCellUser(getChallengeResponse(),AuthenticationPolicy.ignoreInvalidCredentials);
		
        String vcml = getBiomodelVCML(vcellUser);
        if (vcml == null){
        	throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,"BioModel not found");
        }
        try {
			BioModel bioModel = XmlHelper.XMLToBioModel(new XMLSource(vcml));
			SimulationContext[] sc=bioModel.getSimulationContexts();
			for (int i=0;i<sc.length;i++) {
				if (sc[i].getGeometry().getDimension()>0) {
					ThumbnailImage thumb = sc[i].getGeometry().getGeometrySpec().getThumbnailImage().getCurrentValue();
					Integer imageWidthInPixels = 1000;
					//BufferedImage bufferedImage = ITextWriter.generateDocReactionsImage(bioModel.getModel(), imageWidthInPixels );
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ImageIO.write(thumb, "png", outputStream);
					byte[] imageBytes = outputStream.toByteArray();
					return new ByteArrayRepresentation(imageBytes, MediaType.IMAGE_PNG, imageBytes.length);
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e.getMessage());
		}
	}
}
