package org.vcell.util.document;

import java.util.Vector;

/**
 * Insert the type's description here.
 * Creation date: (8/20/2004 2:11:48 PM)
 * @author: Jim Schaff
 */
public class MathModelChildSummary implements java.io.Serializable {
	private String geoName = null;
	private int geoDim = 0;
	
	private String simNames[] = null;
	private String simAnnots[] = null;
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:13:07 PM)
 */
private MathModelChildSummary() {}

public MathModelChildSummary(String arg_geoName, int arg_geoDim, String[] arg_simNames, String[] arg_simAnnots){
	this.geoName = arg_geoName;
	this.geoDim = arg_geoDim;
	this.simNames = arg_simNames;
	this.simAnnots = arg_simAnnots;
}
/**
 * Insert the method's description here.
 * Creation date: (8/24/2004 3:01:10 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */
private String emptyConvention(String str) {
	
	if(str != null && str.length() > 0){
		return str;
	}
	return " ";
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 1:30:48 PM)
 * @return cbit.vcell.biomodel.BioModelChildSummary
 * @param databaseSerialization java.lang.String
 */
public static MathModelChildSummary fromDatabaseSerialization(String databaseSerialization) {
	
	MathModelChildSummary mmcs = new MathModelChildSummary();
	//Assumes there is a non-empty string for every element
	java.util.StringTokenizer st = new java.util.StringTokenizer(databaseSerialization,"\n",false);

	mmcs.geoName = (String)org.vcell.util.TokenMangler.getChildSummaryElementRestoredString((String)st.nextElement());
	mmcs.geoDim = Integer.parseInt((String)st.nextElement());
	
	Vector<String> simNamesV = new Vector<String>();
	Vector<String> simAnnotsV = new Vector<String>();
	int numSims = Integer.parseInt((String)st.nextElement());

	while(st.hasMoreElements()){
		for(int j=0;j<numSims;j+= 1){
			simNamesV.add(org.vcell.util.TokenMangler.getChildSummaryElementRestoredString((String)st.nextElement()));
			simAnnotsV.add(org.vcell.util.TokenMangler.getChildSummaryElementRestoredString((String)st.nextElement()));
		}
	}

	mmcs.simNames = (String[])simNamesV.toArray(new String[simNamesV.size()]);
	mmcs.simAnnots = (String[])simAnnotsV.toArray(new String[simAnnotsV.size()]);
	
	return mmcs;
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 9:52:25 PM)
 * @return cbit.vcell.biomodel.BioModelChildSummary
 */
public static MathModelChildSummary getExample() {
	MathModelChildSummary mmChildSummary = new MathModelChildSummary();
	mmChildSummary.geoName = "geo1";
	mmChildSummary.geoDim = 0;
	mmChildSummary.simNames = new String[]{ "sim11",	"sim12",	"sim13" };
	mmChildSummary.simAnnots = new String[]{ null,		null,		null };
	return mmChildSummary;
}
/**
 * Insert the method's description here.
 * Creation date: (8/20/2004 2:23:12 PM)
 * @return int[]
 */
public int getGeometryDimension() {
	return geoDim;
}
/**
 * Insert the method's description here.
 * Creation date: (8/20/2004 2:23:12 PM)
 * @return int[]
 */
public String getGeometryName() {
	return geoName;
}
/**
 * Insert the method's description here.
 * Creation date: (8/20/2004 2:18:34 PM)
 * @return java.lang.String[]
 */
public String[] getSimulationAnnotations() {
	return simAnnots;
}
/**
 * Insert the method's description here.
 * Creation date: (8/20/2004 2:18:34 PM)
 * @return java.lang.String[]
 */
public String[] getSimulationNames() {
	return simNames;
}
/**
 * Insert the method's description here.
 * Creation date: (8/20/2004 2:17:14 PM)
 * @return java.lang.String
 */
public String toDatabaseSerialization() {
	
	StringBuffer sb = new StringBuffer();
	
	sb.append(emptyConvention(org.vcell.util.TokenMangler.getChildSummaryElementEscapedString(geoName))+"\n");
	sb.append(geoDim+"\n");
	
	//Simulations
	sb.append(simNames.length+"\n");//num simulations
	for(int j=0;j<simNames.length;j+= 1){
		sb.append(emptyConvention(org.vcell.util.TokenMangler.getChildSummaryElementEscapedString(simNames[j]))+"\n");
		sb.append(emptyConvention(org.vcell.util.TokenMangler.getChildSummaryElementEscapedString(simAnnots[j]))+"\n");
	}
	
	return sb.toString();
}
}
