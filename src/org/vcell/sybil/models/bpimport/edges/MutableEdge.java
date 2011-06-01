/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.models.bpimport.edges;

/*   MutableEdge  --- by Oliver Ruebenacker, UCHC --- July to November 2009
 *   A mutable process edge
 */

import org.vcell.sybil.models.sbbox.SBBox;
import org.vcell.sybil.models.sbbox.SBBox.Location;
import org.vcell.sybil.models.sbbox.SBBox.MutableLocation;
import org.vcell.sybil.models.sbbox.SBBox.MutableParticipant;
import org.vcell.sybil.models.sbbox.SBBox.MutableProcess;
import org.vcell.sybil.models.sbbox.SBBox.MutableSpecies;
import org.vcell.sybil.models.sbbox.SBBox.MutableStoichiometry;
import org.vcell.sybil.models.sbbox.SBBox.MutableSubstance;
import org.vcell.sybil.models.sbbox.SBBox.MutableRDFType;
import org.vcell.sybil.models.sbbox.SBBox.Participant;
import org.vcell.sybil.models.sbbox.SBBox.Process;
import org.vcell.sybil.models.sbbox.SBBox.Species;
import org.vcell.sybil.models.sbbox.SBBox.Stoichiometry;
import org.vcell.sybil.models.sbbox.SBBox.Substance;
import org.vcell.sybil.models.sbbox.SBBox.RDFType;

import com.hp.hpl.jena.rdf.model.Resource;

public class MutableEdge implements ProcessEdge {

	protected EdgeSBTray tray;
	
	protected MutableProcess process;
	protected MutableParticipant participant;
	protected MutableSpecies species;
	protected MutableSubstance entity;
	protected MutableRDFType entityType;
	protected MutableSubstance substance;
	protected MutableLocation location;
	protected MutableStoichiometry stoichiometry;
	protected float sc = (float) 1.0;
	
	public MutableEdge(EdgeSBTray box) { this.tray = box; }
	
	public MutableEdge(EdgeSBTray box, ProcessEdge edge) {
		this(box);
		if(edge.process() != null) { setProcess(edge.process()); }
		if(edge.participant() != null) { setParticipant(edge.participant()); }
		if(edge.species() != null) { setSpecies(edge.species()); }
		if(edge.entity() != null) { setEntity(edge.entity()); }
		if(edge.entityType() != null) { setEntityType(edge.entityType()); }
		if(edge.substance() != null) { setSubstance(edge.substance()); }
		if(edge.location() != null) { setLocation(edge.location()); }
		if(edge.stoichiometry() != null) { setStoichiometry(edge.stoichiometry()); }
		setSC(edge.stoichiometricCoeff());
	}

	public void setProcess(Process process) { 
		this.process = tray.box().factories().processFactory().create(process.resource()); 
	}
	
	public void setParticipant(Participant participant) { 
		this.participant = tray.box().factories().participantFactory().create(participant.resource()); 
	}
	
	public void setSpecies(Species species) { 
		this.species = tray.box().factories().speciesFactory().create(species.resource()); 
	}
	
	public void setEntity(Substance entity) { 
		this.entity = tray.box().factories().substanceFactory().create(entity);
	}
	
	public void setEntityType(RDFType entityType) { 
		this.entityType = tray.box().factories().typeFactory().create(entityType);
	}
	
	public void setSubstance(Substance substance) { 
		this.substance = tray.box().factories().substanceFactory().create(substance); 
	}
	
	public void setLocation(Location location) { 
		this.location = tray.box().factories().locationFactory().create(location.resource()); 
	}
	
	public void setStoichiometry(Stoichiometry stoichiometry) { 
		this.stoichiometry = tray.box().factories().stoichiometryFactory().create(stoichiometry.resource()); 
	}
	
	public void setSC(float sc) { this.sc = sc; }
	
	public MutableProcess process() { return process; }
	public MutableParticipant participant() { return participant; }
	public MutableSpecies species() { return species; }
	public MutableSubstance entity() { return entity; }
	public MutableRDFType entityType() { return entityType; }
	public MutableSubstance substance() { return substance; }
	public MutableLocation location() { return location; }
	public MutableStoichiometry stoichiometry() { return stoichiometry; }
	public float stoichiometricCoeff() { return sc; }
	
	public Resource resource(SBBox.NamedThing view) { return view != null ? view.resource() : null; }
	
}
