package cbit.vcell.client;

import cbit.vcell.field.FieldDataGUIPanel;
import cbit.vcell.field.FieldDataWindow;
import cbit.vcell.client.server.*;
import cbit.vcell.client.task.AsynchClientTask;
import cbit.vcell.client.task.ClientTaskDispatcher;
import java.awt.*;
import cbit.vcell.client.desktop.*;
import javax.swing.*;
import org.vcell.util.BeanUtils;
import org.vcell.util.document.VCDocument;
import org.vcell.util.gui.GlassPane;

import java.util.*;
import java.awt.event.*;
/**
 * Insert the type's description here.
 * Creation date: (5/24/2004 10:58:42 AM)
 * @author: Ion Moraru
 */
public class ClientMDIManager implements MDIManager {
	public final static String DATABASE_WINDOW_ID = "DatabaseWindow";
	public final static String TESTING_FRAMEWORK_WINDOW_ID = "TestingFrameworkWindow";
	public final static String BIONETGEN_WINDOW_ID = "BioNetGenWindow";
	public final static String FIELDDATA_WINDOW_ID = "FieldDataWindow";
	private RequestManager requestManager = null;
	private Hashtable<String, TopLevelWindow> windowsHash = new Hashtable<String, TopLevelWindow>();
	private Hashtable<String, TopLevelWindowManager> managersHash = new Hashtable<String, TopLevelWindowManager>();
	private int newlyCreatedDesktops = 0;
	
	private WindowAdapter windowListener = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			getRequestManager().closeWindow(((TopLevelWindow)e.getWindow()).getTopLevelWindowManager().getManagerID(), true);
		}
	};

/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 10:59:51 AM)
 * @param vcellClient cbit.vcell.client.VCellClient
 */
public ClientMDIManager(RequestManager requestManager) {
	setRequestManager(requestManager);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:45:55 PM)
 * @param windowID java.lang.String
 */
public JFrame blockWindow(final String windowID) {
	if (haveWindow(windowID)) {
		JFrame f = (JFrame)getWindowsHash().get(windowID);
		GlassPane gp = new GlassPane(false);  // not used for blocking, only for painting as disabled
		gp.setPaint(true);
		f.setGlassPane(gp);
		gp.setVisible(true);
		//f.setEnabled(false); // blocks window - also disables heavyweight part of frame (title bar widgets, moving, resizing)
		return f;
	} else {
		return null;
	}
}

/**
 * Insert the method's description here.
 * Creation date: (5/25/2004 2:03:47 AM)
 * @return int
 */
void cleanup() {
	if (numberOfWindowsShowing() == 0) {
		// last window was closed, hash still contains recyclables
		Enumeration<TopLevelWindowManager> recyclableWindowManagers = getManagersHash().elements();
		while (recyclableWindowManagers.hasMoreElements()) {
			String managerID = recyclableWindowManagers.nextElement().getManagerID();
			JFrame window = (JFrame)getWindowsHash().get(managerID);
			getManagersHash().remove(managerID);
			getWindowsHash().remove(managerID);
			window.removeWindowListener(windowListener);
			window.dispose();
		}
		// dispose of reusable dialogs
		// getDatabaseWindowManager().cleanup();
		// we are clean now... all our threads *should* die and JVM should exit if no other application running
	}
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:20:58 AM)
 * @param windowID java.lang.String
 */
public int closeWindow(String windowID) {
	if (haveWindow(windowID)) {
		JFrame window = (JFrame)getWindowsHash().get(windowID);
		if (window.isShowing()) {
			TopLevelWindowManager manager = (TopLevelWindowManager)getManagersHash().get(windowID);
			if (((TopLevelWindowManager)getManagersHash().get(windowID)).isRecyclable()) {
				// just hide the window
				window.setVisible(false);
			} else {
				// dispose both window and manager
				getManagersHash().remove(windowID);
				getWindowsHash().remove(windowID);
				if (manager instanceof DocumentWindowManager || manager instanceof TestingFrameworkWindowManager) {
					getRequestManager().getAsynchMessageManager().removeSimStatusListener((SimStatusListener)manager);
					getRequestManager().getAsynchMessageManager().removeExportListener((cbit.rmi.event.ExportListener)manager);
					getRequestManager().getAsynchMessageManager().removeDataJobListener((cbit.rmi.event.DataJobListener)manager);
				}
				window.removeWindowListener(windowListener);
				window.dispose();
				org.vcell.util.gui.SwingHacks.cleanup();				
			}
		}
	}
	// others need to know... (e.g. last window was closed)
	return numberOfWindowsShowing();
}

