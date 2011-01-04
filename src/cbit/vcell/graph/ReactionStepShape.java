package cbit.vcell.graph;
/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;

import cbit.gui.graph.ElipseShape;
import cbit.gui.graph.GraphModel;
import cbit.gui.graph.visualstate.VisualState;
import cbit.gui.graph.visualstate.imp.MutableVisualState;
import cbit.vcell.model.ReactionStep;

public abstract class ReactionStepShape extends ElipseShape {
	ReactionStep reactionStep = null;
	Area icon = null;
	private static boolean bDisplayLabels = false;

	public ReactionStepShape(ReactionStep reactionStep, GraphModel graphModel) {
		super(graphModel);
		this.reactionStep = reactionStep;
		defaultBG = java.awt.Color.lightGray;
		defaultFGselect = java.awt.Color.black;
		backgroundColor = defaultBG;
	}

	@Override
	public VisualState createVisualState() { 
		return new MutableVisualState(this, VisualState.PaintLayer.NODE); 
	}

	public static boolean getDisplayLabels() {
		return bDisplayLabels;
	}

	@Override
	public Object getModelObject() {
		return reactionStep;
	}

	@Override
	public Dimension getPreferedSize(Graphics2D g) {
		getSpaceManager().setSizePreferred(12, 12);
		if(getLabel() != null && getLabel().length() > 0){
			FontMetrics fontMetrics = g.getFontMetrics();
			setLabelSize(fontMetrics.stringWidth(getLabel()), 
					fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent());
		}
		return getSpaceManager().getSizePreferred();
	}

	public ReactionStep getReactionStep() {
		return reactionStep;
	}

	@Override
	public Point getSeparatorDeepCount() {	
		return new Point(0,0);
	}

	@Override
	public final void refreshLayout() {
		int centerX = getSpaceManager().getSize().width/2;
		// position label
		labelPos.x = centerX - getLabelSize().width/2; 
		labelPos.y = 0;
	}

	@Override
	public final void resize(Graphics2D g, Dimension newSize) {
		return;
	}

	public static void setDisplayLabels(boolean argDisplayLabels) {
		bDisplayLabels = argDisplayLabels;
	}
}