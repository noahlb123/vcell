/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.geometry.gui;
/**
 * Insert the type's description here.
 * Creation date: (5/26/2004 1:59:21 PM)
 * @author: Jim Schaff
 */
public class ResolvedLocationTablePanel extends javax.swing.JPanel {
	private javax.swing.JScrollPane ivjJScrollPane1 = null;
	private javax.swing.JTable ivjScrollPaneTable = null;
	private cbit.vcell.geometry.surface.GeometrySurfaceDescription fieldGeometrySurfaceDescription = null;
	private boolean ivjConnPtoP1Aligning = false;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private cbit.vcell.geometry.surface.GeometrySurfaceDescription ivjgeometrySurfaceDescription1 = null;

class IvjEventHandler implements java.beans.PropertyChangeListener {
		public void propertyChange(java.beans.PropertyChangeEvent evt) {
			if (evt.getSource() == ResolvedLocationTablePanel.this && (evt.getPropertyName().equals("geometrySurfaceDescription"))) 
				connPtoP1SetTarget();
		};
	};
	private ResolvedLocationTableModel ivjResolvedLocationTableModel1 = null;

/**
 * ResolvedLocationTableModel constructor comment.
 */
public ResolvedLocationTablePanel() {
	super();
	initialize();
}

/**
 * ResolvedLocationTableModel constructor comment.
 * @param layout java.awt.LayoutManager
 */
public ResolvedLocationTablePanel(java.awt.LayoutManager layout) {
	super(layout);
}


/**
 * ResolvedLocationTableModel constructor comment.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public ResolvedLocationTablePanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}


/**
 * ResolvedLocationTableModel constructor comment.
 * @param isDoubleBuffered boolean
 */
public ResolvedLocationTablePanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}


