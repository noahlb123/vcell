package cbit.vcell.modeldb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.vcell.util.DataAccessException;
import org.vcell.util.TokenMangler;
import org.vcell.util.document.ExternalDataIdentifier;
import org.vcell.util.document.KeyValue;
import org.vcell.util.document.User;

import cbit.sql.Field;
import cbit.sql.Table;
import cbit.vcell.data.DataContext;
import cbit.vcell.data.DataSymbol;
import cbit.vcell.data.FieldDataSymbol;
import cbit.vcell.data.DataSymbol.DataSymbolType;
import cbit.vcell.units.VCUnitDefinition;

public class DataSymbolTable extends Table {
	private static final String TABLE_NAME = "vc_datasymbol";
	public static final String REF_TYPE = "REFERENCES " + TABLE_NAME + "(" + Table.id_ColumnName + ")";

	public final Field dataSymbolName		= new Field("dataSymbolName",	"varchar2(255)",	"NOT NULL");
	public final Field dataSymbolType		= new Field("dataSymbolType",	"varchar2(32)",	"NOT NULL");
	public final Field dataSymbolVCUnitDef	= new Field("dataSymbolVCUnitDef",	"varchar2(64)",	"NOT NULL");
	public final Field simContextRef		= new Field("simContextRef",	"integer",		"NOT NULL "+SimContextTable.REF_TYPE+" ON DELETE CASCADE");
	public final Field fieldDataRef			= new Field("fieldDataRef",		"integer",		ExternalDataTable.REF_TYPE);
	public final Field fieldDataVarName		= new Field("fieldDataVarName",	"varchar2(255)",	"");
	public final Field fieldDataVarType		= new Field("fieldDataVarType",	"varchar2(255)",	"");
	public final Field fieldDataVarTime		= new Field("fieldDataVarTime",	"number",	"");
	
	private final Field fields[] = {dataSymbolName,dataSymbolType,dataSymbolVCUnitDef,simContextRef,fieldDataRef,fieldDataVarName,fieldDataVarType,fieldDataVarTime};
	
