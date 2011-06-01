/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.client.desktop;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.vcell.util.BeanUtils;
import org.vcell.util.document.BioModelInfo;
import org.vcell.util.document.MathModelInfo;
import org.vcell.util.document.VCDocument;
import org.vcell.util.document.VCDocumentInfo;
import org.vcell.util.gui.DialogUtils;

import cbit.gui.TextFieldAutoCompletion;
import cbit.vcell.client.DatabaseWindowManager;
import cbit.vcell.clientdb.DocumentManager;
import cbit.vcell.desktop.BioModelDbTreePanel;
import cbit.vcell.desktop.GeometryTreePanel;
import cbit.vcell.desktop.MathModelDbTreePanel;
import cbit.vcell.geometry.GeometryInfo;
import cbit.vcell.messaging.admin.DatePanel;
/**
 * Insert the type's description here.
 * Creation date: (5/24/2004 1:13:39 PM)
 * @author: Ion Moraru
 */
public class DatabaseWindowPanel extends JPanel {
	private BioModelDbTreePanel ivjBioModelDbTreePanel1 = null;
	private GeometryTreePanel ivjGeometryTreePanel1 = null;
	private JTabbedPane ivjJTabbedPane1 = null;
	private MathModelDbTreePanel ivjMathModelDbTreePanel1 = null;
	private DocumentManager fieldDocumentManager = null;
	private boolean ivjConnPtoP1Aligning = false;
	private boolean ivjConnPtoP2Aligning = false;
	private boolean ivjConnPtoP3Aligning = false;
	private IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private DatabaseWindowManager fieldDatabaseWindowManager = null;
	private VCDocumentInfo fieldSelectedDocumentInfo = null;
	
	private JToggleButton searchToolBarButton = null;

class IvjEventHandler implements java.awt.event.ActionListener, java.beans.PropertyChangeListener, javax.swing.event.ChangeListener, ItemListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == DatabaseWindowPanel.this.getBioModelDbTreePanel1()) 
				connEtoC1(e);
			if (e.getSource() == DatabaseWindowPanel.this.getMathModelDbTreePanel1()) 
				connEtoC2(e);
			if (e.getSource() == DatabaseWindowPanel.this.getGeometryTreePanel1()) 
				connEtoC3(e);
		};
		public void propertyChange(java.beans.PropertyChangeEvent evt) {
			if (evt.getSource() == DatabaseWindowPanel.this && (evt.getPropertyName().equals("documentManager"))) {
				connPtoP1SetTarget();
				connPtoP2SetTarget();
				connPtoP3SetTarget();
			}
			if (evt.getSource() == DatabaseWindowPanel.this.getBioModelDbTreePanel1() && (evt.getPropertyName().equals("documentManager"))) 
				connPtoP1SetSource();
			if (evt.getSource() == DatabaseWindowPanel.this.getMathModelDbTreePanel1() && (evt.getPropertyName().equals("documentManager"))) 
				connPtoP2SetSource();
			if (evt.getSource() == DatabaseWindowPanel.this.getGeometryTreePanel1() && (evt.getPropertyName().equals("documentManager"))) 
				connPtoP3SetSource();
			if (evt.getSource() == DatabaseWindowPanel.this.getGeometryTreePanel1() && (evt.getPropertyName().equals("selectedVersionInfo"))) 
				connEtoC5(evt);
			if (evt.getSource() == DatabaseWindowPanel.this.getMathModelDbTreePanel1() && (evt.getPropertyName().equals("selectedVersionInfo"))) 
				connEtoC6(evt);
			if (evt.getSource() == DatabaseWindowPanel.this.getBioModelDbTreePanel1() && (evt.getPropertyName().equals("selectedVersionInfo"))) 
				connEtoC7(evt);
		};
		public void stateChanged(javax.swing.event.ChangeEvent e) {
			if (e.getSource() == DatabaseWindowPanel.this.getJTabbedPane1()) {
				connEtoC4(e);
				final int selectedIndex = getJTabbedPane1().getSelectedIndex();
				boolean bSelected = true;
				switch (selectedIndex) {
					case 0 :
						bSelected = getBioModelDbTreePanel1().isSearchPanelVisible();
						break;
					case 1:
						bSelected = getMathModelDbTreePanel1().isSearchPanelVisible();
						break;
					case 2:
						bSelected = getGeometryTreePanel1().isSearchPanelVisible();
						break;
				}
				getSearchToolBarButton().setSelected(bSelected);
			}			
		}
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == getSearchToolBarButton()) {
				boolean selected = getSearchToolBarButton().isSelected();
				Border border = null;
				Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 4);
				final int selectedIndex = getJTabbedPane1().getSelectedIndex();
				if (selected) {
					border = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), emptyBorder);
				} else {
					border = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), emptyBorder);
				}
				getSearchToolBarButton().setBorder(border);
				switch (selectedIndex) {
				case 0 :
					getBioModelDbTreePanel1().setSearchPanelVisible(selected);
					if (!selected) {
						getBioModelDbTreePanel1().search(true);
					}
					break;
				case 1:
					getMathModelDbTreePanel1().setSearchPanelVisible(selected);
					if (!selected) {
						getMathModelDbTreePanel1().search(true);
					}
					break;
				case 2:
					getGeometryTreePanel1().setSearchPanelVisible(selected);
					if (!selected) {
						getGeometryTreePanel1().search(true);
					}
					break;
				}
			}
		}
	}

