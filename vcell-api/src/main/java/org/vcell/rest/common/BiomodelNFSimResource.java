package org.vcell.rest.common;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public interface BiomodelNFSimResource {

	public static final String APPLICATION_NFSim_XML = "application/NFSim+xml";
	public static final MediaType VCDOC_MEDIATYPE = MediaType.register(BiomodelNFSimResource.APPLICATION_NFSim_XML, "VCell NFSim+xml Document");

	/**
	 * Returns the list of BioModels accessible to this user
	 */

	@Get(APPLICATION_NFSim_XML)
	public StringRepresentation get_xml();
	
}
