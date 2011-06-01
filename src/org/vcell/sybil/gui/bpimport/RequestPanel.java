/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.gui.bpimport;

/*   RequestPanel  --- by Oliver Ruebenacker, UCHC --- March 2009 to January 2010
 *   Panel to display the results of one request
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.vcell.sybil.actions.ActionSpecs;
import org.vcell.sybil.actions.SpecAction;
import org.vcell.sybil.models.io.FileManager;
import org.vcell.sybil.util.gui.ButtonFormatter;
import org.vcell.sybil.util.http.pathwaycommons.PathwayCommonsRequest;
import org.vcell.sybil.util.http.pathwaycommons.PathwayCommonsResponse;
import org.vcell.sybil.util.http.pathwaycommons.search.PCKeywordResponse;
import org.vcell.sybil.util.http.pathwaycommons.search.PCTextModelResponse;
import org.vcell.sybil.util.http.pathwaycommons.search.PCTextResponse;
import org.vcell.sybil.workers.bpimport.ImportAcceptWorker;
import org.vcell.sybil.workers.input.PathwayCommonsWorker;

import com.hp.hpl.jena.rdf.model.Resource;

public class RequestPanel extends JPanel implements PathwayCommonsWorker.Client {
	
	public static interface Container { public void removePanel(RequestPanel panel); }
	
	private static final long serialVersionUID = -6581309802815958453L;
	protected Container container;
	protected ImportManager importManager;
	protected PathwayCommonsRequest request;
	protected JLabel busyLabel = new JLabel("Handling request, please wait");
	protected JToolBar toolBar = new JToolBar();
	
	public RequestPanel(Container containerNew, ImportManager importManNew, 
			PathwayCommonsRequest requestNew) {
		container = containerNew;
		importManager = importManNew;
		request = requestNew;
		setLayout(new BorderLayout());
		new PathwayCommonsWorker(this).run(this);
		busyLabel.setHorizontalAlignment(JLabel.CENTER);
		add(busyLabel);
		toolBar.add(new JLabel("Results for " + request.description()));
		toolBar.addSeparator();
		JButton buttonRemove = new JButton(new RemoveAction(this));
		toolBar.add(buttonRemove);
		ButtonFormatter.format(buttonRemove);
		add(toolBar, "North");
	}
	
	public FileManager fileMan() { return importManager.fileManager(); }
	public PathwayCommonsRequest request() { return request; }
	public void removeThis() { container.removePanel(this); }
	
	public void setResponse(PathwayCommonsResponse response) { 
		ResponsePanel responsePanel;
		if(response instanceof PCTextModelResponse) {
			PCTextModelResponse textModelResponse = (PCTextModelResponse) response;
			toolBar.add(new JLabel("(Size: " + textModelResponse.text().length() + ")"));
		}
		if(response instanceof PCKeywordResponse) {
			responsePanel = new KeywordResponsePanel(importManager, (PCKeywordResponse) response);
		} else if(response instanceof PCTextModelResponse) {
			TextModelResponsePanel textModelResponsePanel = new TextModelResponsePanel((PCTextModelResponse) response);
			responsePanel = textModelResponsePanel;
			JButton buttonAccept = new JButton(new ImportAction(importManager, textModelResponsePanel));
			ButtonFormatter.format(buttonAccept);
			toolBar.add(buttonAccept);
		} else if(response instanceof PCTextResponse) {
			responsePanel = new TextResponsePanel((PCTextResponse) response);
		} else {
			responsePanel = new DefaultResponsePanel(response);			
		}
		remove(busyLabel);
		add(responsePanel);
		revalidate();
	}
	
	static protected class RemoveAction extends SpecAction {
		
		private static final long serialVersionUID = -9126068580382992664L;
		protected RequestPanel panel;
		
		public RemoveAction(RequestPanel panelNew) {
			super(new ActionSpecs("Remove These Results", "remove panel with these results", 
					"remove this panel and forget results"));
			panel = panelNew;
		}
		
		public void actionPerformed(ActionEvent event) { panel.removeThis(); }

	}

	static protected class ImportAction extends SpecAction implements ImportAcceptWorker.Client {
		
		private static final long serialVersionUID = -9126068580382992664L;
		
		protected ImportManager importManager;
		protected TextModelResponsePanel panel;
		
		public ImportAction(ImportManager importManNew, TextModelResponsePanel panelNew) {
			super(new ActionSpecs("Import Selected", "Import selected entities", 
					"Import selected entities"));
			importManager = importManNew;
			panel = panelNew;
		}
		
		public void actionPerformed(ActionEvent event) { new ImportAcceptWorker(this).run(panel); }
		public FileManager fileManager() { return importManager.fileManager(); }
		public PCTextModelResponse response() { return panel.response(); }
		public Set<Resource> selectedResources() { return panel.selectedEntities(); }

		public boolean requestsSmelting() { return panel.requestsSmelting(); }

	}

}
