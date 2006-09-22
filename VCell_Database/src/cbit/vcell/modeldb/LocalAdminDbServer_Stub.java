// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package cbit.vcell.modeldb;

public final class LocalAdminDbServer_Stub
    extends java.rmi.server.RemoteStub
    implements cbit.vcell.server.AdminDatabaseServer, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_getSimulationJobStatus_0;
    private static java.lang.reflect.Method $method_getSimulationJobStatus_1;
    private static java.lang.reflect.Method $method_getSimulationJobStatus_2;
    private static java.lang.reflect.Method $method_getUser_3;
    private static java.lang.reflect.Method $method_getUser_4;
    private static java.lang.reflect.Method $method_getUserFromSimulationKey_5;
    private static java.lang.reflect.Method $method_getUserInfo_6;
    private static java.lang.reflect.Method $method_getUserInfos_7;
    private static java.lang.reflect.Method $method_insertSimulationJobStatus_8;
    private static java.lang.reflect.Method $method_insertUserInfo_9;
    private static java.lang.reflect.Method $method_updateSimulationJobStatus_10;
    private static java.lang.reflect.Method $method_updateUserInfo_11;
    
    static {
	try {
	    $method_getSimulationJobStatus_0 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getSimulationJobStatus", new java.lang.Class[] {cbit.util.KeyValue.class, int.class});
	    $method_getSimulationJobStatus_1 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getSimulationJobStatus", new java.lang.Class[] {java.lang.String.class});
	    $method_getSimulationJobStatus_2 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getSimulationJobStatus", new java.lang.Class[] {boolean.class, cbit.util.User.class});
	    $method_getUser_3 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getUser", new java.lang.Class[] {java.lang.String.class});
	    $method_getUser_4 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getUser", new java.lang.Class[] {java.lang.String.class, java.lang.String.class});
	    $method_getUserFromSimulationKey_5 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getUserFromSimulationKey", new java.lang.Class[] {cbit.util.KeyValue.class});
	    $method_getUserInfo_6 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getUserInfo", new java.lang.Class[] {cbit.util.KeyValue.class});
	    $method_getUserInfos_7 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("getUserInfos", new java.lang.Class[] {});
	    $method_insertSimulationJobStatus_8 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("insertSimulationJobStatus", new java.lang.Class[] {cbit.vcell.solvers.SimulationJobStatus.class});
	    $method_insertUserInfo_9 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("insertUserInfo", new java.lang.Class[] {cbit.util.UserInfo.class});
	    $method_updateSimulationJobStatus_10 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("updateSimulationJobStatus", new java.lang.Class[] {cbit.vcell.solvers.SimulationJobStatus.class, cbit.vcell.solvers.SimulationJobStatus.class});
	    $method_updateUserInfo_11 = cbit.vcell.server.AdminDatabaseServer.class.getMethod("updateUserInfo", new java.lang.Class[] {cbit.util.UserInfo.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public LocalAdminDbServer_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of getSimulationJobStatus(KeyValue, int)
    public cbit.vcell.solvers.SimulationJobStatus getSimulationJobStatus(cbit.util.KeyValue $param_KeyValue_1, int $param_int_2)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getSimulationJobStatus_0, new java.lang.Object[] {$param_KeyValue_1, new java.lang.Integer($param_int_2)}, 3258198114365414872L);
	    return ((cbit.vcell.solvers.SimulationJobStatus) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getSimulationJobStatus(String)
    public java.util.List getSimulationJobStatus(java.lang.String $param_String_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getSimulationJobStatus_1, new java.lang.Object[] {$param_String_1}, -3992308848836386568L);
	    return ((java.util.List) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getSimulationJobStatus(boolean, User)
    public cbit.vcell.solvers.SimulationJobStatus[] getSimulationJobStatus(boolean $param_boolean_1, cbit.util.User $param_User_2)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getSimulationJobStatus_2, new java.lang.Object[] {new java.lang.Boolean($param_boolean_1), $param_User_2}, 1316770442574823816L);
	    return ((cbit.vcell.solvers.SimulationJobStatus[]) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUser(String)
    public cbit.util.User getUser(java.lang.String $param_String_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUser_3, new java.lang.Object[] {$param_String_1}, -5876487373133529709L);
	    return ((cbit.util.User) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUser(String, String)
    public cbit.util.User getUser(java.lang.String $param_String_1, java.lang.String $param_String_2)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUser_4, new java.lang.Object[] {$param_String_1, $param_String_2}, -6257811118775427044L);
	    return ((cbit.util.User) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUserFromSimulationKey(KeyValue)
    public cbit.util.User getUserFromSimulationKey(cbit.util.KeyValue $param_KeyValue_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUserFromSimulationKey_5, new java.lang.Object[] {$param_KeyValue_1}, -9117084100856111954L);
	    return ((cbit.util.User) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUserInfo(KeyValue)
    public cbit.util.UserInfo getUserInfo(cbit.util.KeyValue $param_KeyValue_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUserInfo_6, new java.lang.Object[] {$param_KeyValue_1}, 5888400704317291773L);
	    return ((cbit.util.UserInfo) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUserInfos()
    public cbit.util.UserInfo[] getUserInfos()
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUserInfos_7, null, 4275675527704489443L);
	    return ((cbit.util.UserInfo[]) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of insertSimulationJobStatus(SimulationJobStatus)
    public cbit.vcell.solvers.SimulationJobStatus insertSimulationJobStatus(cbit.vcell.solvers.SimulationJobStatus $param_SimulationJobStatus_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_insertSimulationJobStatus_8, new java.lang.Object[] {$param_SimulationJobStatus_1}, 6612250165645515978L);
	    return ((cbit.vcell.solvers.SimulationJobStatus) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of insertUserInfo(UserInfo)
    public cbit.util.UserInfo insertUserInfo(cbit.util.UserInfo $param_UserInfo_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_insertUserInfo_9, new java.lang.Object[] {$param_UserInfo_1}, 9077131507998373534L);
	    return ((cbit.util.UserInfo) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of updateSimulationJobStatus(SimulationJobStatus, SimulationJobStatus)
    public cbit.vcell.solvers.SimulationJobStatus updateSimulationJobStatus(cbit.vcell.solvers.SimulationJobStatus $param_SimulationJobStatus_1, cbit.vcell.solvers.SimulationJobStatus $param_SimulationJobStatus_2)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_updateSimulationJobStatus_10, new java.lang.Object[] {$param_SimulationJobStatus_1, $param_SimulationJobStatus_2}, 5376187929673614080L);
	    return ((cbit.vcell.solvers.SimulationJobStatus) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of updateUserInfo(UserInfo)
    public cbit.util.UserInfo updateUserInfo(cbit.util.UserInfo $param_UserInfo_1)
	throws cbit.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_updateUserInfo_11, new java.lang.Object[] {$param_UserInfo_1}, 1504983660871353769L);
	    return ((cbit.util.UserInfo) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (cbit.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
