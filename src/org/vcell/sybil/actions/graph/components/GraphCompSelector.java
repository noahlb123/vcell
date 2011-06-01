/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.actions.graph.components;

/*   GraphCompHack  --- by Oliver Ruebenacker, UCHC --- February 2009 to March 2010
 *   Quick hack to select all shapes of a certain class.
 *   Only a temporary solution, because actions should be unaware of GUI
 */

import java.util.Iterator;

import org.vcell.sybil.gui.graph.nodes.NodeShape;
import org.vcell.sybil.models.graph.UIGraph;
import org.vcell.sybil.models.graph.UIShape;

public class GraphCompSelector {

	public static <S extends UIShape<S>, G extends UIGraph<S, G>>
	void select(UIGraph<S,G> graph, Class<? extends NodeShape> nodeClass) {
		Iterator<S> shapeIter = graph.shapeIter();
		while(shapeIter.hasNext()) {
			S shape = shapeIter.next();
			// TODO sub classes?
			if(shape.getClass().equals(nodeClass)) { graph.select(shape); } 
		}
	}

}
