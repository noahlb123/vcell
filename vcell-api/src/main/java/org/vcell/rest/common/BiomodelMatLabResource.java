package org.vcell.rest.common;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public interface BiomodelMatLabResource {

	public static final String APPLICATION_MATLAB_XML = "application/matlab+xml";
	public static final MediaType VCDOC_MEDIATYPE = MediaType.register(BiomodelMatLabResource.APPLICATION_MATLAB_XML, "VCell matlab+xml Document");

	/**
	 * Returns the list of BioModels accessible to this user
	 */

	@Get(APPLICATION_MATLAB_XML)
	public StringRepresentation get_xml();
	
}
