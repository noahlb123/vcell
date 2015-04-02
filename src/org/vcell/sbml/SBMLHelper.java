package org.vcell.sbml;

import org.sbml.libsbml.SBase;
import org.sbml.libsbml.SBasePlugin;
import org.sbml.libsbml.XMLNamespaces;
import org.sbml.libsbml.libsbmlConstants;


public class SBMLHelper {
	
	public static final String SBML_NS_1 = "http://www.sbml.org/sbml/level1";
	public static final String SBML_NS_2 = "http://www.sbml.org/sbml/level2";
	public static final String SBML_NS_2_2 = "http://www.sbml.org/sbml/level2/version2";
	public static final String SBML_NS_2_3 = "http://www.sbml.org/sbml/level2/version3";
	public static final String SBML_NS_2_4 = "http://www.sbml.org/sbml/level2/version4";
	public static final String SBML_NS_3_1 = "http://www.sbml.org/sbml/level3/version1/core";

	public static String getNamespaceFromLevelAndVersion(long level, long version) {
		String namespaceStr = SBMLHelper.SBML_NS_2;
		if ((level == 1) && version == 2) {
			namespaceStr = SBMLHelper.SBML_NS_1;
		} 
		if (level == 2) {
			if (version == 1) {
				namespaceStr = SBMLHelper.SBML_NS_2;
			} else if (version == 2) {
				namespaceStr = SBMLHelper.SBML_NS_2_2;
			} else if (version == 3) {
				namespaceStr = SBMLHelper.SBML_NS_2_3;
			} else if (version == 4) {
				namespaceStr = SBMLHelper.SBML_NS_2_4;
			} 
		}
		if (level == 3) {
			namespaceStr = SBMLHelper.SBML_NS_3_1;
		}
		return namespaceStr;
	}

	/**
	 * "safely" get plugin, casting to correct type and providing information
	 * error message if fails
	 * @param pluginType desired type
	 * @param source 
	 * @param name
	 * @return appropriately cast plugin
	 * @throws ClassCastException if plugin of wrong type
	 * @throws IllegalArgumentException if name incorrect
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SBasePlugin> T checkedPlugin(Class<T> pluginType, SBase source, String name) {
		SBasePlugin pi = source.getPlugin(name);
		if (pi != null) {
			if (!pluginType.isAssignableFrom(pi.getClass())) {
				throw new ClassCastException(
					"Unable to cast sbml plugin type " + pi.getClass( ).getName() + " retrieved from type "
					+ source.getClass().getName() + " using name " + name + " to type " 
					+ pluginType.getName( ));
			}
			return (T) pi;
		}
		//no such plugin -- list the available ones in error message
		String s = "No plugin named " + name + " on type " + source.getClass().getName( ) 
				+ " available plugins are ";
		final long n = source.getNumPlugins();
		for (int i = 0; i < n; i++) {
			pi = source.getPlugin(i);
			s += pi.getPackageName() + ", ";
		}
		throw new IllegalArgumentException(s);
	}

	private final static String XHTML_URI="http://www.w3.org/1999/xhtml";
	public static void addNote(SBase destination, String note) {
		XMLNamespaces ns = destination.getNamespaces();
		if (!ns.containsUri(XHTML_URI)) {
			ns.add(XHTML_URI,"xhtml");
		}
		int rcode;
		String wrapped = "<p xmlns=\"" + XHTML_URI + "\">" + note + "</p>";
		if (destination.isSetNotes()) {
			rcode = destination.appendNotes(wrapped);
		}
		else {
			rcode = destination.setNotes(wrapped);
		}
		if (rcode != libsbmlConstants.LIBSBML_OPERATION_SUCCESS) {
			throw new RuntimeException("Unable to add note " + note + " to " + destination.getName() 
					+ ", " + LibSBMLConstantsAdapter.lookup(rcode));
		}
	}
	
	/**
	 * map Java types to corresponding SBML constants 
	 */
	public enum DataKind {
		UNKNOWN(libsbmlConstants.DATAKIND_UNKNOWN),
		DOUBLE(libsbmlConstants.SPATIAL_DATAKIND_DOUBLE),
		FLOAT(libsbmlConstants.SPATIAL_DATAKIND_FLOAT),
		BYTE(libsbmlConstants.SPATIAL_DATAKIND_UINT8),
		SHORT(libsbmlConstants.SPATIAL_DATAKIND_UINT16),
		INT(libsbmlConstants.SPATIAL_DATAKIND_UINT32)
		;

		private DataKind(int code) {
			assert(ordinal( ) == code);
		}
		
	}
	
	/**
	 * map Java types to corresponding SBML constants 
	 */
	public enum CompressionKind {
		UNKNOWN(libsbmlConstants.COMPRESSIONKIND_UNKNOWN),
		UNCOMPRESSED(libsbmlConstants.SPATIAL_COMPRESSIONKIND_UNCOMPRESSED),
		DEFLATED(libsbmlConstants.SPATIAL_COMPRESSIONKIND_DEFLATED),
		//BASE64(libsbmlConstants.SPATIAL_COMPRESSIONKIND_BASE64); proposed 
		;
		private CompressionKind(int code) {
			assert(ordinal( ) == code);
		}
	}
	
	/**
	 * map Java types to corresponding SBML constants 
	 */
	public enum InterpolationKind {
		UNKNOWN(libsbmlConstants.INTERPOLATIONKIND_UNKNOWN),
		NEAREST_NEIGHBOR(libsbmlConstants.SPATIAL_INTERPOLATIONKIND_NEARESTNEIGHBOR),
		LINEAR(libsbmlConstants.SPATIAL_INTERPOLATIONKIND_LINEAR);
		private InterpolationKind(int code) {
			assert(ordinal( ) == code);
		}
	}
	/*
	public enum BoundaryKind {

		UNKNOWN(null,libsbmlConstants.BOUNDARYCONDITIONKIND_UNKNOWN),
		DIRICHLET(BoundaryConditionType.DIRICHLET, libsbmlConstants.SPATIAL_BOUNDARYKIND_DIRICHLET),
		NEUMANN(BoundaryConditionType.NEUMANN,libsbmlConstants.SPATIAL_BOUNDARYKIND_NEUMANN),
		ROBIN_VALUE(null,libsbmlConstants.SPATIAL_BOUNDARYKIND_ROBIN_VALUE_COEFFICIENT),
		ROBIN_SUM(null,libsbmlConstants.SPATIAL_BOUNDARYKIND_ROBIN_SUM),
		ROBIN_INWARD(null,libsbmlConstants.SPATIAL_BOUNDARYKIND_ROBIN_INWARD_NORMAL_GRADIENT_COEFFICIENT) 
		;

		BoundaryConditionType boundaryConditionType;
		int sbmlCode;

		private BoundaryKind(BoundaryConditionType boundaryConditionType, int sbmlCode) {
			this.boundaryConditionType = boundaryConditionType;
			this.sbmlCode = sbmlCode;
		}
	}
	*/

}


