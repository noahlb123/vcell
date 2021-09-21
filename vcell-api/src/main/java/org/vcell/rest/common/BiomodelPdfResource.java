package org.vcell.rest.common;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public interface BiomodelPdfResource {

	public static final String APPLICATION_Pdf_XML = "application/Pdf+xml";
	public static final MediaType VCDOC_MEDIATYPE = MediaType.register(BiomodelPdfResource.APPLICATION_Pdf_XML, "VCell Pdf+xml Document");

	/**
	 * Returns the list of BioModels accessible to this user
	 */

	@Get(APPLICATION_Pdf_XML)
	public StringRepresentation get_xml();
	
}
