/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.client;
import org.vcell.util.document.User;

import cbit.vcell.client.data.OutputContext;
import cbit.vcell.client.server.UserPreferences;
import cbit.vcell.desktop.controls.DataListener;
import cbit.vcell.export.server.ExportSpecs;
/**
 * Insert the type's description here.
 * Creation date: (11/18/2004 11:25:04 AM)
 * @author: Anuradha Lakshminarayana
 */
public interface DataViewerManager extends cbit.rmi.event.ExportListener, SimStatusListener ,cbit.rmi.event.DataJobListener{
/**
 * Add a cbit.vcell.desktop.controls.DataListener.
 */
public void addDataListener(DataListener newListener);


/**
 * Insert the method's description here.
 * Creation date: (6/1/2004 9:58:46 PM)
 */
public UserPreferences getUserPreferences();

public User getUser();

/**
 * Remove a cbit.vcell.desktop.controls.DataListener.
 */
public void removeDataListener(DataListener newListener);


/**
 * Insert the method's description here.
 * Creation date: (6/14/2004 10:55:40 PM)
 * @param newDocument cbit.vcell.document.VCDocument
 */
public abstract void showDataViewerPlotsFrames(javax.swing.JInternalFrame[] plotFrames);
	
/**
 * Insert the method's description here.
 * Creation date: (6/1/2004 9:58:46 PM)
 */
public void startExport(
		OutputContext outputContext,ExportSpecs exportSpecs);
}
