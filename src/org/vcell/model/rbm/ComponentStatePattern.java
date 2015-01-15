package org.vcell.model.rbm;

import java.util.List;

import org.vcell.util.Compare;
import org.vcell.util.Issue;
import org.vcell.util.IssueContext;
import org.vcell.util.Matchable;

public class ComponentStatePattern extends RbmElementAbstract implements Matchable {
	
	private final ComponentStateDefinition componentStateDefinition;
	private final boolean bAny;
	
	public static final String strAny = "not specified";
	
	/* Example:

	S(tyr~Y)	// Y is the component state definition, the pattern points to it
	S(tyr)		// the pattern is in any state (no specified state chosen)

	 */
	
	public ComponentStatePattern() {
		this.bAny = true;
		this.componentStateDefinition = null;
	}
	public ComponentStatePattern(ComponentStateDefinition componentStateDefinition) {
		this.bAny = false;
		this.componentStateDefinition = componentStateDefinition;
	}
	
	public boolean isAny(){
		return bAny;
	}
	
	@Override
	public boolean compareEqual(Matchable aThat) {
		if (this == aThat) {
			return true;
		}
		if (!(aThat instanceof ComponentStatePattern)) {
			return false;
		}
		ComponentStatePattern that = (ComponentStatePattern)aThat;

		if(!(bAny == that.bAny)) {
			return false;
		}
		if (!Compare.isEqual(componentStateDefinition, that.componentStateDefinition)){
			return false;
		}
		return true;
	}

	@Override
	public void gatherIssues(IssueContext issueContext, List<Issue> issueList) {

	}

	public ComponentStateDefinition getComponentStateDefinition() {
		return componentStateDefinition;
	}

//	public void setComponentStateDefinition(ComponentStateDefinition componentStateDefinition) {
//		this.componentStateDefinition = componentStateDefinition;
//	}
}
