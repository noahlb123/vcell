package cbit.vcell.field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import cbit.sql.KeyValue;
import cbit.vcell.simdata.ExternalDataIdentifier;

public class FieldDataDBOperationResults implements Serializable {
	
	public ExternalDataIdentifier[] extDataIDArr;
	public String[] extDataAnnotArr;
	public ExternalDataIdentifier extDataID;
	public Hashtable<String,ExternalDataIdentifier> oldNameNewIDHash;
	public Hashtable<String,KeyValue> oldNameOldExtDataIDKeyHash;
	public HashMap<ExternalDataIdentifier, Vector<KeyValue>> extdataIDAndSimRefH;
}
