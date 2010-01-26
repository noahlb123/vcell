package cbit.vcell.client;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JInternalFrame;

import org.vcell.util.BeanUtils;
import org.vcell.util.gui.DialogUtils;

import cbit.vcell.client.data.DataViewer;
import cbit.vcell.client.data.SimulationWorkspaceModelInfo;
import cbit.vcell.client.desktop.simulation.SimulationStatusDetails;
import cbit.vcell.client.desktop.simulation.SimulationStatusDetailsPanel;
import cbit.vcell.client.desktop.simulation.SimulationWindow;
import cbit.vcell.client.desktop.simulation.SimulationWorkspace;
import cbit.vcell.client.server.DataViewerController;
import cbit.vcell.client.task.AsynchClientTask;
import cbit.vcell.client.task.ClientTaskDispatcher;
import cbit.vcell.document.SimulationOwner;
import cbit.vcell.solver.Simulation;
import cbit.vcell.solver.VCSimulationIdentifier;
import cbit.vcell.solver.ode.gui.SimulationStatus;
/**
 * Insert the type's description here.
 * Creation date: (6/7/2004 10:31:36 AM)
 * @author: Ion Moraru
 */
public class ClientSimManager implements java.beans.PropertyChangeListener {
	private DocumentWindowManager documentWindowManager = null;
	private SimulationWorkspace simWorkspace = null;
	private SimulationStatusHash simHash = new SimulationStatusHash();

/**
 * Insert the method's description here.
 * Creation date: (6/7/2004 10:48:50 AM)
 * @param documentWindowManager cbit.vcell.client.DocumentWindowManager
 * @param simulationOwner cbit.vcell.document.SimulationOwner
 */
public ClientSimManager(DocumentWindowManager documentWindowManager, SimulationWorkspace simWorkspace) {
	this.documentWindowManager = documentWindowManager;
	this.simWorkspace = simWorkspace;
	getSimWorkspace().addPropertyChangeListener(this);
	initHash(getSimWorkspace().getSimulations());
}


/**
 * Insert the method's description here.
 * Creation date: (6/7/2004 12:50:45 PM)
 * @return cbit.vcell.client.DocumentWindowManager
 */
DocumentWindowManager getDocumentWindowManager() {
	return documentWindowManager;
}


/**
 * Insert the method's description here.
 * Creation date: (6/7/2004 10:31:36 AM)
 * @return cbit.vcell.solver.ode.gui.SimulationStatus
 * @param simulation cbit.vcell.solver.Simulation
 */
public SimulationStatus getSimulationStatus(Simulation simulation) {
	SimulationStatus cachedSimStatus = simHash.getSimulationStatus(simulation);
	if (cachedSimStatus!=null){
		if (simulation.getIsDirty()) {
			return SimulationStatus.newNeverRan(simulation.getScanCount());
		} else {
			return cachedSimStatus;
		}
	} else {
		// shouldn't really happen
		try {
			throw new RuntimeException("shouldn't really happen");
		} catch (Throwable e) {
			e.printStackTrace(System.out);
		}
		return null;
	}
}


/**
 * Insert the method's description here.
 * Creation date: (6/8/2004 1:17:36 PM)
 * @return cbit.vcell.client.desktop.simulation.SimulationWorkspace
 */
public SimulationWorkspace getSimWorkspace() {
	return simWorkspace;
}

public void preloadSimulationStatus(Simulation[] simulations) {
	initHash(simulations);
}

/**
 * Insert the method's description here.
 * Creation date: (6/7/2004 12:55:18 PM)
 * @param simulations cbit.vcell.solver.Simulation[]
 */
private void initHash(Simulation[] simulations) {
	simHash.changeSimulationInstances(simulations);
	if (simulations != null) {
		for (int i = 0; i < simulations.length; i++){
			SimulationStatus simStatus = simHash.getSimulationStatus(simulations[i]);
			if (simStatus==null || simStatus.isUnknown()){
				// try to get status from server
				simStatus = getDocumentWindowManager().getRequestManager().getServerSimulationStatus(simulations[i].getSimulationInfo());
				if (simStatus != null) {
					simHash.setSimulationStatus(simulations[i], simStatus);
				} else {
					simHash.setSimulationStatus(simulations[i], SimulationStatus.newNeverRan(simulations[i].getScanCount()));
				}
			}
		}
	}
}


