/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.models.bpimport;

/*   StageModelsBuilder  --- by Oliver Ruebenacker, UCHC --- July 2008 to November 2009
 *   Building a model of a model of SBPAX reactions from EdgeSets
 */

import java.util.Set;

import org.vcell.sybil.models.sbbox.SBBox;
import org.vcell.sybil.models.views.SBWorkView;

public class StageModelsBuilder {
	
	static public void build(SBWorkView view) {
		SBBox box = view.box();
		box.performSYBREAMReasoning();
		if(view.systemModel() == null) {
			view.setSystemModel(box.factories().systemModelFactory().createWithDefaultLabel());			
		}
		for(SBBox.Process process : view.processes()) {
			SBBox.MutableProcessModel processModel = box.factories().processModelFactory().createAnonymous();
			processModel.setProcess(process);
			view.systemModel().addProcessModel(processModel);
			Set<SBBox.Participant> participants = process.participants();
			for(SBBox.Participant participant : participants) {
				SBBox.Species species = participant.species();
				if(species != null) {
					view.systemModel().addSpecies(species);
					SBBox.Substance substance = species.substance();
					if(substance != null) { view.systemModel().addSubstance(substance); }
				}
			}
		}
	}
	
}