/**
 * DatabaseWindowPanel constructor comment.
 */
public DatabaseWindowPanel() {
	super();
	initialize();
}

/**
 * connEtoC1:  (BioModelDbTreePanel1.action.actionPerformed(java.awt.event.ActionEvent) --> DatabaseWindowPanel.bioModelDbTreePanel1_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.dbTreePanelActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * connEtoC2:  (MathModelDbTreePanel1.action.actionPerformed(java.awt.event.ActionEvent) --> DatabaseWindowPanel.mathModelDbTreePanel1_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.dbTreePanelActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * connEtoC3:  (GeometryTreePanel1.action.actionPerformed(java.awt.event.ActionEvent) --> DatabaseWindowPanel.geometryTreePanel1_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.dbTreePanelActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * connEtoC4:  (JTabbedPane1.change.stateChanged(javax.swing.event.ChangeEvent) --> DatabaseWindowPanel.tabbedPaneStateChanged(Ljavax.swing.event.ChangeEvent;)V)
 * @param arg1 javax.swing.event.ChangeEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC4(javax.swing.event.ChangeEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.currentDocumentInfo();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * connEtoC5:  (GeometryTreePanel1.selectedVersionInfo --> DatabaseWindowPanel.currentDocumentInfo()V)
 * @param arg1 java.beans.PropertyChangeEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC5(java.beans.PropertyChangeEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.currentDocumentInfo();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connEtoC6:  (MathModelDbTreePanel1.selectedVersionInfo --> DatabaseWindowPanel.currentDocumentInfo()V)
 * @param arg1 java.beans.PropertyChangeEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC6(java.beans.PropertyChangeEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.currentDocumentInfo();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connEtoC7:  (BioModelDbTreePanel1.selectedVersionInfo --> DatabaseWindowPanel.currentDocumentInfo()V)
 * @param arg1 java.beans.PropertyChangeEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC7(java.beans.PropertyChangeEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.currentDocumentInfo();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP1SetSource:  (DatabaseWindowPanel.documentManager <--> BioModelDbTreePanel1.documentManager)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP1SetSource() {
	/* Set the source from the target */
	try {
		if (ivjConnPtoP1Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP1Aligning = true;
			this.setDocumentManager(getBioModelDbTreePanel1().getDocumentManager());
			// user code begin {2}
			// user code end
			ivjConnPtoP1Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP1Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP1SetTarget:  (DatabaseWindowPanel.documentManager <--> BioModelDbTreePanel1.documentManager)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP1SetTarget() {
	/* Set the target from the source */
	try {
		if (ivjConnPtoP1Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP1Aligning = true;
			getBioModelDbTreePanel1().setDocumentManager(this.getDocumentManager());
			// user code begin {2}
			// user code end
			ivjConnPtoP1Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP1Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP2SetSource:  (DatabaseWindowPanel.documentManager <--> MathModelDbTreePanel1.documentManager)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP2SetSource() {
	/* Set the source from the target */
	try {
		if (ivjConnPtoP2Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP2Aligning = true;
			this.setDocumentManager(getMathModelDbTreePanel1().getDocumentManager());
			// user code begin {2}
			// user code end
			ivjConnPtoP2Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP2Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP2SetTarget:  (DatabaseWindowPanel.documentManager <--> MathModelDbTreePanel1.documentManager)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP2SetTarget() {
	/* Set the target from the source */
	try {
		if (ivjConnPtoP2Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP2Aligning = true;
			getMathModelDbTreePanel1().setDocumentManager(this.getDocumentManager());
			// user code begin {2}
			// user code end
			ivjConnPtoP2Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP2Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP3SetSource:  (DatabaseWindowPanel.documentManager <--> GeometryTreePanel1.documentManager)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP3SetSource() {
	/* Set the source from the target */
	try {
		if (ivjConnPtoP3Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP3Aligning = true;
			this.setDocumentManager(getGeometryTreePanel1().getDocumentManager());
			// user code begin {2}
			// user code end
			ivjConnPtoP3Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP3Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP3SetTarget:  (DatabaseWindowPanel.documentManager <--> GeometryTreePanel1.documentManager)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP3SetTarget() {
	/* Set the target from the source */
	try {
		if (ivjConnPtoP3Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP3Aligning = true;
			getGeometryTreePanel1().setDocumentManager(this.getDocumentManager());
			// user code begin {2}
			// user code end
			ivjConnPtoP3Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP3Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * Comment
 */
private void currentDocumentInfo() {
	VCDocumentInfo selectedDocInfo = null;
	switch (getJTabbedPane1().getSelectedIndex()) {
		case VCDocument.BIOMODEL_DOC: {
			selectedDocInfo = (BioModelInfo)getBioModelDbTreePanel1().getSelectedVersionInfo();
			break;
		}
		case VCDocument.MATHMODEL_DOC: {
			selectedDocInfo = (MathModelInfo)getMathModelDbTreePanel1().getSelectedVersionInfo();
			break;
		}
		case VCDocument.GEOMETRY_DOC: {
			selectedDocInfo = (GeometryInfo)getGeometryTreePanel1().getSelectedVersionInfo();
			break;
		}
	}
	setSelectedDocumentInfo(selectedDocInfo);
}


/**
 * Comment
 */
private void dbTreePanelActionPerformed(java.awt.event.ActionEvent e) {
	String actionCommand = e.getActionCommand();
	if (actionCommand.equals("Open") || actionCommand.equals(DatabaseWindowManager.BM_MM_GM_DOUBLE_CLICK_ACTION)) {
		getDatabaseWindowManager().openSelected();
	} else if (actionCommand.equals("Delete")) {
		getDatabaseWindowManager().deleteSelected();
	} else if (actionCommand.equals("Permission")) {
		getDatabaseWindowManager().accessPermissions();
	} else if (actionCommand.equals("Export")) {
		getDatabaseWindowManager().exportDocument();
	} else if (actionCommand.equals("Latest Edition")) {
		getDatabaseWindowManager().compareLatestEdition();
	} else if (actionCommand.equals("Previous Edition")) {
		getDatabaseWindowManager().comparePreviousEdition();
	} else if (actionCommand.equals("Another Edition...")) {
		getDatabaseWindowManager().compareAnotherEdition();
	} else if (actionCommand.equals("Another Model...")) {
		getDatabaseWindowManager().compareAnotherModel();
	} else if (actionCommand.equals("Models Using Geometry")) {
		getDatabaseWindowManager().findModelsUsingSelectedGeometry();
	} else if (actionCommand.equals("Archive")) {
		getDatabaseWindowManager().archive();
	} else if (actionCommand.equals("Publish")) {
		getDatabaseWindowManager().publish();
	}
}


/**
 * Return the BioModelDbTreePanel1 property value.
 * @return cbit.vcell.desktop.BioModelDbTreePanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private BioModelDbTreePanel getBioModelDbTreePanel1() {
	if (ivjBioModelDbTreePanel1 == null) {
		try {
			ivjBioModelDbTreePanel1 = new BioModelDbTreePanel();
			ivjBioModelDbTreePanel1.setName("BioModelDbTreePanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjBioModelDbTreePanel1;
}


/**
 * Gets the databaseWindowManager property (cbit.vcell.client.DatabaseWindowManager) value.
 * @return The databaseWindowManager property value.
 * @see #setDatabaseWindowManager
 */
public DatabaseWindowManager getDatabaseWindowManager() {
	return fieldDatabaseWindowManager;
}


/**
 * Gets the documentManager property (cbit.vcell.clientdb.DocumentManager) value.
 * @return The documentManager property value.
 * @see #setDocumentManager
 */
public DocumentManager getDocumentManager() {
	return fieldDocumentManager;
}


/**
 * Return the GeometryTreePanel1 property value.
 * @return cbit.vcell.desktop.GeometryTreePanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private GeometryTreePanel getGeometryTreePanel1() {
	if (ivjGeometryTreePanel1 == null) {
		try {
			ivjGeometryTreePanel1 = new GeometryTreePanel();
			ivjGeometryTreePanel1.setName("GeometryTreePanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjGeometryTreePanel1;
}


/**
 * Return the JTabbedPane1 property value.
 * @return javax.swing.JTabbedPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTabbedPane getJTabbedPane1() {
	if (ivjJTabbedPane1 == null) {
		try {
			ivjJTabbedPane1 = new javax.swing.JTabbedPane();
			ivjJTabbedPane1.insertTab("BioModels", null, getBioModelDbTreePanel1(), null, 0);
			ivjJTabbedPane1.insertTab("MathModels", null, getMathModelDbTreePanel1(), null, 1);
			ivjJTabbedPane1.insertTab("Geometries", null, getGeometryTreePanel1(), null, 2);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJTabbedPane1;
}

/**
 * Return the MathModelDbTreePanel1 property value.
 * @return cbit.vcell.desktop.MathModelDbTreePanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MathModelDbTreePanel getMathModelDbTreePanel1() {
	if (ivjMathModelDbTreePanel1 == null) {
		try {
			ivjMathModelDbTreePanel1 = new MathModelDbTreePanel();
			ivjMathModelDbTreePanel1.setName("MathModelDbTreePanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMathModelDbTreePanel1;
}


/**
 * Comment
 */
public VCDocumentInfo getSelectedDocumentInfo() {
	return fieldSelectedDocumentInfo;
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	exception.printStackTrace(System.out);
}


/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	this.addPropertyChangeListener(ivjEventHandler);
	getBioModelDbTreePanel1().addPropertyChangeListener(ivjEventHandler);
	getMathModelDbTreePanel1().addPropertyChangeListener(ivjEventHandler);
	getGeometryTreePanel1().addPropertyChangeListener(ivjEventHandler);
	getBioModelDbTreePanel1().addActionListener(ivjEventHandler);
	getMathModelDbTreePanel1().addActionListener(ivjEventHandler);
	getGeometryTreePanel1().addActionListener(ivjEventHandler);
	getJTabbedPane1().addChangeListener(ivjEventHandler);
	connPtoP1SetTarget();
	connPtoP2SetTarget();
	connPtoP3SetTarget();
	getSearchToolBarButton().addItemListener(ivjEventHandler);
}

/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		setLayout(new java.awt.BorderLayout());
		setSize(662, 657);
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(getSearchToolBarButton());
		add(toolBar, BorderLayout.PAGE_START);
		add(getJTabbedPane1(), BorderLayout.CENTER);
		
		getBioModelDbTreePanel1().setSearchPanelVisible(false);
		getMathModelDbTreePanel1().setSearchPanelVisible(false);
		getGeometryTreePanel1().setSearchPanelVisible(false);
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

private JToggleButton getSearchToolBarButton() {
	if (searchToolBarButton == null) {
		searchToolBarButton = new JToggleButton("Search");
		Icon searchIcon = new ImageIcon(getClass().getResource("/icons/search_icon.gif"));
		searchToolBarButton.setIcon(searchIcon);
		Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 4);
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), emptyBorder);
		getSearchToolBarButton().setBorder(border);
	}
	return searchToolBarButton;
}

/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		JFrame frame = new javax.swing.JFrame();
		DatabaseWindowPanel aDatabaseWindowPanel;
		aDatabaseWindowPanel = new DatabaseWindowPanel();
		frame.setContentPane(aDatabaseWindowPanel);
		frame.setSize(aDatabaseWindowPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		BeanUtils.centerOnScreen(frame);
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JPanel");
		exception.printStackTrace(System.out);
	}
}


/**
 * Sets the databaseWindowManager property (cbit.vcell.client.DatabaseWindowManager) value.
 * @param databaseWindowManager The new value for the property.
 * @see #getDatabaseWindowManager
 */
public void setDatabaseWindowManager(DatabaseWindowManager databaseWindowManager) {
	DatabaseWindowManager oldValue = fieldDatabaseWindowManager;
	fieldDatabaseWindowManager = databaseWindowManager;
	firePropertyChange("databaseWindowManager", oldValue, databaseWindowManager);
}


/**
 * Sets the documentManager property (cbit.vcell.clientdb.DocumentManager) value.
 * @param documentManager The new value for the property.
 * @see #getDocumentManager
 */
public void setDocumentManager(DocumentManager documentManager) {
	DocumentManager oldValue = fieldDocumentManager;
	fieldDocumentManager = documentManager;
	firePropertyChange("documentManager", oldValue, documentManager);
}


/**
 */
public void setLatestOnly(boolean latestOnly) {
	getBioModelDbTreePanel1().setLatestVersionOnly(latestOnly);
	getMathModelDbTreePanel1().setLatestVersionOnly(latestOnly);
	getGeometryTreePanel1().setLatestVersionOnly(latestOnly);
}


/**
 * Sets the selectedDocumentInfo property (cbit.vcell.document.VCDocumentInfo) value.
 * @param selectedDocumentInfo The new value for the property.
 * @see #getSelectedDocumentInfo
 */
private void setSelectedDocumentInfo(VCDocumentInfo selectedDocumentInfo) {
	VCDocumentInfo oldValue = fieldSelectedDocumentInfo;
	fieldSelectedDocumentInfo = selectedDocumentInfo;
	firePropertyChange("selectedDocumentInfo", oldValue, selectedDocumentInfo);
}

}
