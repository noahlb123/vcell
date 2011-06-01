/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.sybil.util.category;

/*   CatThreeParams  --- by Oliver Ruebenacker, UCHC --- October 2008 to November 2009
 *   A category with three parameters
 */

public abstract class CategoryThreeParams<C extends Category<C>, P1, P2, P3> extends CategoryTwoParams<C, P1, P2> {
	
	protected P3 p3;
	
	public CategoryThreeParams(Class<?> c, P1 p1, P2 p2, P3 p3) { super(c, p1, p2); this.p3 = p3; }
	
	public P3 p3() { return p3; }
	
	public int hashCode() { 
		int p3HashCode = p3 != null ? p3.hashCode() : 0;
		return super.hashCode() + p3HashCode; 
	}
	
	public boolean equals(Object o) { 
		if(o instanceof CategoryThreeParams<?, ?, ?, ?>) {
			Object c2 = ((CategoryThreeParams<?, ?, ?, ?>) o).c();
			boolean aEqualsA2 = (c == null && c2 == null) || (c != null && c.equals(c2));
			Object p12 = ((CategoryThreeParams<?, ?, ?, ?>) o).p1();
			boolean p1EqualsP12 = (p1 == null && p12 == null) || (p1 != null && p1.equals(p12));
			Object p22 = ((CategoryThreeParams<?, ?, ?, ?>) o).p2();
			boolean p2EqualsP22 = (p2 == null && p22 == null) || (p2 != null && p2.equals(p22));
			Object p32 = ((CategoryThreeParams<?, ?, ?, ?>) o).p3();
			boolean p3EqualsP32 = (p3 == null && p32 == null) || (p3 != null && p3.equals(p32));
			return aEqualsA2 && p1EqualsP12 && p2EqualsP22 && p3EqualsP32;
		}
		return false;
	}
	
	public String toString() { 
		String cString = c.toString();
		String p1String = p1 != null ? p1.toString() : "null";
		String p2String = p2 != null ? p2.toString() : "null";
		String p3String = p3 != null ? p3.toString() : "null";
		return "(" + cString + ", " + p1String + ", " + p2String + ", " + p3String + ")"; 
	}

}
