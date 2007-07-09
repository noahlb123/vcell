package cbit.vcell.model.gui;

/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import cbit.vcell.model.*;
/**
 * This type was created in VisualAge.
 */
public class ReactionCartoonEditorDialog extends org.vcell.util.gui.JInternalFrameEnhanced {
	private javax.swing.JPanel ivjContentsPane = null;
	private cbit.vcell.graph.ReactionCartoonEditorPanel ivjReactionCartoonEditorPanel = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ReactionCartoonEditorDialog() {
	super();
	initialize();
}
/**
 * Insert the method's description here.
 * Creation date: (6/14/2005 4:16:19 PM)
 */
public void cleanupOnClose() {

	getReactionCartoonEditorPanel().cleanupOnClose();
}
/**
 * Return the ContentsPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new javax.swing.JPanel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(new java.awt.BorderLayout());
			getContentsPane().add(getReactionCartoonEditorPanel(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjContentsPane;
}
/**
 * Return the ReactionCartoonEditorPanel property value.
 * @return cbit.vcell.graph.ReactionCartoonEditorPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.graph.ReactionCartoonEditorPanel getReactionCartoonEditorPanel() {
	if (ivjReactionCartoonEditorPanel == null) {
		try {
			ivjReactionCartoonEditorPanel = new cbit.vcell.graph.ReactionCartoonEditorPanel();
			ivjReactionCartoonEditorPanel.setName("ReactionCartoonEditorPanel");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjReactionCartoonEditorPanel;
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
 * This method was created in VisualAge.
 * @param model cbit.vcell.model.Model
 * @param structure cbit.vcell.model.Structure
 */
public void init(Model model, Structure structure,cbit.vcell.client.database.DocumentManager documentManager) {
	getReactionCartoonEditorPanel().setModel(model);
	getReactionCartoonEditorPanel().setStructure(structure);
	getReactionCartoonEditorPanel().setDocumentManager(documentManager);
	setTitle("Reactions for "+structure.getName());
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("ReactionCartoonEditorDialog");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setClosable(true);
		setSize(542, 495);
		setResizable(true);
		setContentPane(getContentsPane());
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
		ReactionCartoonEditorDialog aReactionCartoonEditorDialog;
		aReactionCartoonEditorDialog = new ReactionCartoonEditorDialog();
		frame.setContentPane(aReactionCartoonEditorDialog);
		frame.setSize(aReactionCartoonEditorDialog.getSize());
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
		System.err.println("Exception occurred in main() of javax.swing.JInternalFrame");
		exception.printStackTrace(System.out);
	}
}
/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G550171B4GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E135D88DD0D45795C6CA9411CE4814245831AD0DB603AD6D102919921BE6CC0BB319EA9B5B9A47F11816E9CDA6E9528C13180EF51C76AE8BC28409C65A58E4659FEB0C2178D3CB2926A8984500D0C1C29A92F9300FE57531EFFD6FAD3B4B7253F36EFD776DE3F98BCD8619B95C3D675CFB6E3D671E731DF39FA92BC8AD30651591E2ABA07A5FBFEC04A41693725C5225AFF2DE12273718187E2A008E105FFA
	7321CD85D9A16D6FFC33845AD220CC60E11BAF77966FA77B9D07F4FE364E67E44BA36499BA66ED07C367DD067EG50AA20CC190D5F846DABF4653B1A9A75F17D7724119DBFD27276219E13CECE8DDEB637C7EFAB49AE5BEEAF2E5563A85167A4C2BF8B888FEFA755A74FC0FB366B166B5D21C137D6BFB6A4CAD27D286C9425FA174F5D54F4CD9406B4376CE99194CD16BD971DEECDD6DA5D02A43B88895CC65DAB9CC5D497A14D504FD710F7298E57FB847A1B011EB77825C710FF99FE8FG8594FE46F3FFBD2B1F
	21AF74DD325D357E15D7231CB1C9E1E74BD442E72CBCB6E87F8B3D0FF794347B8E109DD2FB0B0F83F581F90112005E8459C1F27A76B1E84F8FEAAD7268286CB96F75DE101CCA871E99FEB7B501C467DD9307E445C9883B6B7FBA0EDB6CE7AEE273C6BE75BCE61334D37C5E532FFD1BE4FF673E3CC28B1BE427057252D4E60BF5EA58A6EC6D2FC5DDBBC71D6917B3577E001857A64F1EACAC30D85B3165E4618EDDCF1196DE3BD1433B7E9E750123705B267B87632F277C89931F79E88765E3BF91E4399A770D3ED2
	3E17767C7B497E93DF3434C34A8F9273FAFCEC8F12AFF2AF31546FD2FCECCD1B3117E5143F4A44E7FBA9BB4263A583E4E5E33D454C3F4EE898338168A7832583E581ED87A2FE986767BEB6F97BEF7F270FF5C1E0DD7039A5272890B6770C72E7E845002688ED9ED593BCC3E22F203805C1C9D409BE665F8A5AE0F0463CD66A7BC33046A551A3AA8206ADE897C6C5CDD4D4B626C3B900E39411CAFB5C23A2ACB06AA5DC6FF565A05ACFD0356BDE2720096191CC7E3102F7B2A67402C6D0G3F19DD06FCE82F53F07E
	F620FE438EDB293E77C545A3CACDCD6D32FC4B676D411310F4BF5AB913E20797FE477B390DEF0CA1AF977AC5812E67D575B5131ED6B745A4C1897298FB2C8EF1599160BE73C29F771956FD5FA2471F3C5752E71240FFDB834CD77A83113133016A44FD31FEF7DD1FC53CB88256F174E9092DA1D92EE1DFG7D2546DD7D580FF795877BCE85DAE758780ADA49FC7602200A5CCBBB95F0B0519936B85BE77C91E26C799E536FB70ED86DFF21082DF16A0E2E41EC512CE17E9147C40F56AAFAC50F93FE283A6C1206BE
	26476730EC4C6920FCAF44F38B8410EA703BB50CB9C153947720CF933BC5CDF3FBDCE179C7F4AD55B7688DC381DFEBD0C35C157D904F46F8170631B0A4FBBCA23D94E30AAEF753D894A5F194B65DA27BBC9A634BBE7467DBE2D04782553707E0ACCBBE4495D68FE488D874F89577A8DCB5C293E98CE21CBEE154B3C8DB41E7302EA981D9E510471C3F0647DC6D78EA326E06B74FAAE724FC7D6E3C18A00B1964E0B866581E1CE356FB3FC9B1C1672BEC2F03FFC759DC858A7377BDC154398656FA9CA8A7487D7D69
	B114F3CC519D3DC516A4E6D6435EBF776F477B91EF7BC400E2842F778D9BEE733F96A16B556D49643F7223BDA5936E313DF73A6A08396E22355615B68FA035C7105EC3503E6899C1680772C32F337EF998ED00365E99647554268ABCDB43F8EF71E52001F1FE97BF5D476B2964DBE9A47B59930551EA4640B83B039563613BB81EFBFE8F33DD57B82E198D326747395DBE8E18B17D3C2A4AC3EE1ACE74B317844DF745F88702FB219DF6AB2A568641946076F9BD087B45FC23337598FF77B056ADA44476D4961A09
	31C7A37AE7214F72792ECA34C7D7A83266482FC27EC0A8326658D241EB8EAF48B607F88E7AFC887D3DF122373887C8820A83DA8514B5416D705560A9B2CF0E67975ABDA338F92E9D3733F8547B9F0E9770F8041665476B744EF3E04D7CC928B7A8AF1D728EC17F38416BAEC7DEB3747B8C1ED6013C8968A7CF729CC62A9083520C7EDE5ADFE77433AB968ADBF18DEB7CFEF94156304A0B4C16770522637717C356783DB2E40D5F2BC387989FF4182BB5AEFFB0B4B73EBF9C3242F7BB15C9077F842DD39C96FC92AC
	AB2BE227D7AF5D006F1842FBDCC26F9BFDE3BB58144D7BA1658D41848495B6EE5BE17E8E9B18626F3B87A97E8C831E40163A3D6290491F4631B74BB91EFBE1EC9C50AA20AC206DD3FC0DE595E88FC5D45D63943978FED6D670F55A2779587FB8B10EDAE470F7B0F4176091D95E78D715B9EF74CF33BAAF865AE4E36ED7E82C5F944604C03D6A07FD56FF6076B8E5BFE8436AD7D36BDB5B3A3BBA3BAF5E683CFA23259DFEB00CCB751B5F450DFBE3AD738DD27A347EBE525B44BFAE373135BB4B517FB545A7B20CCD
	22F104BAF64258069864BDDADE9661F33F04FF7550B2F96E8CB97A42DB44AC2F2EB04BF16F67724D721E99728253FCF64E8DF59C4BF7EF48A052C3F1D15F7F01989D4B753672A74BED71A9DDFB58D957D67033B25FF869AD3C9B1A03CC4607B40457A7B1FB28D55C77B647701C53DC03F7DD3228373896686CA20E1997A6F1EC8F743D0BB8C636CF3C8DED2FF0D3C7C7161EA9BC8233CB11415B35A063524C29E3443D651F6D4D7CD8A137551A355FA0190BDB730A2358AAE99133D1065EE6EEDCEE43161DA5FD8A
	ED1383325DC0153110DF005A011C317CECDF1840BD824A63764FEB1AB0B4C2B39E877FDE37688F33B178585982D511E78B440631B2B1F6B6EE329C36065ABDB5B6B237BDCA79993111396D3D9A1E5B56012C76F3FC5F17A6D12701D74C6CBAD6DD1DC4DFF4FB5C9AD4D0907788C45DB05063E2F6F9F19A657120EB955063C059C039066E1FCDD6B31BD858632253A57218666BBDB4959D279F191A7F9D3CFE8A6B0AC1D95364D1544F799B2650561A6C25CC465BB4C5714C6D9A5158D01D3FE58A7DD69207A91B65
	4E3B295F1744415D4771FCDADA43F3ECEB9C4F17EBEBB026C60CFE966DA7997D761A05FC2B60BA3373293820EBC466D4E623DD53516F49C6663F273D045604E05F1E20D7545F74845F9F727030AA9A7ED0C6769B3CB635CBD65D68018C3F9E284271129014FDB03E72AE3C136F9B79E58D58E6A3508E20A210E1CDB3C24754559C1A673C87C93E708609721620E8C874F7504BB3F4E5686B64C21B3F983002465DD3D57C5B1183F8BD0BF9AEFE3A9E4FE860ED3D60571A1AAE48786EE8E707E2187BCEB54FC1090B
	B966FED40DBA33201F8D34E3B12F7383C7B85E25BFF0BF597414F51D1F53B319D7320479CE4312F01DFF7A58201D6939F90B6BF1FCF3A515C54BB112BEBF49240767ADBBB957F8024C7E1E6CD2846FC814EF493AF97959EBF9ED31E989BFFBF2AD1E3DA89EE29128B11E4756F333F0F4BEE273FE7F99671D2C7E347398BEBFC16F2EABBE725B63D64A6F0F0F7C76A8D4F37F99815946F81EDF375950779A16C29E82F282CDG2DG4ADC4A71F7B5E9A0733CC9FAE4EFBB8432E4AAE5197E53F6543F91F4BD83D412
	GB983289128AB016BFF09601BA7DABE3FA64B9A5EA75601025BA3AA946E957829F19B564EE5C3E7C2345CD7C43F8D8792A2EDF800726392A2ED7825DAEE43A4106DC860BE73A426A7FDAF3FB07DF6D55F796FD6B1F7F48DF6BF3F36C547695F582C5FBD39B66BF74F6FECF33FEB7EE00B7E5D2AA0EF76F72BF37668FBEB354F1FC3BA6C56393E5BBEBB5777592DF37DA77659393E2D56F8BBAF63FED2DA97760D57CD3FD3EB6F3C1FBCFCC757C8D906E79E282D1CE3CC5CB29607ACB7CE56E25EED9171DB862F33
	7D22B9DFB8AA1A7350D72C72C50E2E2BA4F1E63EE018536842FDB4032C9FA886A885A89DA88BE8ABD0EEA2471E976CC7C9F46CE109B48CB8ECCE0BAB3A4FDDF14D6FF35DAEEB1F7B50B55B67BEF1D97B1C53E576B97D9B2D2E9777FCD598B5744AAE4C3B2C466990BC020B464F7FGD0CB87884F5EBA1DB58BGG349EGGD0CB818294G94G88G88G550171B44F5EBA1DB58BGG349EGG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGG
	GEF8BGGGG
**end of data**/
}
}