/**
 * Insert the method's description here.
 * Creation date: (5/27/2006 4:10:10 PM)
 * @return java.lang.String
 * @param version cbit.sql.Version
 */
public static String createCanonicalTitle(int docType,org.vcell.util.document.Version version) {
	
	String docName = (version != null?version.getName():"NoName");
	java.util.Date docDate = (version != null?version.getDate():null);
	org.vcell.util.document.VersionFlag versionFlag = (version != null?version.getFlag():null);
	return
		(versionFlag != null && versionFlag.compareEqual(org.vcell.util.document.VersionFlag.Archived)? "("+org.vcell.util.document.CurateSpec.CURATE_TYPE_STATES[org.vcell.util.document.CurateSpec.ARCHIVE]+") ":"")+
		(versionFlag != null && versionFlag.compareEqual(org.vcell.util.document.VersionFlag.Published)?"("+org.vcell.util.document.CurateSpec.CURATE_TYPE_STATES[org.vcell.util.document.CurateSpec.PUBLISH]+") ":"")+
		(docType == VCDocument.BIOMODEL_DOC?"BIOMODEL: ":"")+
		(docType == VCDocument.MATHMODEL_DOC?"MATHMODEL: ":"")+
		(docType == VCDocument.GEOMETRY_DOC?"GEOMETRY: ":"")+
		docName+" "+
		"("+(docDate != null?docDate.toString():"NoDate")+")";
	
}


/**
 * Insert the method's description here.
 * Creation date: (5/5/2004 9:07:12 PM)
 */
private DocumentWindow createDocumentWindow() {
	DocumentWindow documentWindow = new DocumentWindow();
	// stagger 90% screen size windows
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	documentWindow.setSize((int)(screenSize.getWidth()*0.9), (int)(screenSize.getHeight()*0.9));
	int location = (25 * getNewlyCreatedDesktops())%(screenSize.width/2);
	documentWindow.setLocation(location,location);
	if (getNewlyCreatedDesktops() == 0) {
		// first window
		// cbit.util.BeanUtils.attemptMaximize(documentWindow);	
	}
	return documentWindow;
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:20:58 AM)
 * @param vcDocument cbit.vcell.document.VCDocument
 */
public void createNewDocumentWindow(final DocumentWindowManager windowManager) {
	// used for opening new document windows
	// assumes caller checked for having this document already open

	// make the window
	Hashtable<String, Object> hash = new Hashtable<String, Object>();
	AsynchClientTask task1  = new AsynchClientTask("Creating New Document Window", AsynchClientTask.TASKTYPE_SWING_BLOCKING) {
		public void run(Hashtable<String, Object> hashTable) throws Exception {
			DocumentWindow documentWindow = createDocumentWindow();
			documentWindow.setWorkArea(windowManager.getComponent());
			
			// keep track of things
			String windowID = windowManager.getManagerID();
			getWindowsHash().put(windowID, documentWindow);
			getManagersHash().put(windowID, windowManager);
			// wire manager to events
			getRequestManager().getAsynchMessageManager().addSimStatusListener(windowManager);
			getRequestManager().getAsynchMessageManager().addExportListener(windowManager);
			getRequestManager().getAsynchMessageManager().addDataJobListener(windowManager);
			// get the window ready
			setCanonicalTitle(windowID);
			documentWindow.setWindowManager(windowManager);
			documentWindow.addWindowListener(windowListener); // listen for event when user clicks window close button and send request to manager
			setNewlyCreatedDesktops(getNewlyCreatedDesktops() + 1);
			getRequestManager().updateStatusNow(); // initialize status bar with current status (also syncs all other windows)
			// done
			documentWindow.setVisible(true);
		}
	};
	AsynchClientTask[] taskArray = new AsynchClientTask[]{task1};
	ClientTaskDispatcher.dispatch(null, hash, taskArray);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 12:55:28 PM)
 */
