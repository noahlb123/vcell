package cbit.vcell.modeldb;

/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import cbit.sql.Field;
import cbit.sql.Table;
import cbit.util.DataAccessException;
import cbit.util.KeyValue;
import cbit.util.SessionLog;
import cbit.util.User;
import cbit.vcell.dictionary.DBFormalSpecies;
import cbit.vcell.dictionary.DBNonFormalUnboundSpecies;
import cbit.vcell.dictionary.ReactionDescription;
import cbit.vcell.dictionary.database.DBSpeciesTable;
import cbit.vcell.model.FluxReaction;
import cbit.vcell.model.Kinetics;
import cbit.vcell.model.KineticsDescription;
import cbit.vcell.model.Membrane;
import cbit.vcell.model.ReactionStep;
import cbit.vcell.model.SimpleReaction;
import cbit.vcell.model.Structure;
import cbit.vcell.model.VCMODL;
/**
 * This type was created in VisualAge.
 */
public class ReactStepTable extends cbit.sql.Table {
	private static final String TABLE_NAME = "vc_reactstep";
	public static final String REF_TYPE = "REFERENCES " + TABLE_NAME + "(" + Table.id_ColumnName + ")";

	public final Field reactType			= new Field("reactType",	"varchar(10)",	"NOT NULL");
	public final Field modelRef 			= new Field("modelRef",		"integer",		"NOT NULL "+ModelTable.REF_TYPE+" ON DELETE CASCADE");
	public final Field structRef			= new Field("structRef",	"integer",		"NOT NULL "+StructTable.REF_TYPE);
	//public final Field kinetics 			= new Field("kinetics",		"long raw",		"NOT NULL");
	public final Field kinetics 			= new Field("kinetics",		"CLOB",		"");
	public final Field name	 				= new Field("name",			"varchar(30)",	"");
	public final Field chargeValence		= new Field("chargeValence","integer",		"");
	public final Field physicsOptions		= new Field("physicsOptions","integer",		"");
	public final Field kineticsLarge		= new Field("kineticsLRG",	"CLOB",				"");
	public final Field kineticsSmall		= new Field("kineticsSML",	"VARCHAR2(4000)",	"");

	private final Field fields[] = {reactType,modelRef,structRef,kinetics,name,chargeValence,physicsOptions,kineticsLarge,kineticsSmall};
	
	public static final ReactStepTable table = new ReactStepTable();

	private static final String REACTTYPE_FLUX = DatabaseConstants.REACTTYPE_FLUX;
	private static final String REACTTYPE_SIMPLE = DatabaseConstants.REACTTYPE_SIMPLE;
	//
	static final int RXIDDN_BIOMODEL_NAME_INDEX = 3;
	static final int RXIDDN_REACTSTEP_NAME_INDEX = 4;
	
