package cbit.vcell.messaging.server;
import cbit.rmi.event.SimulationJobStatus;
import cbit.util.DataAccessException;
import cbit.util.document.KeyValue;
import cbit.vcell.messaging.db.UpdateSynchronizationException;
import cbit.vcell.modeldb.AdminDatabaseServer;
import cbit.vcell.simulation.VCSimulationIdentifier;

/**
 * Insert the type's description here.
 * Creation date: (2/20/2004 3:38:59 PM)
 * @author: Fei Gao
 */
public interface DispatcherDbManager {
	SimulationJobStatus getSimulationJobStatus(AdminDatabaseServer adminDb, KeyValue simKey, int jobIndex) throws DataAccessException;


	SimulationJobStatus updateDispatchedStatus(SimulationJobStatus oldJobStatus, AdminDatabaseServer adminDb, String computeHost, VCSimulationIdentifier vcSimID, int jobIndex, String startMsg) throws DataAccessException, UpdateSynchronizationException;


SimulationJobStatus updateEndStatus(SimulationJobStatus oldJobStatus, AdminDatabaseServer adminDb, VCSimulationIdentifier vcSimID, int jobIndex, String hostName, int status, String solverMsg) throws DataAccessException, UpdateSynchronizationException;


	void updateLatestUpdateDate(SimulationJobStatus oldJobStatus, AdminDatabaseServer adminDb, VCSimulationIdentifier vcSimID, int jobIndex) throws DataAccessException, UpdateSynchronizationException;


	SimulationJobStatus updateRunningStatus(SimulationJobStatus oldJobStatus, AdminDatabaseServer adminDb, String hostName, VCSimulationIdentifier vcSimID, int jobIndex, boolean hasData, String solverMsg)	throws DataAccessException, UpdateSynchronizationException;
}