void createRecyclableWindows() {
	// we make'em only once, during application initialization sequence; happens before first connection attempt
	/* database window */
	if (! getWindowsHash().containsKey(DATABASE_WINDOW_ID)) {
		// make the window
		DatabaseWindow databaseWindow = new DatabaseWindow();
		DatabaseWindowPanel databaseWindowPanel = new DatabaseWindowPanel();
		databaseWindow.setWorkArea(databaseWindowPanel);
		databaseWindow.setSize(1000, 900);
		BeanUtils.centerOnScreen(databaseWindow);
		// make the manager
		DatabaseWindowManager windowManager = new DatabaseWindowManager(databaseWindowPanel, getRequestManager());
		// keep track of things
		getWindowsHash().put(DATABASE_WINDOW_ID, databaseWindow);
		getManagersHash().put(DATABASE_WINDOW_ID, windowManager);
		// get window ready
		setCanonicalTitle(DATABASE_WINDOW_ID);
		databaseWindow.setDatabaseWindowManager(windowManager);
		databaseWindowPanel.setDatabaseWindowManager(windowManager);
		blockWindow(DATABASE_WINDOW_ID);
		// listen for event when user clicks window close button
		databaseWindow.addWindowListener(windowListener);
	}
	/* testing framework */
	//...
	//cbit.vcell.server.User currentUser = null;
	//if (getRequestManager().getDocumentManager()!=null){
		//currentUser = getRequestManager().getDocumentManager().getUser();
	//}
	if (! getWindowsHash().containsKey(TESTING_FRAMEWORK_WINDOW_ID) /*&& currentUser!=null && currentUser.isTestAccount()*/) {
		// make the window
		TestingFrameworkWindow testingFrameworkWindow = new TestingFrameworkWindow();
		TestingFrameworkWindowPanel testingFrameworkWindowPanel = new TestingFrameworkWindowPanel();
		testingFrameworkWindow.setWorkArea(testingFrameworkWindowPanel);
		testingFrameworkWindow.setSize(1000, 900);
		BeanUtils.centerOnScreen(testingFrameworkWindow);
		// make the manager
		TestingFrameworkWindowManager windowManager = new TestingFrameworkWindowManager(testingFrameworkWindowPanel, getRequestManager());
		// keep track of things
		getWindowsHash().put(TESTING_FRAMEWORK_WINDOW_ID, testingFrameworkWindow);
		getManagersHash().put(TESTING_FRAMEWORK_WINDOW_ID, windowManager);
		// get window ready
		setCanonicalTitle(TESTING_FRAMEWORK_WINDOW_ID);
		testingFrameworkWindow.setTestingFrameworkWindowManager(windowManager);
		testingFrameworkWindowPanel.setTestingFrameworkWindowManager(windowManager);
		blockWindow(TESTING_FRAMEWORK_WINDOW_ID);
		
		// wiring up export and simStatus listener to update viewing simulations
		getRequestManager().getAsynchMessageManager().addSimStatusListener(windowManager);
		getRequestManager().getAsynchMessageManager().addExportListener(windowManager);
		getRequestManager().getAsynchMessageManager().addDataJobListener(windowManager);
		
		// listen for event when user clicks window close button
		testingFrameworkWindow.addWindowListener(windowListener);
	}

	if (! getWindowsHash().containsKey(BIONETGEN_WINDOW_ID) ) {
		// make the window
		BNGWindow bngWindow = new BNGWindow();
		cbit.vcell.client.bionetgen.BNGOutputPanel bngOutputPanel = new cbit.vcell.client.bionetgen.BNGOutputPanel();
		bngWindow.setWorkArea(bngOutputPanel);
		bngWindow.setSize(1000, 900);
		BeanUtils.centerOnScreen(bngWindow);
		// make the manager
		BNGWindowManager windowManager = new BNGWindowManager(bngOutputPanel, getRequestManager());
		// keep track of things
		getWindowsHash().put(BIONETGEN_WINDOW_ID, bngWindow);
		getManagersHash().put(BIONETGEN_WINDOW_ID, windowManager);
		// get window ready
		setCanonicalTitle(BIONETGEN_WINDOW_ID);
		// set bngService for bngOutputPanel - thro' clientServerManager or separate manager for bionetgen?
		bngWindow.setBngWindowManager(windowManager);
		bngOutputPanel.setBngWindowManager(windowManager);
		blockWindow(BIONETGEN_WINDOW_ID);
		
		// listen for event when user clicks window close button
		bngWindow.addWindowListener(windowListener);
	}
	
	if (! getWindowsHash().containsKey(FIELDDATA_WINDOW_ID) ) {
		// make the window
		FieldDataWindow fieldDataWindow = new FieldDataWindow();
		FieldDataGUIPanel fieldDataGUIPanel = new FieldDataGUIPanel();
		fieldDataWindow.setWorkArea(fieldDataGUIPanel);
		fieldDataWindow.pack();
		BeanUtils.centerOnScreen(fieldDataWindow);
		// make the manager
		FieldDataWindowManager fieldDataWindowManager =
			new FieldDataWindowManager(fieldDataGUIPanel,getRequestManager());
		// keep track of things
		getWindowsHash().put(FIELDDATA_WINDOW_ID, fieldDataWindow);
		getManagersHash().put(FIELDDATA_WINDOW_ID, fieldDataWindowManager);
		// get window ready
		setCanonicalTitle(FIELDDATA_WINDOW_ID);
		fieldDataWindow.setFieldDataWindowManager(fieldDataWindowManager);
		fieldDataGUIPanel.setFieldDataWindowManager(fieldDataWindowManager);
		blockWindow(FIELDDATA_WINDOW_ID);
		// listen for event when user clicks window close button
		fieldDataWindow.addWindowListener(windowListener);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 8:57:53 PM)
 * @return cbit.vcell.client.desktop.DatabaseWindowManager
 */
public BNGWindowManager getBNGWindowManager() {
	return (BNGWindowManager)getManagersHash().get(BIONETGEN_WINDOW_ID);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 8:57:53 PM)
 * @return cbit.vcell.client.desktop.DatabaseWindowManager
 */
public DatabaseWindowManager getDatabaseWindowManager() {
	return (DatabaseWindowManager)getManagersHash().get(DATABASE_WINDOW_ID);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 12:48:08 PM)
 * @return java.util.Hashtable
 */
private Hashtable<String, TopLevelWindowManager> getManagersHash() {
	return managersHash;
}


/**
 * Return the value of the field newlyCreatedDesktops
 */
public int getNewlyCreatedDesktops() {
	return newlyCreatedDesktops;
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:13:23 AM)
 * @return cbit.vcell.client.RequestManager
 */
private RequestManager getRequestManager() {
	return requestManager;
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 8:57:53 PM)
 * @return cbit.vcell.client.desktop.DatabaseWindowManager
 */
public TestingFrameworkWindowManager getTestingFrameworkWindowManager() {
	return (TestingFrameworkWindowManager)getManagersHash().get(TESTING_FRAMEWORK_WINDOW_ID);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 7:55:59 PM)
 * @return cbit.vcell.client.desktop.TopLevelWindowManager
 * @param windowID java.lang.String
 */
public TopLevelWindowManager getWindowManager(java.lang.String windowID) {
	return (TopLevelWindowManager)getManagersHash().get(windowID);
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 8:08:49 PM)
 * @return java.util.Enumeration
 */
public Enumeration<TopLevelWindowManager> getWindowManagers() {
	return getManagersHash().elements();
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:21:31 AM)
 * @return java.util.Hashtable
 */
private Hashtable<String, TopLevelWindow> getWindowsHash() {
	return windowsHash;
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 8:29:15 PM)
 * @return boolean
 * @param windowID java.lang.String
 */
public boolean haveWindow(java.lang.String windowID) {
	return getWindowsHash().containsKey(windowID);
}


/**
 * Insert the method's description here.
 * Creation date: (5/25/2004 2:03:47 AM)
 * @return int
 */
private int numberOfWindowsShowing() {
	int i = 0;
	Enumeration<TopLevelWindow> en = getWindowsHash().elements();
	while (en.hasMoreElements()) {
		i = ((JFrame)en.nextElement()).isShowing() ? i + 1 : i;
	}
	return i;
}


/**
 * Insert the method's description here.
 * Creation date: (1/19/2005 5:28:51 PM)
 */
public void refreshRecyclableWindows() {
	createRecyclableWindows();
}


/**
 * Insert the method's description here.
 * Creation date: (6/10/2004 1:21:27 AM)
 * @param windowID java.lang.String
 */
public void setCanonicalTitle(java.lang.String windowID) {
	if (! haveWindow(windowID)) {
		return;
	}
	String windowTitle = null;
	TopLevelWindowManager manager = getWindowManager(windowID);
	if (manager instanceof DocumentWindowManager) {
		windowTitle =
			createCanonicalTitle(
				((DocumentWindowManager)manager).getVCDocument().getDocumentType(),((DocumentWindowManager)manager).getVCDocument().getVersion());
	} else if (manager instanceof DatabaseWindowManager) {
		windowTitle = "Database Manager";
	} else if (manager instanceof TestingFrameworkWindowManager) {
		windowTitle = "Math Testing Framework";
	} else if (manager instanceof BNGWindowManager) {
		windowTitle = "BioNetGen";
	} else if (manager instanceof FieldDataWindowManager) {
		windowTitle = "Field Data Manager";
	}
	((JFrame)getWindowsHash().get(windowID)).setTitle(windowTitle);
}

/**
 * Set the value of the field newlyCreatedDesktops
 */
private void setNewlyCreatedDesktops(int aNewlyCreatedDesktops) {
	newlyCreatedDesktops = aNewlyCreatedDesktops;
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:13:23 AM)
 * @param newRequestManager cbit.vcell.client.RequestManager
 */
private void setRequestManager(RequestManager newRequestManager) {
	requestManager = newRequestManager;
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:20:58 AM)
 * @param windowID java.lang.String
 */
public void showWindow(java.lang.String windowID) {
	if (haveWindow(windowID)) {
		JFrame frame = (JFrame)getWindowsHash().get(windowID);
		frame.setState(Frame.NORMAL);
		frame.setVisible(true);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 11:45:55 PM)
 * @param windowID java.lang.String
 */
public void unBlockWindow(final String windowID) {
	if (haveWindow(windowID)) {
		JFrame f = (JFrame)getWindowsHash().get(windowID);
		f.setGlassPane(new JPanel());
		f.getGlassPane().setVisible(false);
		((JPanel)f.getGlassPane()).setOpaque(false);
		f.setEnabled(true);
	}
}

/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 3:15:04 PM)
 * @param connectionStatus cbit.vcell.client.server.ConnectionStatus
 */
public void updateConnectionStatus(ConnectionStatus connectionStatus) {
	if (connectionStatus.getStatus() == ConnectionStatus.CONNECTED) {
		unBlockWindow(DATABASE_WINDOW_ID);
		unBlockWindow(TESTING_FRAMEWORK_WINDOW_ID);
		unBlockWindow(BIONETGEN_WINDOW_ID);
		unBlockWindow(FIELDDATA_WINDOW_ID);
	} else {
		blockWindow(DATABASE_WINDOW_ID);
		blockWindow(TESTING_FRAMEWORK_WINDOW_ID);
		blockWindow(BIONETGEN_WINDOW_ID);
		blockWindow(FIELDDATA_WINDOW_ID);
	}
	Enumeration<TopLevelWindow> windows = getWindowsHash().elements();
	while (windows.hasMoreElements()) {
		TopLevelWindow window = windows.nextElement();
		window.updateConnectionStatus(connectionStatus);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (5/28/2004 3:17:39 AM)
 * @param oldID java.lang.String
 * @param newID java.lang.String
 */
public void updateDocumentID(java.lang.String oldID, java.lang.String newID) {
	TopLevelWindow window = windowsHash.get(oldID);
	TopLevelWindowManager manager = managersHash.get(oldID);
	windowsHash.remove(oldID);
	managersHash.remove(oldID);
	windowsHash.put(newID, window);
	managersHash.put(newID, manager);
	setCanonicalTitle(newID);
}
	
/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 3:15:04 PM)
 * @param freeBytes long
 * @param totalBytes long
 */
public void updateMemoryStatus(long freeBytes, long totalBytes) {
	Enumeration<TopLevelWindow> windows = getWindowsHash().elements();
	while (windows.hasMoreElements()) {
		TopLevelWindow window = windows.nextElement();
		window.updateMemoryStatus(freeBytes, totalBytes);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (5/24/2004 3:15:04 PM)
 * @param progress int
 */
public void updateWhileInitializing(int progress) {
	Enumeration<TopLevelWindow> windows = getWindowsHash().elements();
	while (windows.hasMoreElements()) {
		TopLevelWindow window = windows.nextElement();
		window.updateWhileInitializing(progress);
	}
}
}