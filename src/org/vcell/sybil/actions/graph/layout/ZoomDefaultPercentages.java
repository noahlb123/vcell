/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.actions.graph.layout;

import java.util.HashSet;
import java.util.Set;

public class ZoomDefaultPercentages {

	private static final int[] percentages = {10, 20, 30, 40, 50, 64, 80, 100, 125, 156, 195};

	static public Set<Integer> percentages() {
		Set<Integer> percentageSet = new HashSet<Integer>();
		for(int percentage : percentages) { percentageSet.add(new Integer(percentage)); }
		return percentageSet;
	}
	
	static public int nextLowerThan(int someInt) {
		int nextLower = 10;
		for(int percentage : percentages) {
			if(percentage < someInt && percentage > nextLower) { nextLower = percentage; }
		}
		return nextLower;
	}

	static public int nextHigherThan(int someInt) {
		int nextHigher = 195;
		for(int percentage : percentages) {
			if(percentage > someInt && percentage < nextHigher) { nextHigher = percentage; }
		}
		return nextHigher;
	}

}