/**
 * connEtoM1:  (ResolvedLocationTablePanel.initialize() --> ScrollPaneTable.model)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM1() {
	try {
		// user code begin {1}
		// user code end
		getScrollPaneTable().setModel(getResolvedLocationTableModel1());
		getScrollPaneTable().createDefaultColumnsFromModel();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * connEtoM2:  (geometrySurfaceDescription1.this --> resolvedLocationTableModel.geometrySurfaceDescription)
 * @param value cbit.vcell.geometry.surface.GeometrySurfaceDescription
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM2(cbit.vcell.geometry.surface.GeometrySurfaceDescription value) {
	try {
		// user code begin {1}
		// user code end
		getResolvedLocationTableModel1().setGeometrySurfaceDescription(getgeometrySurfaceDescription1());
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * connPtoP1SetSource:  (ResolvedLocationTablePanel.geometrySurfaceDescription <--> geometrySurfaceDescription1.this)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP1SetSource() {
	/* Set the source from the target */
	try {
		if (ivjConnPtoP1Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP1Aligning = true;
			if ((getgeometrySurfaceDescription1() != null)) {
				this.setGeometrySurfaceDescription(getgeometrySurfaceDescription1());
			}
			// user code begin {2}
			// user code end
			ivjConnPtoP1Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP1Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * connPtoP1SetTarget:  (ResolvedLocationTablePanel.geometrySurfaceDescription <--> geometrySurfaceDescription1.this)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP1SetTarget() {
	/* Set the target from the source */
	try {
		if (ivjConnPtoP1Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP1Aligning = true;
			setgeometrySurfaceDescription1(this.getGeometrySurfaceDescription());
			// user code begin {2}
			// user code end
			ivjConnPtoP1Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP1Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}


/**
 * Gets the geometrySurfaceDescription property (cbit.vcell.geometry.surface.GeometrySurfaceDescription) value.
 * @return The geometrySurfaceDescription property value.
 * @see #setGeometrySurfaceDescription
 */
public cbit.vcell.geometry.surface.GeometrySurfaceDescription getGeometrySurfaceDescription() {
	return fieldGeometrySurfaceDescription;
}


/**
 * Return the geometrySurfaceDescription1 property value.
 * @return cbit.vcell.geometry.surface.GeometrySurfaceDescription
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.geometry.surface.GeometrySurfaceDescription getgeometrySurfaceDescription1() {
	// user code begin {1}
	// user code end
	return ivjgeometrySurfaceDescription1;
}


/**
 * Return the JScrollPane1 property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getJScrollPane1() {
	if (ivjJScrollPane1 == null) {
		try {
			ivjJScrollPane1 = new javax.swing.JScrollPane();
			ivjJScrollPane1.setName("JScrollPane1");
			ivjJScrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			ivjJScrollPane1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			getJScrollPane1().setViewportView(getScrollPaneTable());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJScrollPane1;
}


/**
 * Return the ResolvedLocationTableModel1 property value.
 * @return cbit.vcell.geometry.gui.ResolvedLocationTableModel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private ResolvedLocationTableModel getResolvedLocationTableModel1() {
	if (ivjResolvedLocationTableModel1 == null) {
		try {
			ivjResolvedLocationTableModel1 = new cbit.vcell.geometry.gui.ResolvedLocationTableModel();
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjResolvedLocationTableModel1;
}


/**
 * Return the ScrollPaneTable property value.
 * @return javax.swing.JTable
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTable getScrollPaneTable() {
	if (ivjScrollPaneTable == null) {
		try {
			ivjScrollPaneTable = new javax.swing.JTable();
			ivjScrollPaneTable.setName("ScrollPaneTable");
			getJScrollPane1().setColumnHeaderView(ivjScrollPaneTable.getTableHeader());
			getJScrollPane1().getViewport().setBackingStoreEnabled(true);
			ivjScrollPaneTable.setBounds(0, 0, 200, 200);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjScrollPaneTable;
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}


/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	this.addPropertyChangeListener(ivjEventHandler);
	connPtoP1SetTarget();
}

/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("ResolvedLocationTablePanel");
		setPreferredSize(new java.awt.Dimension(200, 300));
		setLayout(new java.awt.BorderLayout());
		setSize(420, 205);
		setMinimumSize(new java.awt.Dimension(100, 200));
		add(getJScrollPane1(), "Center");
		initConnections();
		connEtoM1();
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
		javax.swing.JFrame frame = new javax.swing.JFrame();
		ResolvedLocationTablePanel aResolvedLocationTablePanel;
		aResolvedLocationTablePanel = new ResolvedLocationTablePanel();
		frame.setContentPane(aResolvedLocationTablePanel);
		frame.setSize(aResolvedLocationTablePanel.getSize());
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
 * Sets the geometrySurfaceDescription property (cbit.vcell.geometry.surface.GeometrySurfaceDescription) value.
 * @param geometrySurfaceDescription The new value for the property.
 * @see #getGeometrySurfaceDescription
 */
public void setGeometrySurfaceDescription(cbit.vcell.geometry.surface.GeometrySurfaceDescription geometrySurfaceDescription) {
	cbit.vcell.geometry.surface.GeometrySurfaceDescription oldValue = fieldGeometrySurfaceDescription;
	fieldGeometrySurfaceDescription = geometrySurfaceDescription;
	firePropertyChange("geometrySurfaceDescription", oldValue, geometrySurfaceDescription);
}


/**
 * Set the geometrySurfaceDescription1 to a new value.
 * @param newValue cbit.vcell.geometry.surface.GeometrySurfaceDescription
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void setgeometrySurfaceDescription1(cbit.vcell.geometry.surface.GeometrySurfaceDescription newValue) {
	if (ivjgeometrySurfaceDescription1 != newValue) {
		try {
			cbit.vcell.geometry.surface.GeometrySurfaceDescription oldValue = getgeometrySurfaceDescription1();
			ivjgeometrySurfaceDescription1 = newValue;
			connPtoP1SetSource();
			connEtoM2(ivjgeometrySurfaceDescription1);
			firePropertyChange("geometrySurfaceDescription", oldValue, newValue);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	// user code begin {3}
	// user code end
}


/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88GD3FBB0B6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E135BAEDF0945711D48EDF280BE2131C6DE0629F8A41366EA23BC89DA1549DF5D6924AA74778CC944861D8AE131CAB41D5640A72A9D744255CBD2DD05845A6163162A8B1F194DB09059132048488A130D69F20C5C2DA2485C9A0A20DF6C75A015D1DE5F676CB9FF05D6F4D1B9DADB3DA1B102D6A1AFD7DBE7A63F5776BFEB324627759E5A5368AC2CA4A0876BBD6C24812BAC21E7E5983759C577CFE010398
	FEFFGF803644D6C02E7B774ED7394B84AC9B8E40776A83433207DEB787FA609062A8F419F8BBA453504484B4B6EDB184EF397B81D5D347D367DB5F87E92E09CF0784CFD9F69B75AABB57CE28D4F2144C648529A9C53E75F2F61F64098BB60FA8146EB587884BC9983F735B4EB63C6BF17C30A3E2D587662BA06B5B9EC516696EB4FAA72CA494A83C8ABCE7944B9CD508EG207025C49E4235DB5D5726CEF9A7A435AE669583013AA9D18E0A2A122C1B0ACAADAD83E2C48E44C4DF2F6C95D4C98EF90409006896C2
	E2206A83DC7BF13B1D2EC548B6D8B71762D64DA2BD9F7C1F853877830E6F3F01789D705FFE3040D1F5104BF8727DD6CD0661192F13CD07033BDE37103179A013ED7CE0CA466D0167ED3FE17BB104FAFF907A72GB6816C84282CADF0B4833CCD6D605D0B9F4053B921F64841209CF206436D811FF2CE760981785F5282BD9AEEC0744A0A0F903657F376DA93FE9682B66FB31FF89E53C9CEA35F775C0147C861BF5DEF5BED2213421C991B3B166942DF1B5289233D5E1276125A79F6B91F7690B1522EFDF6F3E919
	896D6EA7B617BE282D334AC0FBDBAD6EF5759C6E75C678DF226902616BA93E76D08A4FECF48D3599ECF7C2DF4EA1EE9B433F643C746EDAC14A9B3EE02A07E55F4C323DD647F8282ECB6765DB546E5CF50C26C367E5A345878CF8464B850A477692685B8E406C2BFA84FD567DA1789040E20087815681EC81587EA13731EB97BF7C18B666969435BDAA85FC22C2585C6A213760A9A6D4C5F005A22A90720A9785C5C22F0B90ED4C21A15441443CF907296DFB014699B1A4AA54D7E1F581DCD9D4A2EC4C0921B7F00C
	A2B2CF1602A290880689DF37E368CD540790D1AF04FD02AA26C6307E1EA154C9CC3888AB4282701F69657CA55457EB75850EEA0026FA2E07A768FABD228251222525D7162FC543B4FE104575286767E86C68067F6375DC4767AEA3AE9F5ADB75F5FEBE344F30CE2764C57685A549FD6C83EAD3C5B0FEFFC3012333015B4EFF8DF35B695C7BA8295DFC1F296D2C83BB8EB4B01BD95C106EC3C7695A48DFAE74EDE86031FF5D6CB0BC77B7187B51A781641B72E8D80B5158CCE9147D056BA3149A5665B8266FFF99C5
	793683ED7B6182C74DE12E736661AAE6436DC2C464D65B2F0041093E548EB0BE1F3CD12E6935D463F737AE4377E8DF9B2C6B87D88A3076B05761659B43A6737ED2D9676BD025B40C6BBB6EA08D262B3A91BC2F441898D2BB45309872411F0856F7E48469E97EBCA96BF3CED0FC987C3F839CAE22631DA36823022AAA52C4D49587C5D515C2D329FE97259509CE04D3210353F20F200CF29C7CDF9FFFE184FD47AB07C2A25DB4FD0A56BFC4FDD78C08C1E03AC30E06D4063FF289F31DEBE2D20B9D4225DF90747DC0
	9463904B9FD6892F228C0A9484D340D0C66A9B512FDF56739F042D305F18876D023E4AC66E1B717738EFD6CF7F83499B8B5B4C4E18E58F5DE3CBB4B23F48EACC79A663E96032B96F43B406E8788863B53E673770344EE6180D3CC954E6DC0367C003D1E5D2700A1DE2442BC8E11C51200D5B326FED2B75E820EF507C2B688A72188B3C6781ECE9647E3569B276731826B9D6079C8830ED023E4AA654654CDC05260B9DB0575E44F4096D15347F3D9B9586FD0D9B7A991DEF0C227FC5446BD1910E92E42FC75F537C
	51FDE9FD97357DE57D9BC7F1FF8306386DB902B4775C64396BF60D5EAE406F87E8BB42E89752F14F696332318FFD7F880B219B60D987F87FD19C7777040F2BB94A56EBBB2A45DB98778E60995D1192BEEE35211F2EF71451E5FB7D5F87507EE8DEFBD50889093AC89CFC2825652CA1EF9DF55835D8D0F41467271E984DCD0E419982B0FA0C5B6AF776727C745EEBB9447E7DCD25D6B938FA0C71316CD84AD6D75F9FAFE536F0EA8CE9EE013EED47389D74DCB11E114EC8C476CA541434BDF8650A5116996E7F2E6C
	01672424C4D497841B845F2F32BFA33FF8FEEBE8AD1FD9C14FB0345BE2205B548C79CBB362DF18C39FC7DDB3B93D0DDC4E25538F11717B165BDE3510F3D5B313EFE3F3CA4E4595C7CA197E3FB4FA1BF585759C464B5CE9234E573DBEE7490B7DB86321663821A6785A7F14B2599BEFF2597D501F8D407CF7B945CBA1C9150400B4AD569CE0312BBCE90CDDDBE0FCB1C0D58B682D054FDDFF93658CA23304674C0F3E53264D5970588AE2976E321955B0D9AFCD59FADB981FEACB0A5F6ECD4F5BC681984F3B8867F9
	9D0CA9EA6175DA45A9FC5298CCF83C1AA338072ADF0AB0BEE6C7B89F0BB62CA615EFFE56148F045A930C3E67C40A0F0A9FBEEF2B79A167E31D4E47CA98D3F8024B7F53B90C2556B1126B64E527D1A7EBF7E7197262FFE4192D221571505A1A62E5837031D56765D3A52FE97E1D85E35637F2DE765AB2714272G4141EB21E20DC67949D43ED58D7F1BCF4E4F65D80D10CFE3C26FC9D6A3146A78820A9FB560991F17E2DCE781685BF81267353BA7502E1ACE95B85CGFE00C5G59GF927F8AC786C1F9BC9063A41
	9502F4BEA4848667A5GBCFF9FC33AF0E6EB6DB3E3C6FBE1FA4819C3BD941E7A78F9AB1B37064EAB7A8473188D9C38E43C3F587546D9DB2905BDD6FF4456EE7AA8E583CCF7EB3DF8462900DF8A108310873075A32E378DE335197446EBA8CDE1EC5DF7C4DC17344159855084508B90E8636B3EB51EF1BF3A92267B41F4F67CE6265ABF2BED3EBE593CB399672DCA1B47624355990BF30F86D7965F9CF1E3FC4BEFE335D791BC6DCE08F3749C6CCE72BB1DAAA7BF9BFFBE03F6BD8C6DB04092A737EBF5924F64ECE8
	2F835884308320223D40D18F60E9673A740D1F4828CBCC20BD3213A6C09D69992B261BBD6476F4DA19F11E5ABEFF1E163FCD21FCD95017831087D088C0BA40E6G5ABA38FCEB465BB349271763A0A34F6FEF95D363771B37A967460C7306BB4C6414A414B38CFDD9GAB8172GDE84A01DD057F7F2B97788996554057BB8F2AEA9C9673735F3BEFF66F25EFF4B3CF75265CCDB075DB9A4E85E97063EACG067B360A38ECE82F56F10F44E82E866D9DBAAEBF0E380A2E82C7ED972FE17FBDC6F3A83D3D0536B1E7E0
	6D7158F0067D78B8006BDB4766649D21D1EA01E77BDEBEE1DD637EFA423C467D5D04F90D7B5E449B8C8FEB98EF40F87F410905EB604689339A78F7D4066F59B0CEA9D08BCAC11112E531D1CC7C01596DC0B444CC97A9C7524F875D9F9E390A7BB85C456D7E490943A6F65F6513D4D9610BF8C4C1691F1C540DFEB0ACFAB94FF9DEEB7D7D1B57DCFFCFFB4D7577DD6F427AFB4E3B307E7EA4DA73F2C8B46725D1B467653838B0AFCE510A9736DFBFD6FFC5CC6EA77AA0D2921E6306EFBA34B1DD2AFC2E017073EE2A
	8B57B0B96F863D8AD452D86DC1BFBB531C54F751DF54AEFE26DDCE505A9C5A6B818AF59FBE17DCA806961978A24B892F53BBEC7BE91693166878E90A2FB060D92ED8BA4B5F4BD4C15F62535C56EECCDA6F4FDD13667B7319C92CC9A3F2D4710A69FB707949CA3DAF9508DD2A98C4B533B1DF2473D5C11992EF594395F4BE6B331A7F15C92BFDE6FB68A42FEAFB681ED464203BC1DB7BFF272CE5AD731B4B7A2A5FDA56FD7E4C323E6D371675DDFFE6D98F7AD1168949A7A95AED8D1371E7045B29DB155D8D7A1A91
	49DA46E949DC46C512350C1F16B24B38D43216718BD2E6993325F499193FBE79566B545FA391B1C29336CEC195B4799FAFB17A69E1FD6F6FF1A0AFE1C51C9495C5748DC2390DB6FF86FDA0071E475B40760BCE731A6E87AA5EB58604249CD58959C847DD0C723C55876DD90015GEB4F307C1545032F461167C948324074E8704D6D46397F9A63F3AB21DD8B508B90G486A66B4DF20F3E122940C86A9070C3FC79348DF910CA3AEBE56CD6F4C8DD126C173012BFB0DBE2045B096D3CE47F9CC2905F55ADC73E308
	47E595D31E21B14A6FCA2FBF1F257804AB3D7E94923C7EDC82FD5BDDBCF771A5F9BE63BE4B63DCFC16687CADB27CF74C5E09FC428A980DF77F2AB45433787E7E6E6BC437414755AAF80644F8959E95F5C2DC2D6B048CA104AF32EA8E575757D6B556579FE0B5EBF65CD833B66F7914695DB4425A33EC5FB6E84F62DFAD575E395F0872583E2B2740515A43ED74C7F5E8AB7A19050C3434344BF87F5B4B8C1E1DD7CFC46D5AFC35071FD7C5F44DDCE82F87A86C6177F609BF713B325C07D710624D667713DBBDF336
	229E46E7E5EF2A46A64167ED0C660BB1CE53837DD93D085B97B75EAF23AE3F88836A60497ABB67756399F000987BE3737A11DFD72931BFE7DEFF19E1BE136D29BFF2590A1FDAC582DFDBEC797D43A6CD261D5AF3690B8F1404FFB4DD4AEC6131C84A960E45963005863485B008834CEF2E503C217E9C54326738AF2D21B8BF34176A387284772F156738FFEDCEE08E39D1EF3FC05BF8CF4E5AD389238F6038BB6FE70B6651304A71EF97D8FC1D516FCC37038C242F40D1855086B05E47ED74194BC61FAADF9E3071
	DCA8FD4DC5FD5A7B1E3E74FB20ECDA2765813E300F67CA5F3DFE901E97052B37E66400F4ABF2D8D454A44352379B1ADE96C7EF6FFB0E4F5F663C27BE71BC96677D74BE3138AFBD273CCE71767E741C7295BD6ED4C0DF221F5F53FC071E1F05505E89D06EAEF054GF483785CDC177B957C50EA415A5EA307FBA1118998D2FDA65FCB315B7B4E659B3B39FF2F7E67FFA4EB7F3653721EBF61565E2DE84F1A7FFC20841FCCBE9F3D574A073E97816C674186811AGFA4F6BF7D00AF6CF0866GD51A60755337AAFA91
	A80971949A93886D1DE09C3FA10B01A1C9DEA1400E7DF6C1F14B81491B547204FBE8CC40CF4C464F73BC6169B872661795E9DA8E29374E64B950CBF42E93A09B66961DC75C7B092A85F43775BC538123734CB466E429F4043D2BE3B17DCBC99E53DB87F8CCFFA6199E53BFB7AF2617CC2747F41C1F6AEF1CCE0F697367BFB5139E5379FC36D71F46D7D3DAAE7AF2640F4442FFE1C74EC2FC6E1F388A9928C7185267BC24623AFA1E455CDA6B4BD511367EDE073EDB9A040121A9E627219851FDB2EE78CF6E489D4A
	42B160B7FFD59ACC7E6FC72DEB0D9FC44DEB0D9FC74DEB7B1FC4973EC778E9547ADB04622871DB84366F77D23BD3418E17822C864887A89C607E79F731F719FF1A78E617EF8AEB9D7CA2CA657403F1EBD955F8666F286662789EF5C2D6D5B9086BF37C5F4469BD019C26C806DB92C79DA95214DFE5C3B57C5D54F78362A4C5B3FFFCB80E351F61CA224ED0AB54DD6C9A7038BA1C3DE3039D837D3D3D6D4E01B1E76F10F3E4D077E712E8DCE0FEF77F006BD2FF1F47EC85B63FB30175D307086F0AF81C0D0BBC4E6E
	7F4A17497E5F375938DF275B514E816DBB3A4154791B326107973C9B31364B871356FB7565E4663D4A25F1CC849DFB12E12DC67DEA926514A7A7A322EE93EB1265BA4E95F14B9189A3G333F3A19AAF88E03DCAA40B200D5G6B818A81F6G58BD5C963B27EB08F5DE4192F6EA0F0AA0716FCE0E4FD84B58B613D9C6570C39BD764F5CEA0F1E99F3FB3CB4E334C7069B5356D5740BB50697E7B017E6F576B9A1A4CCD1DFE55FCBDF1B6E03E76ECFC8D9C4A17BEE4D131DF7BF7B9F39E31D95B6E2F311DF78C5E444
	C54A6212CF75A742E0D9E8A679116B6486B53FFCG1F7ECE7026B1975CF91757323E2A3D40EC306DC2FAAE7875D914F396704BAE70BD5BBDFB48EA4FA0028C8605C0GCE61EB4E108F4F157624AA26E2593AD9DCBF4247E81D2C6FDB338E4D9F791C7F87D0CB8788444A5ACC2290GG30AEGGD0CB818294G94G88G88GD3FBB0B6444A5ACC2290GG30AEGG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG5C90GGGG
**end of data**/
}
}