	//private static final int MODEL_NAME_INDEX = 2;
	//private static final int REACTSTEP_NAME_INDEX = 3;
	//private static final int REACTSTEP_ID_INDEX = 4;
	//public static final int SPECIES_ID_INDEX = 8;
	//public static final int DBSPECIES_ID_INDEX = 9;
	//public static final int PERMISSION_ID_INDEX = 20;
	//public static final int SPECIES_CONTEXT_INDEX = 22;
	//public static final int MODEL_ID_INDEX = 23;
/**
 * ModelTable constructor comment.
 */
private ReactStepTable() {
	super(TABLE_NAME);
	addFields(fields);
}
/**
 * This method was created in VisualAge.
 * @return cbit.vcell.model.ReactionParticipant
 * @param rset java.sql.ResultSet
 */
public ReactionStep getReactionStep(Structure structure, KeyValue rsKey, java.sql.ResultSet rset, SessionLog log) throws java.sql.SQLException, DataAccessException {
	
	KeyValue key = rsKey;
	if (rset.wasNull()){
		key = null;
	}
	String reactType = rset.getString(ReactStepTable.table.reactType.toString());

	String reactionStepName = null;
	String nameString = rset.getString(ReactStepTable.table.name.toString());
	if (rset.wasNull()){
		nameString = null;
	}
	if (nameString!=null){
		reactionStepName = nameString;
	}

	ReactionStep rs = null;
	try {
		if (reactType.equals(ReactStepTable.REACTTYPE_FLUX)){
			rs = new FluxReaction((Membrane)structure,key,reactionStepName);
		}else if (reactType.equals(ReactStepTable.REACTTYPE_SIMPLE)){
			rs = new SimpleReaction(structure,key,reactionStepName);
		}
	}catch (java.beans.PropertyVetoException e){
		e.printStackTrace(System.out);
		throw new DataAccessException(e.getMessage());
	}


	//
	// if valence is stored as a 'null', it was an error (previous models were updated administratively).
	//
	int valenceValue = rset.getInt(ReactStepTable.table.chargeValence.toString());
	if (rset.wasNull()){
		throw new DataAccessException("unexpected null for chargeValence");
	}
	try {
		rs.getChargeCarrierValence().setExpression(new cbit.vcell.parser.Expression(valenceValue));
	}catch (java.beans.PropertyVetoException e){
		e.printStackTrace(System.out);
	}

	//
	// New procedure for getting kinetics
	//
	String kinetics_vcml =
		DbDriver.varchar2_CLOB_get(rset,ReactStepTable.table.kineticsSmall,ReactStepTable.table.kineticsLarge);
	if(kinetics_vcml == null || kinetics_vcml.length() == 0){
		throw new DataAccessException("no data stored for kinetics");
	}
	//This isn't needed?
	//if (kinetics_vcml.endsWith(";}\n")){
		//StringBuffer buffer = new StringBuffer(kinetics_vcml.substring(0,kinetics_vcml.length()-2));
		//buffer.append("\n}\n");
		//kinetics_vcml = buffer.toString();
	//}
	cbit.util.CommentStringTokenizer tokens = new cbit.util.CommentStringTokenizer(kinetics_vcml);

	
	
	
	
	Kinetics kinetics = null;
	try {
		String token = tokens.nextToken();
		if (!token.equalsIgnoreCase(VCMODL.Kinetics)){
			throw new DataAccessException("expected "+VCMODL.Kinetics);
		}
		token = tokens.nextToken();
		KineticsDescription kineticsDescription = KineticsDescription.fromVCMLKineticsName(token);
		if (kineticsDescription!=null){
			kinetics = kineticsDescription.createKinetics(rs);
		}else{
			throw new DataAccessException("expected valid kinetics type, read '"+token+"'");
		}		
		kinetics.fromTokens(tokens);
//
// for debug purposes only, remove when unresolvedParameters are ok ... when globals exist
//
if (kinetics.getUnresolvedParameters().length!=0){
	System.out.println("<<<WARNING>>> ReactStepTable.getReactionStep(key="+rsKey+") has "+kinetics.getUnresolvedParameters().length+" UnresolvedParameters");
	for (int i = 0; i < kinetics.getUnresolvedParameters().length; i++){
		System.out.println(">>>>>>>>>>>>> UnresolvedParameter["+i+"] = "+kinetics.getUnresolvedParameters()[i].toString());
	}
}
	}catch (Exception e){
		log.exception(e);
		throw new DataAccessException(e.getMessage());
	}
	rs.setKinetics(kinetics);
	
	//
	// if physicsOptions is stored as a 'null', it was an error (previous models were updated administratively).
	//
	int physicsOptionsValue = rset.getInt(ReactStepTable.table.physicsOptions.toString());
	if (rset.wasNull()){
		throw new DataAccessException("unexpected null for physicsOptions");
	}
	try {
		rs.setPhysicsOptions(physicsOptionsValue);
	}catch (java.beans.PropertyVetoException e){
		e.printStackTrace(System.out);
	}

	return rs;
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param reactionStep cbit.vcell.model.ReactionStep
 */
private String getReactType(ReactionStep reactionStep) throws DataAccessException {
	String reactionType = null;
	if (reactionStep instanceof SimpleReaction){
		reactionType = REACTTYPE_SIMPLE;
	}else if (reactionStep instanceof FluxReaction){
		reactionType = REACTTYPE_FLUX;
	}else if (reactionStep == null){
		throw new IllegalArgumentException("reactionStep is null");
	}else{
		throw new DataAccessException("unsupported type "+reactionStep.getClass().toString());
	}
	return reactionType;
}
/**
 * Insert the method's description here.
 * Creation date: (7/12/2003 2:59:27 PM)
 * @return java.lang.String
 * @param likeString java.lang.String
 */
public String getSQLReactionStepInfosQuery(KeyValue[] rxIDs,User user) {
	//Get Descriptive name for rxID
	//watch for multiple copies of reactionsteps being return because of
	//DatabasePolicy.  Happens because PermissionTable can have more than 1
	//parent allowing access
	String sql = null;
	Field[] f =
	{
		UserTable.table.userid,				//1
		BioModelTable.table.ownerRef,       //2
		BioModelTable.table.name,			//3
		ReactStepTable.table.name,			//4
		ReactStepTable.table.id,			//5
		BioModelTable.table.versionDate		//6
	};
	Table[] t =
	{
		ReactStepTable.table,
		BioModelTable.table,
		UserTable.table
	};
	//
	StringBuffer sbForId = new StringBuffer("(" + ReactStepTable.table.id.getQualifiedColName() + " IN (");
	for(int i=0;i < rxIDs.length;i+= 1){
		if(i > 0 ){
			sbForId.append(",");
		}
		sbForId.append(rxIDs[i].toString());
	}
	sbForId.append("))");
	//
	String condition =
		sbForId.toString() +
		" AND " +
		//ProteinTable.table.id.getQualifiedColName() + " (+)= " + DBSpeciesTable.table.proteinRef +
		//" AND " +
		//EnzymeTable.table.id.getQualifiedColName() + " (+)= " + DBSpeciesTable.table.enzymeRef +
		//" AND " +
		//CompoundTable.table.id.getQualifiedColName() + " (+)= " + DBSpeciesTable.table.compoundRef +
		//" AND " +
		//DBSpeciesTable.table.id.getQualifiedColName() + " (+)= " + SpeciesTable.table.dbSpeciesRef +
		//" AND " +
		//SpeciesTable.table.id.getQualifiedColName() + " = " + SpeciesContextModelTable.table.speciesRef.getQualifiedColName() +
		//" AND " +
		//SpeciesContextModelTable.table.id.getQualifiedColName() + " = " + ReactPartTable.table.scRef.getQualifiedColName() +
		//" AND " +
		//ReactPartTable.table.reactStepRef.getQualifiedColName() + " = " + ReactStepTable.table.id.getQualifiedColName() +
		//" AND " +
		ReactStepTable.table.modelRef.getQualifiedColName() + " = " + BioModelTable.table.modelRef.getQualifiedColName() +
		" AND " +
		BioModelTable.table.ownerRef.getQualifiedColName() + " = " + UserTable.table.id.getQualifiedColName();
	String special = " ORDER BY " + ReactStepTable.table.id.getQualifiedColName();
	sql = DatabasePolicySQL.enforceOwnershipSelect(user,f,t,condition,special,true);
	StringBuffer sb = new StringBuffer(sql);
	//LOBs cannot be accessed if the query uses the DISTINCT or UNIQUE keyword
	sb.insert(7," DISTINCT ");
	return sb.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (7/12/2003 2:59:27 PM)
 * @return java.lang.String
 * @param likeString java.lang.String
 */
public String getSQLUserReactionListQuery(ReactionQuerySpec rqs, User user) {
	
	String reactant_or_flux_likeString = rqs.getReactantLikeString();
	String catalyst_likeString = rqs.getCatalystLikeString();
	String product_likeString = rqs.getProductLikeString();
	DBFormalSpecies reactant_or_flux_dbspecies = rqs.getReactantBoundSpecies();
	DBFormalSpecies catalyst_dbspecies = rqs.getCatalystBoundSpecies();
	DBFormalSpecies product_dbspecies = rqs.getProductBoundSpecies();
	String repWildCard = rqs.getAnyReactionParticipantLikeString();
	DBFormalSpecies typeWildCard = rqs.getAnyReactionParticipantBoundSpecies();

	//Get list of distinct USER reactions that are visible to user
	
	//
	//Create comma-separated lists of dbspeciesid
	//
	StringBuffer reactant_flux_list = new StringBuffer();
	StringBuffer catalyst_list = new StringBuffer();
	StringBuffer product_list = new StringBuffer();
	if (reactant_or_flux_dbspecies != null){
		reactant_flux_list.append(reactant_or_flux_dbspecies.getDBFormalSpeciesKey().toString());
	}
	if (catalyst_dbspecies != null){
		catalyst_list.append(catalyst_dbspecies.getDBFormalSpeciesKey().toString());
	}
	if (product_dbspecies != null){
		product_list.append(product_dbspecies.getDBFormalSpeciesKey().toString());
	}
	//Creat conditions for flux,reaction,catalyst,product
	boolean[] bNeedsDBSpeciesTableArr = new boolean[3];
	StringBuffer[] subConditionsArr = new StringBuffer[3];
	for(int i=0;i< 3;i+= 1){
		boolean bHasLike = false;
		boolean bHasDBspid = false;
		StringBuffer subConditions = new StringBuffer();
		subConditionsArr[i] = subConditions;
		if(i == 0){
			bHasLike = (reactant_or_flux_likeString != null && reactant_or_flux_likeString.length() > 0);
			bHasDBspid = (reactant_or_flux_dbspecies != null);
			if(!bHasLike && !bHasDBspid){continue;}
			subConditions.append("(");
			subConditions.append("(vc_reactpart_sub.role = 'reactant' OR vc_reactpart_sub.role = 'flux') AND ");
			if(bHasLike){
				subConditions.append("upper(vc_species_sub.commonname) LIKE upper('"+reactant_or_flux_likeString+"')");
				if(bHasDBspid){subConditions.append(" OR ");}
			}
			if(bHasDBspid){
				bNeedsDBSpeciesTableArr[i] = true;
				//subConditions.append("vc_species_sub.dbspeciesref IN ("+reactant_flux_list.toString()+")");
				subConditions.append("vc_species_sub.dbspeciesref = vc_dbspecies_sub.id AND (");
				subConditions.append("vc_dbspecies_sub.compoundref IN ("+reactant_flux_list.toString()+") OR ");
				subConditions.append("vc_dbspecies_sub.enzymeref IN ("+reactant_flux_list.toString()+") OR ");
				subConditions.append("vc_dbspecies_sub.proteinref IN ("+reactant_flux_list.toString()+")");
				subConditions.append(")");
			}
			subConditions.append(")");
		}else if(i == 1){
			bHasLike = (catalyst_likeString != null && catalyst_likeString.length() > 0);
			bHasDBspid = (catalyst_dbspecies != null);
			if(!bHasLike && !bHasDBspid){continue;}
			if(subConditions.length() > 0){subConditions.append(" AND ");}
			subConditions.append("(");
			subConditions.append("vc_reactpart_sub.role = 'catalyst' AND ");
			if(bHasLike){
				subConditions.append("upper(vc_species_sub.commonname) LIKE upper('"+catalyst_likeString+"')");
				if(bHasDBspid){subConditions.append(" OR ");}
			}
			if(bHasDBspid){
				bNeedsDBSpeciesTableArr[i] = true;
				//subConditions.append("vc_species_sub.dbspeciesref IN ("+catalyst_list.toString()+")");
				subConditions.append("vc_species_sub.dbspeciesref = vc_dbspecies_sub.id AND (");
				subConditions.append("vc_dbspecies_sub.compoundref IN ("+catalyst_list.toString()+") OR ");
				subConditions.append("vc_dbspecies_sub.enzymeref IN ("+catalyst_list.toString()+") OR ");
				subConditions.append("vc_dbspecies_sub.proteinref IN ("+catalyst_list.toString()+")");
				subConditions.append(")");
			}
			subConditions.append(")");
		}else if(i == 2){
			bHasLike = (product_likeString != null && product_likeString.length() > 0);
			bHasDBspid = (product_dbspecies != null);
			if(!bHasLike && !bHasDBspid){continue;}
			if(subConditions.length() > 0){subConditions.append(" AND ");}
			subConditions.append("(");
			subConditions.append("vc_reactpart_sub.role = 'product' AND ");
			if(bHasLike){
				subConditions.append("upper(vc_species_sub.commonname) LIKE upper('"+product_likeString+"')");
				if(bHasDBspid){subConditions.append(" OR ");}
			}
			if(bHasDBspid){
				bNeedsDBSpeciesTableArr[i] = true;
				//subConditions.append("vc_species_sub.dbspeciesref IN ("+product_list.toString()+")");
				subConditions.append("vc_species_sub.dbspeciesref = vc_dbspecies_sub.id AND (");
				subConditions.append("vc_dbspecies_sub.compoundref IN ("+product_list.toString()+") OR ");
				subConditions.append("vc_dbspecies_sub.enzymeref IN ("+product_list.toString()+") OR ");
				subConditions.append("vc_dbspecies_sub.proteinref IN ("+product_list.toString()+")");
				subConditions.append(")");
			}
			subConditions.append(")");
		}
	}
	//
	String sql = null;
	Field[] f =
	{
		ReactStepTable.table.name,			//1
		ReactStepTable.table.id,			//2
		ReactStepTable.table.reactType,		//3
		ReactPartTable.table.role,			//4
		ReactPartTable.table.stoich,		//5
		SpeciesTable.table.commonName		//6
		//SpeciesTable.table.dbSpeciesRef		//7
	};
	Table[] t =
	{
		ReactStepTable.table,
		BioModelTable.table,
		ReactPartTable.table,
		SpeciesContextModelTable.table,
		SpeciesTable.table
	};
	//
	//
	//Non-WildCard subcondition
	boolean hadPreviousCondiiton = false;
	String searchConditions = "";
	for(int i = 0;i < 3;i+= 1){
		if(subConditionsArr[i].length() > 0){
			//
			if(hadPreviousCondiiton){
				searchConditions+= " INTERSECT ";
			}
			hadPreviousCondiiton = true;
			//
			searchConditions+=
			    "(" +
			    " SELECT "+Table.SQL_GLOBAL_HINT+" DISTINCT " +
			    " vc_reactstep_sub.id " +
			    " FROM " +
			    ReactStepTable.table.getTableName() + " vc_reactstep_sub" + "," +
			    ReactPartTable.table.getTableName() + " vc_reactpart_sub" + "," +
			    SpeciesContextModelTable.table.getTableName() + " vc_modelsc_sub" + "," +
			    SpeciesTable.table.getTableName() + " vc_species_sub" +
			    (bNeedsDBSpeciesTableArr[i] ? "," + DBSpeciesTable.table.getTableName() + " vc_dbspecies_sub":"") +
			    " WHERE " +
			    subConditionsArr[i].toString() +
			    " AND " +
			    " vc_species_sub.id=vc_modelsc_sub.speciesref AND " +
				" vc_modelsc_sub.modelref = " + BioModelTable.table.modelRef.getQualifiedColName() + " AND " +
			    " vc_modelsc_sub.id=vc_reactpart_sub.scref AND " +
			    " vc_reactpart_sub.reactstepref=vc_reactstep_sub.id " +
			    ")";
		}
	}
	//
	//
	//WildCard subcondtion
	if(repWildCard != null || typeWildCard != null){
		searchConditions =
		    "(" +
		    " SELECT "+Table.SQL_GLOBAL_HINT+" DISTINCT " +
		    " vc_reactstep_sub.id " +
		    " FROM " +
		    ReactStepTable.table.getTableName() + " vc_reactstep_sub" + "," +
		    ReactPartTable.table.getTableName() + " vc_reactpart_sub" + "," +
		    SpeciesContextModelTable.table.getTableName() + " vc_modelsc_sub" + "," +
		    SpeciesTable.table.getTableName() + " vc_species_sub" +
		    (typeWildCard != null ? "," + DBSpeciesTable.table.getTableName() + " vc_dbspecies_sub":"") +
		    " WHERE " +
		    "(" +
		    (repWildCard != null ? "upper(vc_species_sub.commonname) LIKE upper('"+repWildCard+"')":"") +
		    (repWildCard != null && typeWildCard != null?" OR  ":"") +
		    (typeWildCard != null?
			    "(" +
			    "vc_species_sub.dbspeciesref IS NOT NULL AND " +
			    "vc_species_sub.dbspeciesref = vc_dbspecies_sub.id AND " +
			    "(" +
			    "vc_dbspecies_sub.compoundref="+typeWildCard.getDBFormalSpeciesKey() + " OR " +
			    "vc_dbspecies_sub.enzymeref="+typeWildCard.getDBFormalSpeciesKey() + " OR " +
			    "vc_dbspecies_sub.proteinref="+typeWildCard.getDBFormalSpeciesKey() +
			    ")" +
			    ")"
			    :"") +
		    ")" +
		    " AND " +
		    " vc_species_sub.id=vc_modelsc_sub.speciesref AND " +
			" vc_modelsc_sub.modelref = " + BioModelTable.table.modelRef.getQualifiedColName() + " AND " +
		    " vc_modelsc_sub.id=vc_reactpart_sub.scref AND " +
		    " vc_reactpart_sub.reactstepref=vc_reactstep_sub.id " +
		    ")";			
	}
	//
	//
	//
	String condition = "";
	if(searchConditions.length() > 0){
		condition+= ReactStepTable.table.id.getQualifiedColName() + " IN " + "("+searchConditions+")"+ " AND ";
	}
	condition += 
		SpeciesTable.table.id.getQualifiedColName() + " = " + SpeciesContextModelTable.table.speciesRef.getQualifiedColName() +
		" AND " +
		SpeciesContextModelTable.table.id.getQualifiedColName() + " = " + ReactPartTable.table.scRef.getQualifiedColName() +
		" AND " +
		ReactPartTable.table.reactStepRef.getQualifiedColName() + " = " + ReactStepTable.table.id.getQualifiedColName() +
		" AND " +
		ReactStepTable.table.modelRef.getQualifiedColName() + " = " + BioModelTable.table.modelRef.getQualifiedColName();

	String special = " ORDER BY " + ReactStepTable.table.id.getQualifiedColName();
	sql = DatabasePolicySQL.enforceOwnershipSelect(user,f,t,condition,special,true);
	StringBuffer sb = new StringBuffer(sql);
	//LOBs cannot be accessed if the query uses the DISTINCT or UNIQUE keyword
	sb.insert(7,Table.SQL_GLOBAL_HINT+" DISTINCT ");
	return sb.toString();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param key KeyValue
 * @param modelName java.lang.String
 */
public String getSQLValueList(ReactionStep reactionStep, KeyValue modelKey, KeyValue structKey,KeyValue key) throws DataAccessException {

	String	kinetics = reactionStep.getKinetics().getVCML();

	StringBuffer buffer = new StringBuffer();
	buffer.append("(");
	buffer.append(key+",");
	buffer.append("'"+getReactType(reactionStep)+"',");
	buffer.append(modelKey+",");
	buffer.append(structKey+",");
	buffer.append("EMPTY_CLOB()"+","); // keep for compatibility with release site
	if (reactionStep.getName()==null){
		buffer.append("null"+",");
	}else{
		buffer.append("'"+reactionStep.getName()+"',");
	}
	try {
		buffer.append(((int)reactionStep.getChargeCarrierValence().getExpression().evaluateConstant())+",");
	}catch (cbit.vcell.parser.ExpressionException e){
		e.printStackTrace(System.out);
		throw new DataAccessException("failure extracting charge carrier valence from Reaction '"+reactionStep.getName()+"': "+e.getMessage());
	}
	buffer.append(reactionStep.getPhysicsOptions()+",");

	// New kinetics format
	if(DbDriver.varchar2_CLOB_is_Varchar2_OK(kinetics)){
		buffer.append("null"+","+DbDriver.INSERT_VARCHAR2_HERE);
	}else{
		buffer.append(DbDriver.INSERT_CLOB_HERE+","+"null");
	}

	buffer.append(")");
	
	return buffer.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (7/12/2003 2:59:27 PM)
 * @return java.lang.String
 * @param likeString java.lang.String
 */
public ReactionDescription[] getUserReactionList(java.sql.ResultSet rset)throws java.sql.SQLException {

	java.util.Vector resultV = new java.util.Vector();
	ReactionDescription[] result = null;
	java.util.Vector rxidV = null;
	//
	java.math.BigDecimal rxid = null;
	ReactionDescription dbfr = null;
	//
	while(rset.next()){
		java.math.BigDecimal currRXID = rset.getBigDecimal(ReactStepTable.table.id.toString());
		if(rset.isFirst()){
			rxid = currRXID;
		}
		if(rxid != null && !rxid.equals(currRXID)){
			rxid = currRXID;
			dbfr = null;
		}
		//
		if(dbfr == null){
			String rxType = rset.getString(ReactStepTable.table.reactType.toString());
			String rxName = rset.getString(ReactStepTable.table.name.toString());
			dbfr = new ReactionDescription(rxName,rxType,new KeyValue(rxid));
			resultV.add(dbfr);
		}
		String name = rset.getString(SpeciesTable.table.commonName.toString());
		DBNonFormalUnboundSpecies dbnfu = new DBNonFormalUnboundSpecies(name);
		//
		String partRole = rset.getString(ReactPartTable.table.role.toString());
		char partRoleType;
		if(partRole.equals("reactant")){
			partRoleType = ReactionDescription.RX_ELEMENT_REACTANT;
		}else if(partRole.equals("flux")){
			partRoleType = ReactionDescription.RX_ELEMENT_FLUX;
		}else if(partRole.equals("catalyst")){
			partRoleType = ReactionDescription.RX_ELEMENT_CATALYST;
		}else if(partRole.equals("product")){
			partRoleType = ReactionDescription.RX_ELEMENT_PRODUCT;
		}else{
			throw new RuntimeException("Unknown role in UserList Query results");
		}
		//
		int stoich = rset.getInt(ReactPartTable.table.stoich.toString());
		dbfr.addReactionElement(dbnfu,stoich,partRoleType);
		if(partRole.equals("flux")){
			dbfr.addReactionElement(new DBNonFormalUnboundSpecies(name),stoich,partRoleType);
		}
	}
	//
	if(resultV.size() > 0){
		result = new ReactionDescription[resultV.size()];
		resultV.copyInto(result);
	}
	return result;
}
}