	public static final DataSymbolTable table = new DataSymbolTable();
/**
 * ModelTable constructor comment.
 */
private DataSymbolTable() {
	super(TABLE_NAME);
	addFields(fields);
}

public String getSQLValueList(DataSymbol dataSymbol,KeyValue simContextKey) throws DataAccessException {

	FieldDataSymbol fieldDataSymbol = null;
	if(dataSymbol instanceof FieldDataSymbol){
		fieldDataSymbol = (FieldDataSymbol)dataSymbol;
	}
	StringBuffer buffer = new StringBuffer();
	buffer.append("(");
	buffer.append(Table.NewSEQ + ",");
	buffer.append("'" + dataSymbol.getName() + "',");
	buffer.append("'" + dataSymbol.getDataSymbolType().toString() + "',");
	buffer.append("'" + TokenMangler.getSQLEscapedString(dataSymbol.getUnitDefinition().getSymbol()) + "',");
	buffer.append(simContextKey.toString() + ",");
	buffer.append((fieldDataSymbol != null?fieldDataSymbol.getExternalDataIdentifier().getKey().toString():"null")+",");
	buffer.append((fieldDataSymbol != null?"'" + fieldDataSymbol.getFieldDataVarName() + "'":"null")+",");
	buffer.append((fieldDataSymbol != null?"'" + fieldDataSymbol.getFieldDataVarType() + "'":"null")+",");
	buffer.append((fieldDataSymbol != null?fieldDataSymbol.getFieldDataVarTime():"null"));

	buffer.append(")");
	
	return buffer.toString();
}
public void saveDataSymbols(Connection con,KeyValue simContextRef,DataContext dataContext,User simcontextOwner) throws SQLException,DataAccessException{
	DataSymbol[] dataSymbols = dataContext.getDataSymbols();
	if(dataSymbols == null){
		return;
	}
	for (int i = 0; i < dataSymbols.length; i++) {
		if(dataSymbols[i] instanceof FieldDataSymbol &&
			!((FieldDataSymbol)dataSymbols[i]).getExternalDataIdentifier().getOwner().compareEqual(simcontextOwner)){
			throw new DataAccessException("SimContext not own datasymbol");
		}
		String sql = 
			"INSERT INTO "+DataSymbolTable.table.getTableName()+
			DataSymbolTable.table.getSQLColumnList()+
			" VALUES "+
			getSQLValueList(dataSymbols[i], simContextRef);
		
		DbDriver.updateCleanSQL(con, sql);
	}
}
public void populateDataSymbols(Connection con,KeyValue simContextRef,DataContext dataContext,User simcontextOwner) throws SQLException,DataAccessException{
	Statement stmt = null;
	try{
		HashMap<BigDecimal, ExternalDataIdentifier> extDataIDHashMap = new HashMap<BigDecimal, ExternalDataIdentifier>();
		stmt = con.createStatement();
		ResultSet rset =
			stmt.executeQuery(
				"SELECT * FROM "+ExternalDataTable.table.getTableName()+
				" WHERE "+
					ExternalDataTable.table.ownerRef.getUnqualifiedColName()+" = "+simcontextOwner.getID().toString()
				);
		while(rset.next()){
			BigDecimal extdataIDBigDec = rset.getBigDecimal(ExternalDataTable.table.id.getUnqualifiedColName());
			ExternalDataIdentifier extdataID =
				new ExternalDataIdentifier(new KeyValue(extdataIDBigDec),
						simcontextOwner,
					rset.getString(ExternalDataTable.table.externalDataName.getUnqualifiedColName()));
			extDataIDHashMap.put(extdataIDBigDec,extdataID);
		}
		rset.close();
		
		rset =
			stmt.executeQuery(
				"SELECT * FROM "+DataSymbolTable.table.getTableName()+
				" WHERE "+
				DataSymbolTable.table.simContextRef.getUnqualifiedColName()+" = "+simContextRef.toString()
				);
		while(rset.next()){
			String nextDataSymbolName = rset.getString(DataSymbolTable.table.dataSymbolName.toString());
			String nextDataSymbolTypeS = rset.getString(DataSymbolTable.table.dataSymbolType.toString());
			DataSymbolType nextDataSymbolType = DataSymbolType.valueOf(nextDataSymbolTypeS);
			String nextDataSymbolVCUnitDefS = rset.getString(DataSymbolTable.table.dataSymbolVCUnitDef.toString());
			nextDataSymbolVCUnitDefS = TokenMangler.getSQLRestoredString(nextDataSymbolVCUnitDefS);
			VCUnitDefinition nextVCUnitDefinition = VCUnitDefinition.getInstance(nextDataSymbolVCUnitDefS);
			
			//FieldDataSymbol (ExternalDataIdentifier) - (only data symbol defined right now)
			BigDecimal extDataBigDecimal = rset.getBigDecimal(DataSymbolTable.table.fieldDataRef.getUnqualifiedColName());
			ExternalDataIdentifier nextExtDataID = null;
			if(!rset.wasNull()){
				nextExtDataID = extDataIDHashMap.get(extDataBigDecimal);
				if(nextExtDataID == null){
					throw new DataAccessException("PopulateDataSymbols extdataID "+extDataBigDecimal+" not found in database for user "+simcontextOwner.getName());
				}
				String nextFieldDataVarName = rset.getString(DataSymbolTable.table.fieldDataVarName.getUnqualifiedColName());
				String nextFieldDataVarType = rset.getString(DataSymbolTable.table.fieldDataVarType.getUnqualifiedColName());
				double nextFieldDataVarTime = rset.getDouble(DataSymbolTable.table.fieldDataVarTime.getUnqualifiedColName());
				FieldDataSymbol fieldDataSymbol =
					new FieldDataSymbol(nextDataSymbolName, nextDataSymbolType,dataContext,nextVCUnitDefinition,
						nextExtDataID, nextFieldDataVarName, nextFieldDataVarType, nextFieldDataVarTime);
				dataContext.addDataSymbol(fieldDataSymbol);
			}else{
				throw new DataAccessException("DataSymbol has no ExternalDataIdentifier");
			}
		}
	}finally{
		if(stmt != null){stmt.close();}
	}
}
}
