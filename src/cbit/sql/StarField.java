/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.sql;

/**
 * This type was created in VisualAge.
 */
public class StarField extends Field {
/**
 * StarField constructor comment.
 * @param columnName java.lang.String
 * @param sqlType java.lang.String
 * @param sqlConstraints java.lang.String
 */
public StarField(Table argTable) {
	super("*", null, null);
	setTableName(argTable.getTableName());
}
}
