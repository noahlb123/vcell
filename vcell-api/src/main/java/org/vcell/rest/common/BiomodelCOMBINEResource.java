package org.vcell.rest.common;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public interface BiomodelCOMBINEResource {

	public static final String APPLICATION_COMBINE_XML = "application/COMBINE+xml";
	public static final MediaType VCDOC_MEDIATYPE = MediaType.register(BiomodelCOMBINEResource.APPLICATION_COMBINE_XML, "VCell COMBINE+xml Document");

	/**
	 * Returns the list of BioModels accessible to this user
	 */

	@Get(APPLICATION_COMBINE_XML)
	public StringRepresentation get_xml();
	
}
