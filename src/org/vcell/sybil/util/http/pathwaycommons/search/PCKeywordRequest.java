/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.util.http.pathwaycommons.search;

/*   PathwayCommonsSearch  --- by Oliver Ruebenacker, UCHC --- March 2009 to January 2010
 *   Launch a web request using command search from Pathway Commons
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.ParserConfigurationException;

import org.vcell.sybil.util.http.pathwaycommons.PCExceptionResponse;
import org.vcell.sybil.util.http.pathwaycommons.PCRParameter;
import org.vcell.sybil.util.http.pathwaycommons.PathwayCommonsRequest;
import org.vcell.sybil.util.http.pathwaycommons.PathwayCommonsResponse;
import org.vcell.sybil.util.xml.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class PCKeywordRequest extends PathwayCommonsRequest {
	
	protected String keyword;
	
	public PCKeywordRequest(String keywordNew)  { 
		super(PCRParameter.CmdVersion.search); 
		keyword = keywordNew;
		paras().add(new PCRParameter.Q(keyword));
		paras().add(PCRParameter.Output.xml);
	}

	public PathwayCommonsResponse response() {
		Document document = null;
		try { 
			URL url = url();
			URLConnection connection = url.openConnection();
			document = DOMUtil.parse(connection.getInputStream());
		} 
		catch (MalformedURLException e) { return new PCExceptionResponse(this, e); } 
		catch (IOException e) { return new PCExceptionResponse(this, e); } 
		catch (ParserConfigurationException e) { return new PCExceptionResponse(this, e); } 
		catch (SAXException e) { return new PCExceptionResponse(this, e); }
		Element errorElement = DOMUtil.firstChildElement(document, "error");
		if(errorElement != null) { return new PCErrorResponse(this, errorElement); }
		Element searchResponse = DOMUtil.firstChildElement(document, "search_response");
		if(searchResponse != null) { return new PCKeywordResponse(this, searchResponse); }
		return new PCEmptyResponse(this);
	}

	public String keyword() { return keyword; }
	public String description() { return "Keyword search for \"" + keyword + "\""; }
	@Override public String shortTitle() { return "Keyword \"" + keyword + "\""; }
	
}
