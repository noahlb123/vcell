package cbit.vcell.client.desktop.biomodel;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

import org.vcell.model.rbm.ComponentStatePattern;
import org.vcell.model.rbm.MolecularComponentPattern;
import org.vcell.model.rbm.MolecularTypePattern;
import org.vcell.util.Displayable;
import org.vcell.util.gui.VCellIcons;

import cbit.vcell.client.desktop.biomodel.RbmDefaultTreeModel.BondLocal;
import cbit.vcell.client.desktop.biomodel.RbmDefaultTreeModel.SpeciesPatternLocal;
import cbit.vcell.client.desktop.biomodel.RbmDefaultTreeModel.StateLocal;
import cbit.vcell.desktop.BioModelNode;
import cbit.vcell.graph.AbstractComponentShape;
import cbit.vcell.model.RbmObservable;

@SuppressWarnings("serial")
public class RbmObservableTreeCellRenderer extends RbmTreeCellRenderer {

	public RbmObservableTreeCellRenderer() {
		super();
		setBorder(new EmptyBorder(0, 2, 0, 0));		
	}
	
	@Override
	public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {	
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setBorder(null);
		if (value instanceof BioModelNode) {
			BioModelNode node = (BioModelNode)value;
			Object userObject = node.getUserObject();
			String text = null;
			Icon icon = null;
			String toolTip = null;
			if (userObject instanceof RbmObservable) {
				RbmObservable ob = (RbmObservable) userObject;
				text = toHtml(ob);
				toolTip = toHtmlWithTip(ob);
				icon = VCellIcons.rbmObservableIcon;
			} else if (userObject instanceof SpeciesPatternLocal) {
				SpeciesPatternLocal spl = (SpeciesPatternLocal) userObject;
				text = toHtml(spl,true);
				toolTip = toHtmlWithTip(spl,true);
				icon = VCellIcons.rbmProductIcon;
			} else if (userObject instanceof MolecularTypePattern) {
				MolecularTypePattern molecularTypePattern = (MolecularTypePattern) userObject;
				text = toHtml(molecularTypePattern, true);
				toolTip = toHtmlWithTip(molecularTypePattern, true);
				icon = VCellIcons.rbmMolecularTypeSimpleIcon;
			} else if (userObject instanceof MolecularComponentPattern) {
				MolecularComponentPattern mcp = (MolecularComponentPattern) userObject;
				text = toHtml(mcp, true);
				toolTip = toHtmlWithTip(mcp, true);
				icon = VCellIcons.rbmComponentGrayIcon;
				if(mcp.getMolecularComponent().getComponentStateDefinitions().size() > 0) {
					icon = VCellIcons.rbmComponentGrayStateIcon;
				}
				if(mcp.isbVisible()) {
					icon = VCellIcons.rbmComponentGreenIcon;
					if(mcp.getMolecularComponent().getComponentStateDefinitions().size() > 0) {
						icon = VCellIcons.rbmComponentGreenStateIcon;
					}
				}
				ComponentStatePattern csp = mcp.getComponentStatePattern();
				if(csp != null && !csp.isAny()) {
					icon = VCellIcons.rbmComponentGreenIcon;
					if(mcp.getMolecularComponent().getComponentStateDefinitions().size() > 0) {
						icon = VCellIcons.rbmComponentGreenStateIcon;
					}
				}
				BioModelNode parent = (BioModelNode) ((BioModelNode) value).getParent().getParent().getParent();
				if(parent == null) {
					icon = VCellIcons.rbmComponentErrorIcon;
					return this;
				}
				Object parentObject = parent.getUserObject();
				if(!(parentObject instanceof RbmObservable)) {
					icon = VCellIcons.rbmComponentErrorIcon;
					return this;
				}
				if(AbstractComponentShape.hasIssues((RbmObservable)parentObject, mcp, mcp.getMolecularComponent())) {
					icon = VCellIcons.rbmComponentErrorIcon;
				}
			} else if (userObject instanceof StateLocal) {
				StateLocal sl = (StateLocal) userObject;
				text = toHtml(sl, true);
				toolTip = toHtmlWithTip(sl,true);
				icon = VCellIcons.rbmComponentStateIcon;
			} else if (userObject instanceof BondLocal) {
				BondLocal bl = (BondLocal)userObject;
				text = toHtml(bl,sel);
				toolTip = toHtmlWithTip(bl,true);
				icon = VCellIcons.rbmBondIcon;
			} else {
				if(userObject != null) {
					System.out.println(userObject.toString());
					text = userObject.toString();
				} else {
					text = "null user object";
				}
			}
			setText(text);
			setIcon(icon);
			setToolTipText(toolTip == null ? text : toolTip);
		}
		return this;
	}
	

}
