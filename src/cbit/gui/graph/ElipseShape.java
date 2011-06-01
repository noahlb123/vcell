/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.gui.graph;
import java.awt.*;
/**
 * This Class was generated by a SmartGuide.
 * 
 */
public abstract class ElipseShape extends Shape {
	int index_ = -1;

/**
 * This method was created by a SmartGuide.
 * @param feature cbit.vcell.model.Feature
 */
public ElipseShape (GraphModel graphModel) {
	super(graphModel);
}


/**
 * This method was created by a SmartGuide.
 * @return int
 * @param g java.awt.Graphics
 */
public Dimension getPreferedSize(java.awt.Graphics2D g) {
	java.awt.FontMetrics fm = g.getFontMetrics();
	labelSize.height = fm.getMaxAscent() + fm.getMaxDescent();
	labelSize.width = fm.stringWidth(getLabel());
	preferedSize.height = labelSize.height + 10;
	preferedSize.width = labelSize.width + 10;
	return preferedSize;
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 * @param pick java.awt.Point
 */
final double getRadius ( Point pick ) {
	int centerX = screenPos.x+screenSize.width/2;
	int centerY = screenPos.y+screenSize.height/2;
	double radiusX = pick.x-centerX;
	double radiusY = pick.y-centerY;
	double b = screenSize.height/2;
	double a = screenSize.width/2;
   double radius = radiusX*radiusX/(a*a) + radiusY*radiusY/(b*b);

   return radius;
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 * @param p java.awt.Point
 */
public final boolean isInside (Point p ) {
	if (getRadius(p) < 1.0){
		return true;
	}else{
		return false;
	}
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 * @param p java.awt.Point
 */
public final boolean isOnBorder(Point p) {
	double radius = getRadius(p);
	if (radius >= 0.9 && radius <= 1.1){
		return true;
	}else{
		return true;
	}
}


/**
 * This method was created by a SmartGuide.
 * @return int
 * @param g java.awt.Graphics
 */
public void layout() throws LayoutException {

	if (screenSize.width<=labelSize.width ||
		 screenSize.height<=labelSize.height){
		 throw new LayoutException("screen size smaller than label");
	} 
	//
	// this is like a row/column layout  (1 column)
	//
	int centerX = screenSize.width/2;
	int centerY = screenSize.height/2;
	
	//
	// position label
	//
	labelPos.x = centerX - labelSize.width/2;
	labelPos.y = centerY - labelSize.height/2;
}


/**
 * This method was created by a SmartGuide.
 * @param g java.awt.Graphics
 */
public void paint ( java.awt.Graphics2D g2D, int parentOffsetX, int parentOffsetY ) {

   int absPosX = screenPos.x + parentOffsetX;
   int absPosY = screenPos.y + parentOffsetY;
	//
	// draw elipse
	//
	g2D.setColor(backgroundColor);
	g2D.fillOval(absPosX,absPosY,screenSize.width,screenSize.height);
	g2D.setColor(forgroundColor);
	g2D.drawOval(absPosX,absPosY,screenSize.width,screenSize.height);

	//
	// draw label
	//
	java.awt.FontMetrics fm = g2D.getFontMetrics();
	int textX = absPosX  + screenSize.width/2 - fm.stringWidth(getLabel())/2;
	int textY = absPosY + 5 + fm.getMaxAscent();
	if (getLabel()!=null && getLabel().length()>0){
		g2D.drawString(getLabel(),textX,textY);
	}
	return;
}
}
