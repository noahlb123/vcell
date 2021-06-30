package org.vcell.rest.common;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public interface BiomodelSEDMLResource {

	public static final String APPLICATION_SEDML_XML = "application/sedml+xml";
	public static final MediaType VCDOC_MEDIATYPE = MediaType.register(BiomodelSEDMLResource.APPLICATION_SEDML_XML, "VCell sbml+xml Document");

	/**
	 * Returns the list of BioModels accessible to this user
	 */

	@Get(APPLICATION_SEDML_XML)
	public StringRepresentation get_xml();
	
}
