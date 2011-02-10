package cbit.vcell.client.desktop.biomodel;

import cbit.vcell.client.BioModelWindowManager;
import cbit.vcell.client.GuiConstants;
import cbit.vcell.client.desktop.geometry.GeometrySummaryViewer;
import cbit.vcell.mapping.SimulationContext;
import cbit.vcell.mapping.gui.StructureMappingCartoonPanel;

@SuppressWarnings("serial")
public class ApplicationGeometryPanel extends ApplicationSubPanel {
	private GeometrySummaryViewer geometrySummaryViewer;
	private StructureMappingCartoonPanel structureMappingCartoonPanel;
		
	public ApplicationGeometryPanel() {
		super();
		initialize();
	}

	private void initialize(){			
		geometrySummaryViewer = new GeometrySummaryViewer();
		structureMappingCartoonPanel = new StructureMappingCartoonPanel();
		
		geometrySummaryViewer.setBorder(GuiConstants.TAB_PANEL_BORDER);
		structureMappingCartoonPanel.setBorder(GuiConstants.TAB_PANEL_BORDER);
		tabbedPane.addTab("Geometry Settings", geometrySummaryViewer);
		tabbedPane.addTab("Structure Mapping", structureMappingCartoonPanel);
	}
	
	@Override
	public void setSimulationContext(SimulationContext newValue) {
		super.setSimulationContext(newValue);
		geometrySummaryViewer.setGeometryOwner(simulationContext);
		structureMappingCartoonPanel.setSimulationContext(simulationContext);		
	}

	@Override
	public void setBioModelWindowManager(BioModelWindowManager newValue) {
		super.setBioModelWindowManager(newValue);
		geometrySummaryViewer.addActionListener(bioModelWindowManager);
	}
}
