package cbit.vcell.client.desktop.simulation;
import cbit.vcell.simulation.*;

import javax.swing.*;
/**
 * Insert the type's description here.
 * Creation date: (5/11/2004 1:28:57 PM)
 * @author: Ion Moraru
 */
public class SimulationEditor extends JPanel {
	private JTabbedPane ivjJTabbedPane1 = null;
	private cbit.vcell.solver.ode.gui.MathOverridesPanel ivjMathOverridesPanel1 = null;
	private cbit.vcell.math.gui.MeshSpecificationPanel ivjMeshSpecificationPanel1 = null;
	private cbit.vcell.solver.ode.gui.SolverTaskDescriptionAdvancedPanel ivjSolverTaskDescriptionAdvancedPanel1 = null;
	private cbit.vcell.solver.ode.gui.SolverTaskDescriptionPanel ivjSolverTaskDescriptionPanel1 = null;
	private cbit.vcell.simulation.Simulation fieldClonedSimulation = null;

public SimulationEditor() {
	super();
	initialize();
}

/**
 * connEtoC1:  (SimulationEditor.initialize() --> SimulationEditor.makeBold()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1() {
	try {
		// user code begin {1}
		// user code end
		this.makeBoldTitle();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * Gets the clonedSimulation property (cbit.vcell.solver.Simulation) value.
 * @return The clonedSimulation property value.
 * @see #setClonedSimulation
 */
public cbit.vcell.simulation.Simulation getClonedSimulation() {
	return fieldClonedSimulation;
}


/**
 * Return the JTabbedPane1 property value.
 * @return javax.swing.JTabbedPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTabbedPane getJTabbedPane1() {
	if (ivjJTabbedPane1 == null) {
		try {
			ivjJTabbedPane1 = new javax.swing.JTabbedPane();
			ivjJTabbedPane1.setName("JTabbedPane1");
			ivjJTabbedPane1.insertTab("Parameters", null, getMathOverridesPanel1(), null, 0);
			ivjJTabbedPane1.insertTab("Mesh", null, getMeshSpecificationPanel1(), null, 1);
			ivjJTabbedPane1.insertTab("Task", null, getSolverTaskDescriptionPanel1(), null, 2);
			ivjJTabbedPane1.insertTab("Advanced", null, getSolverTaskDescriptionAdvancedPanel1(), null, 3);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJTabbedPane1;
}


/**
 * Return the MathOverridesPanel1 property value.
 * @return cbit.vcell.solver.ode.gui.MathOverridesPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.solver.ode.gui.MathOverridesPanel getMathOverridesPanel1() {
	if (ivjMathOverridesPanel1 == null) {
		try {
			ivjMathOverridesPanel1 = new cbit.vcell.solver.ode.gui.MathOverridesPanel();
			ivjMathOverridesPanel1.setName("MathOverridesPanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMathOverridesPanel1;
}


/**
 * Return the MeshSpecificationPanel1 property value.
 * @return cbit.vcell.math.gui.MeshSpecificationPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.math.gui.MeshSpecificationPanel getMeshSpecificationPanel1() {
	if (ivjMeshSpecificationPanel1 == null) {
		try {
			ivjMeshSpecificationPanel1 = new cbit.vcell.math.gui.MeshSpecificationPanel();
			ivjMeshSpecificationPanel1.setName("MeshSpecificationPanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMeshSpecificationPanel1;
}


/**
 * Return the SolverTaskDescriptionAdvancedPanel1 property value.
 * @return cbit.vcell.solver.ode.gui.SolverTaskDescriptionAdvancedPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.solver.ode.gui.SolverTaskDescriptionAdvancedPanel getSolverTaskDescriptionAdvancedPanel1() {
	if (ivjSolverTaskDescriptionAdvancedPanel1 == null) {
		try {
			ivjSolverTaskDescriptionAdvancedPanel1 = new cbit.vcell.solver.ode.gui.SolverTaskDescriptionAdvancedPanel();
			ivjSolverTaskDescriptionAdvancedPanel1.setName("SolverTaskDescriptionAdvancedPanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSolverTaskDescriptionAdvancedPanel1;
}


/**
 * Return the SolverTaskDescriptionPanel1 property value.
 * @return cbit.vcell.solver.ode.gui.SolverTaskDescriptionPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.solver.ode.gui.SolverTaskDescriptionPanel getSolverTaskDescriptionPanel1() {
	if (ivjSolverTaskDescriptionPanel1 == null) {
		try {
			ivjSolverTaskDescriptionPanel1 = new cbit.vcell.solver.ode.gui.SolverTaskDescriptionPanel();
			ivjSolverTaskDescriptionPanel1.setName("SolverTaskDescriptionPanel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSolverTaskDescriptionPanel1;
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	exception.printStackTrace(System.out);
}


/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
}


/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("SimulationEditor");
		setLayout(new java.awt.BorderLayout());
		setSize(547, 346);
		add(getJTabbedPane1(), "Center");
		initConnections();
		connEtoC1();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}

/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		JFrame frame = new javax.swing.JFrame();
		SimulationEditor aSimulationEditor;
		aSimulationEditor = new SimulationEditor();
		frame.setContentPane(aSimulationEditor);
		frame.setSize(aSimulationEditor.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.show();
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JPanel");
		exception.printStackTrace(System.out);
	}
}


/**
 * Comment
 */
private void makeBoldTitle() {
	getJTabbedPane1().setFont(getJTabbedPane1().getFont().deriveFont(java.awt.Font.BOLD));
}


/**
 * Comment
 */
public void prepareToEdit(cbit.vcell.simulation.Simulation simulation) {
	try {
		Simulation clonedSimulation = (Simulation)cbit.util.BeanUtils.cloneSerializable(simulation);
		clonedSimulation.refreshDependencies();
		getMathOverridesPanel1().setMathOverrides(clonedSimulation == null ? null : clonedSimulation.getMathOverrides());
		getMeshSpecificationPanel1().setMeshSpecification(clonedSimulation == null ? null : clonedSimulation.getMeshSpecification());
		getSolverTaskDescriptionPanel1().setSolverTaskDescription(clonedSimulation == null ? null : clonedSimulation.getSolverTaskDescription());
		getSolverTaskDescriptionAdvancedPanel1().setSolverTaskDescription(clonedSimulation == null ? null : clonedSimulation.getSolverTaskDescription());
		boolean shouldMeshBeEnabled = false;
		MeshSpecification meshSpec = clonedSimulation.getMeshSpecification();
		if(	meshSpec != null && 
			meshSpec.getGeometry() != null &&
			meshSpec.getGeometry().getDimension() > 0){
				shouldMeshBeEnabled = true;
		}

		int meshTabIndex = getJTabbedPane1().indexOfTab("Mesh");
		if(getJTabbedPane1().isEnabledAt(meshTabIndex) != shouldMeshBeEnabled){
			if(!shouldMeshBeEnabled && getJTabbedPane1().getSelectedIndex() == meshTabIndex){
				getJTabbedPane1().setSelectedIndex(0);
			}
			getJTabbedPane1().setEnabledAt(meshTabIndex,shouldMeshBeEnabled);
		}
		// ok, we're ready
		setClonedSimulation(clonedSimulation);
	} catch (Throwable exc) {
		exc.printStackTrace(System.out);
		JOptionPane.showMessageDialog(this, "Could not initialize simulation editor\n"+exc.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
	}
}


/**
 * Sets the clonedSimulation property (cbit.vcell.solver.Simulation) value.
 * @param clonedSimulation The new value for the property.
 * @see #getClonedSimulation
 */
private void setClonedSimulation(cbit.vcell.simulation.Simulation clonedSimulation) {
	cbit.vcell.simulation.Simulation oldValue = fieldClonedSimulation;
	fieldClonedSimulation = clonedSimulation;
	firePropertyChange("clonedSimulation", oldValue, clonedSimulation);
}


/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G370171B4GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E135DA8DF094C5968E90A0EA00G61A7D6BCA90CD7510B961E28B90BAAF31EF702D01A7367C4453BD8F27CD4A1976F7048F960F5B69B9288C890CE2397B5AAE290AE82D2F5D4C4ABA4618C9A0E88F10DA5064C6ECE32134C4EECE6E6331B9F12FB5DBDBDB33B19CDA2E5D275EA265F6B6E576F756B773E1E8D5A77760242FC47BE04728B117E771FFC04F69EC2682DE93D1B99AFC9F215A04B5FBBC0FB50D3
	DFBB6139GE4774DF71514222D794550DE8C6DC4E83F866FFBD1784C0A037092C3CF9A48EE3BABF07F48FA62C2CCCF91E99F60F74273E520CC606127D3467ABF634BF47EAA1D4FA85F0150865267DB3EDC67B5C03B8DA89DE809CC7BBF8A4F6DC473646A93FA3F0BB3535126879447AEBC0FE5CEC64783F4EC0B7E2CC0AF6623EE2CEBA2D9A79E13843225C0587882B4316C93F81EEC6A6A784C6396342A9E8FAF0AD59ED160A52D4A4B2BDD1A9C28D285FFD064B4C1162A2BAF986F273D02A6ABE8004C7FGDFCC
	66C3289E5A458A661D8CE21DD950DE8914EB708BFAB03F915E3DC0838A3373538FCE6AF634745F054A54DFEF5F9D434EE4055A37C4B16D948F398BDEA7FB1295463E4F83D9396AAAB982548C2481A5837D0B44427D218F61F94A2D5549FE3FAC1D8A84EAC52FF2D67672A23CD7D703C4671D67BD3262C5086EF782FF58E6BDA3919DB777870F23BEC97F186DFD4679DB512A5B67BA0AECFC32AA3D5F1121D2DF5C271ABE213A9F0A29FB359A991B113A3FC1D65DE87D3122C29B5D653FBBD614274F33D3B3F51F54
	70DEAFA2B1F0985E737578207C3B89BF4E4227F1DACB78381D8432CD9A0B0D161759DA9A1D8BD16951F936FE18FFEF2223A5C8579088C62FA5C462AEB5983D1689041F910CDE4B769E3616A5A02B68F1155078DA56074FEDBC3497GE582E58365826D8CC13F900B315270C7E30C31A6CE51EA0302686595C447AE8B3F8ACFBE2CA95C99C955B849433BB8C5605CA22FA23D4F43E16C83F7443847C96CFBC047673C44AB6420426C1C1F57F8C525FDF242FBF09F05A752E6414F0382FFG31F957057742D364D46D
	6B001753F83387156706711E74F0AE1891A600F76A173F76E3FF55007DADC0BE438FDD3DF83EAFF9C562456A6AC6D96E8A861A31A5A8B3047D3C06640E81F8CF8EB19F7FF9G7336C0FBDF184D333077954BBC750287AF1FD3FA59993BB308F3F3E518454C469696B3753BEEC1079F1BE39BB3C9903F75E19AAB974351E7A70B4C0957C55B8D4116B11E5F4A307D599ABDD7F0E356D1708367FF59582B36C13CD771304685C0D9060F1F6E2D20B1DB4B29BC0B52F38A84986FB5BDCE57F93947297B13362712764A
	B073556E1E969BFF7FD8C4F5D4929DD7620731907549A3FD388E71BDD0926B79GAFF961C555E5CF7661D8534FE93BEC0C49A17CG1C6BBAB8C82A41DF53074F9E27E90A608EEA7C85DE5384294314EFA43A5420BBE02684266B05BE1CFF65901CEB237F0BFD78CCF8E4C962496698C3F47996F2A6F9117743226B642024D13EE3G6FD5975F2B6704ED835B90BE53E29067970A8D96F198G8594418FDB0ED394EA6AA5584A40B6189070B046598F4F933D6C6C055EE3E7EFFF5FCDA86BD240E1D7BB665FB843B1
	3F171E130CDE736C51B5ED9E30DF7BD6129BF43ECA577AA5AA05E7B4B821311EB20467D9817367G657532D86F3D0C65AC1F68C1DEA70BA2F525616376C1BC374AF787F9C843B8F1FDE9784DBF78CA144C257B104A0303580722A567DD6C43BE4C6CE1582F340F7A2FD2FF5674D1BFD03B7EF2907B1A6033CE2E078BD729A108176A6AAF1AB8D8B545DE9D6D45FA7F74BE0631E68B58669CE867E1FD7DECDFFE3B0BE12C642EF434EA7D5122D8D832321F2E23315F5C17CD392736D11FBE0B304EC4102576B3FF66
	8EDA737CA9D515BD82598C5D97578FD9770D725206F64033DDD0D46D8C9C2CB0735B4DC3F83D388669EC3DA6BFC3EAB236F59168DD87D4C07AB704B0DF1084CD60C4210F2FBCC87D770BA63CF7FE2E0B2F15C5EF332009A0F2D1DFAC5CDB235B3A6256056864D02B438EEFA66C76B9AA86288F9A86CCDFA44FF697248351BCA436C7E32C6481B37F278F8C2F8594FB94F562F5AF990846A4FB88FF45C0B4A6C99198A6D985321A4B2CDEAA8E9CA7E9504E82DA89148BD4BA8878F7106D4FB174B19A850F1C11GA6
	C81CF8A1A281B1DCC074C24E505B9B48BE101C01D8BEDE5B936D074641C85BFDD1EDBA6E05E1632A225A09D163285D5985789C2501AC8BE8A550A62032A1D749F12016A1E6FBCF7E272359FE9AA778E6398EF88DF0DE6B22F3AA0C27F8E592B9EFE118BB9108725AFC1837885ACB07D87D5B5509F36C86237D9AE9BB63D87BFAA13A4E633EBFEEFD4C9C2663472C1554377D0E5875F122433EBEA6B86C6B63F4479E4A07B92C28184967BAC62E1FBFF158554FB7095D4BDE5DCD66D6D5DEA51BDD4FE91CAEDB2B7D
	935954517F81F056FD36973D955A2E77B92D49F5C78D1DCF9644367F1982FB7B5796603C2B4AC14543C75B3831204C1019D1F8C6637DF8B9FAEDA463B5CE6960077968A5B21E4AE20DAFF24672A335755AF60C87C42ED78EEA104B62F00C5F564E6AFEA534EB00FCC083F1943753FCFBDB88573BF638F040F497823C8785A6103B174F789E827DF371FC40BF8254B401657036CFF0EE7B824A241B77627AF614620D06CEAB5E481CC0752D06E79E904D0BCF34336F9B2593DDA58D9359FA56D4E1FB0D7AD9450534
	6A6ADA99577AC6EA9C2D216FF4946B6363A6329AFA2E834F19896DEC20F593D98D8D9FE0B5B463278BD1769F6DEBE8CE73E5871AC44F737EC9E65D08BB64AE20BA2F73B11DEDA01FBF8973167B76C51D55BB9CB497D2795688B95EC37CC94B14FFA4D865F83D1FBB2DF2AEC2DEE899CFEDDB7EBE33ED4B72C5C83CA3BE6637269C5DA6E73CEE5B03A979CDA9CD5B285F8F953F09ECF10B393BA027FE086BE45F0ECE46B35FDF5705FD2FC1BB91A8AD1E654CBF8C2B33E34B034FDD6138839DBFF49C2D5F6F78C8DD
	0F2775BB5F60FFC878CBADFC9A3FDFF73078FD94E4A7A6333A7EDDAB0E7DD4E8E782E5832D83AA1E82F8FB8A2BEDB7BB0F20D1EADB339CE80484A5DAD2AA35EF216F4A3ECB7D32084549623BEFC619D36A9DA5B1626444947D1B247E4CDC1112EF566F0D1730FD89A0CB83DA8A14833489E867D423F6FFFF185A07C17DA9CD63BCBE02A019F1AE018F19EC1CF428ED13BBC7322DE2EAACCC36C744FBF1FCAA5D236D867FAD42EF3270695E5DC0E21560D510E5CFE5317A018F99F1F444727EDD47D86F77D7C2D407
	B62EBA683EF8DBE3573D2ED67B3A575DEADF77C32DA357752156587762A92D56FBB10D2B639C4EF34793203EGE9C089C02989AC2676DDFA174614CDBC1D76F6603A0B3F3AE9CC7F66CB31EDFD6952685F6A0A5A70BD47ADEB1A6C4773B37E2EB63CDF1A9CA0CC4AFB258D7BC891BAFC9A6D2A734B5AF01E9479F6422638B33513614E4C8483E70A784E339A5A4E2B60DE8954F495136DA732B8E82781AD855AE048EE6A320B99BCC7B42F520647886B0D66197A6F3555D198A30EE3693043305B8DBF1D64E26FD5
	9DB77ADEB5F2641E8ABEEE6E8D68F869AB8E63A1393DDD650D18F0F125866F0C5AA42B8246D97B2F467BF2FBE745886BAF3D1A7A08621E8BDF61FB73D9CE731D6B6195C57072AA395D9F55657C9B78772B333C6A4339CDE897BC7AE7DDCB1F53645B4885D904991AB9352B1ED7BD0A90985E4F77DE2CFE273CBD589DDE3DBF45DEAD2295FB0930EEFCFF4900E796906DD3ADD97B2C547B6C3B86E26F9A5627BCE06DD3F38D6D13G4FD4234F5F3A2DFD966BFD1C09F01FCBE498B13B936FC39D0F6F292C9605F8D6
	0B4AFFF683AAFF3B065C916CEA5111C47DBBEF220947D87C952067F79CC343FF8BD3092F2A201AD6F584056A6A617B44F0CC12483E43A4A5B29CB355CF6ABD34F300729299AECDAEBAE2234B8FF36BDAECF71B6AD1A42627F49A53A348D8CF83345B004253181E675A2B47E0D36C38E136B5878C5B8C1D7F6DA636C1BB87A84F5079D8D56D156A0C08C126FB1EEA58BB1D693ECAA376C23B8DA8BC1D6125875D785CA5C1BB83A89BE89D509620FDB3A09E01EAE6309A300E2B04270B6B5491937DE6C82AGB069A5
	0154810593A40D662FB962C81862620C485C9700B6458EFFA7F190BFA39A8FBAA54CCF1A910D872779999ECC8599CAE279B3A40D245F1B9429BF1C4474BFCD62A3B1A9DA7F9A421F1F942DDF16197EB410954FE47A3F8D0C24DF1C9929BFEEA653FFB709156419517AFFC5788BE6C66BEF6CE67AB3C0D6BA0B69CFD5C7522F4D0A541FB00B695FCA62A5F5D6347EED64774674D9517AE7E8CC7FE210154DE6B17304BBF6ED49F10FDEDB563B6DF1401F5C43F140A637BD8EF851ED458114778FFDDE4578E8C17905
	9EFCFF237778331C44F5D83EE3E5088CBB8C4CE65F31F67BB1EF8134974FE6BE4F1730DB687B8E4B7BEC7F7863580CF155C1ED1EAEB1BFE499FEF81E70D6C3BB4F70433332E97B864BFBD09EFFBFCC9FD79D5466C0007941194C7C70C80045C3E5B273C3D637E97BB24B7B796E7177C357386A20B65F2EB2BF9CB77C702A467C50E27821A0E85A3E5372BEC79BFFBF5CBA2EBA4249A4B79B584F1BBC9C53D3BCF600648B0DF3131FD63212717FCCBDAAA0E30E9B8DEC681C93EB0EE2F9789C1BBB4DB9269A58314C
	E68E5A7F8D516C7F004A7A9F0E597F3C9F772778E23D015BEA6630EFFCCB3F37FE872BB8F44E76BBD85E77FD0E302EA3F50E09A9AFDEF7FCBB1D67216F2C7F3BD5FA2D6848371987D336BEFE15E59E3A3E65A65E1B8B391A70EEB170D803414BB5F0D25CDC96239BA370CAA670D74C9D79B7860A236EA47B1683FD37GD54CF3151CGEA84724EE335F02EFFA47CC4BF2F1A78094E7B84491BF47CD37E77E30D07DB7897DC8FF74E5D49FB34E62E43087B6C887C930635A44E9B599EBA6E679138E5810CD9BC26F1
	AFC4600D25B0E655A863E83EA8A467A2977A9EACE03C9D04D7BC5FD5324B41FCF00F949BCF7CC69A9DCFBCA4596309472461F862C9499ECF64C8433FAB2C15587AF987DB7F86092DFFEFBE63AD8B30FE2F9BF6BE98E07DE4235FCE0D753B4749F8FBB4E3BE873B03F187AC3F63FEA3F8B59F651707F7197C869E5BGED7AFF1DB31A717F72E5BC078AB534437CE7A78DB971AD8A9E78122121E2F639E0774603C3563319B759E37BBF8A180AD3681E172718E713467343AEECC38B703594F6AE5EF57DFB04383E60
	67C4D163442ED3129747F8ED2F461B5F399EF76179D556C797D2596F496F561D16B17F87D0CB8788G39C3C5528EGGB4ABGGD0CB818294G94G88G88G370171B4G39C3C5528EGGB4ABGG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG8C8FGGGG
**end of data**/
}
}