/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.actions.graph.manipulations;

/*  ReactionsEntitiesPartAction  --- by Oliver Ruebenacker, UCHC --- January 2008 to January 2009
 *   An EvaluatorManipAction for showing processes, participants and entities (Subprocesses collapsed)
 */

import org.vcell.sybil.actions.ActionSpecs;
import org.vcell.sybil.actions.CoreManager;
import org.vcell.sybil.actions.graph.ModelGraphManager;
import org.vcell.sybil.models.graph.UIGraph;
import org.vcell.sybil.models.graph.UIShape;
import org.vcell.sybil.models.graph.manipulator.categorizer.ReactionsManipulator;

public class ReactionsEntitiesParticipantsAction<S extends UIShape<S>, G extends UIGraph<S, G>> 
extends EvaluatorManipulationAction<S, G, ReactionsManipulator<S, G>> {

	private static final long serialVersionUID = 1096143738102335941L;

	public ReactionsEntitiesParticipantsAction(ActionSpecs newSpecs, CoreManager coreManager,
			ModelGraphManager<S, G> graphManager) {
		super(newSpecs, coreManager, graphManager);
	}

	public ReactionsManipulator<S, G> graphManipulation(G graph) {
		ReactionsManipulator<S, G> manip = new ReactionsManipulator<S, G>(evaluator(graph));
		manip.setWithReactants(true);
		manip.setWithComponents(false);
		manip.setCollapseSubProcesses(true);
		manip.setCollapseParticipants(false);
		return manip;
	}

}