	/**
	 * This method gets called when a bound property is changed.
	 * @param evt A PropertyChangeEvent object describing the event source 
	 *   	and the property that has changed.
	 */
public void propertyChange(java.beans.PropertyChangeEvent evt) {
	if (evt.getPropertyName().equals("simulations")) {
		simHash.changeSimulationInstances((Simulation[])evt.getNewValue());
	}
	if (evt.getPropertyName().equals("simulationOwner")) {
		initHash(((SimulationOwner)evt.getNewValue()).getSimulations());
	}
}


/**
 * Insert the method's description here.
 * Creation date: (6/2/2004 3:01:29 AM)
 * @param simulation cbit.vcell.solver.Simulation[]
 */
public void runSimulations(Simulation[] simulations) {
	getDocumentWindowManager().getRequestManager().runSimulations(this, simulations);
}


/**
 * Insert the method's description here.
 * Creation date: (6/7/2004 10:31:36 AM)
 * @param simulations cbit.vcell.solver.Simulation[]
 */
public void showSimulationResults(Simulation[] simulations) {
	if (simulations == null) {
		return;
	}
	Vector<Simulation> v = new Vector<Simulation>();
	for (int i = 0; i < simulations.length; i++){
		if (simulations[i].getSimulationInfo() != null && getSimulationStatus(simulations[i]).getHasData()) {
			v.add(simulations[i]);
		}
	}
	final Simulation[] simsToShow = (Simulation[])BeanUtils.getArray(v, Simulation.class);
	
	Hashtable<String, Object> hash = new Hashtable<String, Object>();

	// Create the AsynchClientTasks 
	ArrayList<AsynchClientTask> taskList = new ArrayList<AsynchClientTask>();
	final String dataViewerControllers_string = "dataViewerControllers";
	final String failures_string = "failures";

	final DocumentWindowManager documentWindowManager = getDocumentWindowManager();
	for (int i = 0; i < simsToShow.length; i++){		
		final Simulation sim  = simsToShow[i];
		final VCSimulationIdentifier vcSimulationIdentifier = simsToShow[i].getSimulationInfo().getAuthoritativeVCSimulationIdentifier();
		final SimulationWindow simWindow = documentWindowManager.haveSimulationWindow(vcSimulationIdentifier);
		
		if (simWindow == null) {			
			AsynchClientTask task = new AsynchClientTask("Retrieving results for " + sim.getName(), AsynchClientTask.TASKTYPE_NONSWING_BLOCKING)  {
				public void run(Hashtable<String, Object> hashTable) throws Exception {						
					Hashtable<Simulation,Throwable> failures = (Hashtable<Simulation,Throwable>)hashTable.get(failures_string);
					if (failures == null) {
						failures = new Hashtable<Simulation, Throwable>();
						hashTable.put(failures_string, failures);
					}
						
					Hashtable<VCSimulationIdentifier, DataViewerController> dataViewerControllers = (Hashtable<VCSimulationIdentifier, DataViewerController>)hashTable.get(dataViewerControllers_string);
					if (dataViewerControllers == null) {
						dataViewerControllers = new Hashtable<VCSimulationIdentifier, DataViewerController>();
						hashTable.put(dataViewerControllers_string, dataViewerControllers);
					}
				
					try {
						// make the manager and wire it up
						DataViewerController dataViewerController = documentWindowManager.getRequestManager().getDataViewerController(sim, 0);
						documentWindowManager.addDataListener(dataViewerController);//For changes in time or variable
						// make the viewer
						dataViewerControllers.put(vcSimulationIdentifier, dataViewerController);
					} catch (Throwable exc) {
						exc.printStackTrace(System.out);
						failures.put(sim, exc);
					}
				}
			};
			taskList.add(task);
		}			
	}	
		
	AsynchClientTask task = new AsynchClientTask("Showing results", AsynchClientTask.TASKTYPE_SWING_BLOCKING) {
		public void run(Hashtable<String, Object> hashTable) throws Exception {					
			Hashtable<VCSimulationIdentifier, DataViewerController> dataViewerControllers = (Hashtable<VCSimulationIdentifier, DataViewerController>)hashTable.get(dataViewerControllers_string);
			Hashtable<Simulation,Throwable> failures = (Hashtable<Simulation,Throwable>)hashTable.get(failures_string);
			for (int i = 0; i < simsToShow.length; i++){
				final Simulation sim  = simsToShow[i];
				final VCSimulationIdentifier vcSimulationIdentifier = simsToShow[i].getSimulationInfo().getAuthoritativeVCSimulationIdentifier();				
				final SimulationWindow simWindow = documentWindowManager.haveSimulationWindow(vcSimulationIdentifier);
				if (simWindow != null) {
					JInternalFrame existingFrame = simWindow.getFrame();
					documentWindowManager.showFrame(existingFrame);
				} else {					
					// wire it up the viewer
					DataViewerController viewerController = dataViewerControllers.get(vcSimulationIdentifier);
					Throwable ex = failures.get(sim); 
					if (viewerController != null && ex == null) { // no failure
						DataViewer viewer = viewerController.createViewer();
						documentWindowManager.addExportListener(viewer);
						documentWindowManager.addDataJobListener(viewer);//For data related activities such as calculating statistics
						
						viewer.setSimulationModelInfo(new SimulationWorkspaceModelInfo(getSimWorkspace().getSimulationOwner(),sim.getName()));
						viewer.setDataViewerManager(documentWindowManager);
						SimulationWindow newWindow = new SimulationWindow(vcSimulationIdentifier, sim, getSimWorkspace().getSimulationOwner(), viewer);						
						documentWindowManager.addResultsFrame(newWindow);
					}
				}
			}
			if (failures != null) {
				if (!failures.isEmpty()) {
					Enumeration<Simulation> en = failures.keys();
					while (en.hasMoreElements()) {
						Simulation sim = en.nextElement();
						Throwable exc = (Throwable)failures.get(sim);
						// notify user
						PopupGenerator.showErrorDialog(ClientSimManager.this.getDocumentWindowManager(), "Failed to retrieve results for simulation '"+sim.getName()+"':\n"+exc.getMessage());
					}
				}
			}
		}			
	};
	taskList.add(task);

	// Dispatch the tasks using the ClientTaskDispatcher.		
	AsynchClientTask[] taskArray = new AsynchClientTask[taskList.size()];
	taskList.toArray(taskArray);
	ClientTaskDispatcher.dispatch(getDocumentWindowManager().getComponent(), hash, taskArray, false, true, null);
}


/**
 * Insert the method's description here.
 * Creation date: (6/7/2004 10:31:36 AM)
 * @param simulations cbit.vcell.solver.Simulation[]
 */
public void showSimulationStatusDetails(Simulation[] simulations) {
	if (simulations != null) {
		final Simulation[] simsToShow = simulations; //(Simulation[])cbit.util.BeanUtils.getArray(v, Simulation.class);
		for (int i = 0; i < simsToShow.length; i ++) {
			SimulationStatusDetailsPanel ssdp = new SimulationStatusDetailsPanel();
			ssdp.setPreferredSize(new Dimension(800, 350));
			ssdp.setSimulationStatusDetails(new SimulationStatusDetails(getSimWorkspace(), simsToShow[i]));
			DialogUtils.showComponentCloseDialog(getDocumentWindowManager().getComponent(), ssdp, "Simulation Status Details");			
			ssdp.setSimulationStatusDetails(null);
		}
	}
}


/**
 * Insert the method's description here.
 * Creation date: (6/2/2004 3:01:29 AM)
 * @param simulations cbit.vcell.solver.Simulation[]
 */
public void stopSimulations(Simulation[] simulations) {
	getDocumentWindowManager().getRequestManager().stopSimulations(this, simulations);
}


/**
 * Insert the method's description here.
 * Creation date: (6/9/2004 3:04:12 PM)
 */
void updateStatusFromServer(Simulation simulation) {
	// 
	// get cached status
	//
	SimulationStatus oldStatus = getSimulationStatus(simulation);
	SimulationStatus serverStatus = getDocumentWindowManager().getRequestManager().getServerSimulationStatus(simulation.getSimulationInfo());

	SimulationStatus newStatus = null;
	if (oldStatus.isStopRequested() && serverStatus.numberOfJobsDone() < simulation.getScanCount()) {
		// if stop requested but still going, get updated server info but adjust status
		newStatus = SimulationStatus.newStopRequest(serverStatus);
	} else {
		// otherwise accept server information
		newStatus = serverStatus;
	}

	// update cache
	simHash.setSimulationStatus(simulation,newStatus);
	
	System.out.println("---ClientSimManager.updateStatusFromServer[newStatus=" + newStatus + "], simulation="+simulation.toString());
	if (oldStatus!=newStatus){
		int simIndex = getSimWorkspace().getSimulationIndex(simulation);
		getSimWorkspace().firePropertyChange(new PropertyChangeEvent(getSimWorkspace(), "status", new Integer(-1), new Integer(simIndex)));
	}
}


/**
 * Insert the method's description here.
 * Creation date: (6/2/2004 3:01:29 AM)
 * @param simulations cbit.vcell.solver.Simulation[]
 */
public void updateStatusFromStartRequest(final Simulation simulation, boolean failed, String failureMessage) {
	// asynchronous call - from start request worker thread
	SimulationStatus newStatus = failed ? SimulationStatus.newStartRequestFailure(failureMessage, simulation.getScanCount()) : SimulationStatus.newStartRequest(simulation.getScanCount());
	simHash.setSimulationStatus(simulation,newStatus);
	int simIndex = getSimWorkspace().getSimulationIndex(simulation);
	getSimWorkspace().firePropertyChange(new PropertyChangeEvent(getSimWorkspace(), "status", new Integer(-1), new Integer(simIndex)));
}


/**
 * Insert the method's description here.
 * Creation date: (6/2/2004 3:01:29 AM)
 * @param simulations cbit.vcell.solver.Simulation[]
 */
public void updateStatusFromStopRequest(final Simulation simulation) {
	// asynchronous call - from stop request worker thread
	SimulationStatus currentStatus = getSimulationStatus(simulation);
	SimulationStatus newStatus = SimulationStatus.newStopRequest(currentStatus);
	simHash.setSimulationStatus(simulation,newStatus);
	int simIndex = getSimWorkspace().getSimulationIndex(simulation);
	getSimWorkspace().firePropertyChange(new PropertyChangeEvent(getSimWorkspace(), "status", new Integer(-1), new Integer(simIndex)));
}
}