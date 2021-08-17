package org.vcell.util.gui.exporter;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.vcell.sedml.SEDMLExporter;
import org.vcell.util.FileUtils;

import cbit.util.xml.XmlUtil;
import cbit.vcell.biomodel.BioModel;
import cbit.vcell.clientdb.DocumentManager;
import cbit.vcell.mapping.SimulationContext;

@SuppressWarnings("serial")
public class SedmlExtensionFilter extends SelectorExtensionFilter {
	private static final String FNAMES = ".sedml";
	
	public SedmlExtensionFilter() {
		this(FNAMES, "SedML format<Level1,Version1> (.sedml)", SelectorExtensionFilter.Selector.FULL_MODEL);
	}
	public SedmlExtensionFilter(String fNames, String name, Selector selector) {
		super(fNames, name, selector);
	}

	@Override
	public void writeBioModel(DocumentManager documentManager, BioModel bioModel, File exportFile, SimulationContext ignored) throws Exception {
		String resultString;
		// export the entire biomodel to a SEDML file (for now, only non-spatial,non-stochastic applns)
		int sedmlLevel = 1;
		int sedmlVersion = 1;
		String sPath = FileUtils.getFullPathNoEndSeparator(exportFile.getAbsolutePath());
		String sFile = FileUtils.getBaseName(exportFile.getAbsolutePath());
		String sExt = FileUtils.getExtension(exportFile.getAbsolutePath());
		
		SEDMLExporter sedmlExporter = null;
		if (bioModel != null) {
			Object[] options = {"VCML","SBML"};
			int choice = JOptionPane.showOptionDialog(null,	// parent component
					"VCML or SBML?",			// message,
					"Choose an option",			// title
					JOptionPane.YES_NO_OPTION,	// optionType
					JOptionPane.QUESTION_MESSAGE,	// messageType
					null,						// Icon
					options,
					"VCML");					// initialValue 
			boolean bForceVCML = choice == 0 ? true : false;

			sedmlExporter = new SEDMLExporter(bioModel, sedmlLevel, sedmlVersion);
			resultString = sedmlExporter.getSEDMLFile(sPath, bForceVCML, false);
		} else {
			throw new RuntimeException("unsupported Document Type " + Objects.requireNonNull(bioModel).getClass().getName() + " for SedML export");
		}
		if (sExt.equals("sedml")) {
			doSpecificWork(sedmlExporter, resultString, sPath, sFile);
			return;
		}
		else {
			XmlUtil.writeXMLStringToFile(resultString, exportFile.getAbsolutePath(), true);
		}
	}
	
	public void doSpecificWork(SEDMLExporter sedmlExporter, String resultString, String sPath, String sFile) throws Exception {
		String sedmlFileName = Paths.get(sPath, sFile + ".sedml").toString();
		XmlUtil.writeXMLStringToFile(resultString, sedmlFileName, true);
		sedmlExporter.addSedmlFileToList(sFile + ".sedml");
	}
	

}
