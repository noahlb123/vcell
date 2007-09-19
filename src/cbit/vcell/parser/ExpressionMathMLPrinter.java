package cbit.vcell.parser;

/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import java.awt.*;
import java.util.*;

import org.jdom.Element;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class ExpressionMathMLPrinter {
	private SimpleNode rootNode = null;
	enum MathType {REAL, BOOLEAN};
/**
 * This method was created by a SmartGuide.
 * @param font java.awt.Font
 */
ExpressionMathMLPrinter (SimpleNode rootNode) {
	this.rootNode = rootNode;
}
/**
 * Insert the method's description here.
 * Creation date: (2/8/2002 5:51:09 PM)
 * @return java.lang.String
 */
String getMathML() throws ExpressionException, java.io.IOException {
	org.jdom.output.XMLOutputter xmlwriter = new org.jdom.output.XMLOutputter();
	return xmlwriter.outputString(getMathML(rootNode, MathType.REAL));
}

/**
 * castChild :
 * @param element
 * @param outputType - returns the element converted to real if needed if TRUE; converts to boolean if FALSE
 * @param inputType	- is the input element type real - TRUE; if boolean - FALSE
 * @return
 */
private Element castChild(Element element, MathType outputType, MathType inputType) {
	Element castedElement = null;
	if (outputType == inputType) {
		castedElement = element;
	} else if (inputType.equals(MathType.REAL) && outputType.equals(MathType.BOOLEAN)) {
		// convert a REAL to BOOLEAN piecewise.
		// <piecewise>                           
		//    <piece>                            
		//       <cn> 1 < /cn>                        
		//       <apply>                                 
		//          <neq/>
		//  		<cn> 0 </cn>
		//	  		<apply>
		//				realElementCondn - 'element'
		//			</apply>
		//       </apply>                            
		//    </piece>                                  
		//    <otherwise>                                    
		//       <cn> 0.0 </cn>
		//    </othewise>                       
		// </piecewise>                             

		// Construct the piecewise element : create piece and otherwise separately and add.
		Element piecewiseElement = new org.jdom.Element(MathMLTags.PIECEWISE);
		// construct the piece element :  create const (1.0) element and apply element and add to piece - refer to pseudocode above.
		Element pieceElement  = new org.jdom.Element(MathMLTags.PIECE);
		Element constElement_1 = new org.jdom.Element(MathMLTags.CONSTANT);
		constElement_1.addContent("1.0");
		Element applyElement = new org.jdom.Element(MathMLTags.APPLY);	
		Element neqElement = new org.jdom.Element(MathMLTags.NOT_EQUAL);
		Element constElement_0 = new org.jdom.Element(MathMLTags.CONSTANT);
		constElement_0.addContent("0.0");
		applyElement.addContent(neqElement);
		applyElement.addContent(constElement_0);
		applyElement.addContent(element);
		pieceElement.addContent(constElement_1);
		pieceElement.addContent(applyElement);
		// construct the otherwise element : add
		Element otherwiseElement = new org.jdom.Element(MathMLTags.OTHERWISE);
		Element constElement = new org.jdom.Element(MathMLTags.CONSTANT);
		constElement.addContent("0.0");
		otherwiseElement.addContent(constElement);
		// Now put together the piecewise element with the piece and otherwise. 
		piecewiseElement.addContent(pieceElement);
		piecewiseElement.addContent(otherwiseElement);
		castedElement = piecewiseElement;
	} else if (inputType.equals(MathType.BOOLEAN) && outputType.equals(MathType.REAL)) {
		// convert a BOOLEAN to REAL piecewise.
		// <piecewise>                           
		//    <piece>                            
		//       <cn> 1 < /cn>                        
		//       <apply>                                 
		//         ...booleanElementCondn - - 'element'                  
		//       </apply>                            
		//    </piece>                                  
		//    <otherwise>                                    
		//       <cn> 0.0 </cn>
		//    </othewise>                       
		// </piecewise>                             

		// Construct the piecewise element : create piece and otherwise separately and add.
		Element piecewiseElement = new org.jdom.Element(MathMLTags.PIECEWISE);
		// construct the piece element :  create const (1.0) element and apply element (incoming argument) and add to piece - refer to pseudocode above.
		Element pieceElement  = new org.jdom.Element(MathMLTags.PIECE);
		Element constElement_1 = new org.jdom.Element(MathMLTags.CONSTANT);
		constElement_1.addContent("1.0");
		Element applyElement = element;
		pieceElement.addContent(constElement_1);
		pieceElement.addContent(applyElement);
		// construct the otherwise element : add
		Element otherwiseElement = new org.jdom.Element(MathMLTags.OTHERWISE);
		Element constElement_0 = new org.jdom.Element(MathMLTags.CONSTANT);
		constElement_0.addContent("0.0");
		otherwiseElement.addContent(constElement_0);
		// Now put together the piecewise element with the piece and otherwise. 
		piecewiseElement.addContent(pieceElement);
		piecewiseElement.addContent(otherwiseElement);
		castedElement = piecewiseElement;
	}
	
	return castedElement;
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2002 5:53:06 PM)
 * @return java.lang.String
 */
public static String getMathML(Expression exp) throws ExpressionException, java.io.IOException {
	ExpressionMathMLPrinter mathMLPrinter = new ExpressionMathMLPrinter(exp.getRootNode());
	return mathMLPrinter.getMathML();
}
/**
 * draw the expression with y at the center and x at the left
 * @param g java.awt.Graphics
 * @param node cbit.vcell.parser.SimpleNode
 */
private org.jdom.Element getMathML(Node node, MathType desiredMathType) throws ExpressionException {
	//
	// Equals
	//
	if (node instanceof ASTRelationalNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(((ASTRelationalNode)node).getMathMLElementTag()));
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
		applyNode.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
		return castChild(applyNode, desiredMathType, MathType.BOOLEAN);
	}else if (node instanceof ASTAndNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.AND));
		for (int i = 0; i < node.jjtGetNumChildren(); i++){
			applyNode.addContent(getMathML(node.jjtGetChild(i), MathType.BOOLEAN));	
		}
		return castChild(applyNode, desiredMathType, MathType.BOOLEAN);
	}else if (node instanceof ASTOrNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.OR));
		for (int i = 0; i < node.jjtGetNumChildren(); i++){
			applyNode.addContent(getMathML(node.jjtGetChild(i), MathType.BOOLEAN));	
		}
		return castChild(applyNode, desiredMathType, MathType.BOOLEAN);
	}else if (node instanceof ASTNotNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.NOT));
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.BOOLEAN));
		return castChild(applyNode, desiredMathType, MathType.BOOLEAN);
	}else if (node instanceof ASTPowerNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.POWER));
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
		applyNode.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
		return castChild(applyNode, desiredMathType, MathType.REAL);
	}else if (node instanceof DerivativeNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.DIFFERENTIAL));
		org.jdom.Element bvarNode = new org.jdom.Element(MathMLTags.BVAR);
		org.jdom.Element idNode = new org.jdom.Element(MathMLTags.IDENTIFIER);
		idNode.setText(((DerivativeNode)node).independentVar);
		bvarNode.addContent(idNode);
		applyNode.addContent(bvarNode);
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
		return castChild(applyNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTLaplacianNode){
		throw new RuntimeException("ExpressionMathMLPrinter.getMathML(), laplacian operator not yet supported");
	}else if (node instanceof ASTAssignNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.EQUAL));
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
		applyNode.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
		return castChild(applyNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTAddNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.PLUS));
		for (int i = 0; i < node.jjtGetNumChildren(); i++){
			applyNode.addContent(getMathML(node.jjtGetChild(i), MathType.REAL));	
		}
		return castChild(applyNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTExpression){
		if (node.jjtGetNumChildren()!=1){
			throw new ExpressionException("Expression node should have 1 child");
		}	
		return castChild(getMathML(node.jjtGetChild(0), desiredMathType), desiredMathType, desiredMathType);
	}else if (node instanceof ASTFloatNode){
		org.jdom.Element floatNode = new org.jdom.Element(MathMLTags.CONSTANT);
		Double value = ((ASTFloatNode)node).value;
		floatNode.addContent(value.toString());
		//floatNode.setAttribute(new org.jdom.Attribute(MathMLTags.CellML_units,MathMLTags.DIMENSIONLESS,org.jdom.Namespace.getNamespace(cbit.util.XMLTags.CELLML_NAMESPACE_PREFIX, cbit.util.XMLTags.CELLML_NAMESPACE_URI) ));
		return castChild(floatNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTFuncNode){
		ASTFuncNode funcNode = (ASTFuncNode)node;
		//
		// functions that have direct MathML mappings
		//
		if (funcNode.getMathMLName()!=null){
			org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
			String mathMLFunctionName = funcNode.getMathMLName();
			applyNode.addContent(new org.jdom.Element(mathMLFunctionName));
			for (int i = 0; i < node.jjtGetNumChildren(); i++){
				applyNode.addContent(getMathML(node.jjtGetChild(i), MathType.REAL));	
			}
			return castChild(applyNode, desiredMathType, MathType.REAL);
		//
		// functions that do not have direct MathML mappings
		//
		}else if (funcNode.getFunction() == ASTFuncNode.SQRT){
			org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
			applyNode.addContent(new org.jdom.Element(MathMLTags.ROOT));
			Element degreeNode = new Element(MathMLTags.DEGREE);
			applyNode.addContent(degreeNode);
			org.jdom.Element constNode = new org.jdom.Element(MathMLTags.CONSTANT);
			constNode.setAttribute("type", "integer");
			constNode.addContent("2");
			degreeNode.addContent(constNode);
			applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));	
			//constNode.setAttribute(new org.jdom.Attribute(MathMLTags.CellML_units,MathMLTags.DIMENSIONLESS,org.jdom.Namespace.getNamespace(cbit.util.XMLTags.CELLML_NAMESPACE_PREFIX, cbit.util.XMLTags.CELLML_NAMESPACE_URI)));
			return castChild(applyNode, desiredMathType, MathType.REAL);
		}else if (funcNode.getFunction() == ASTFuncNode.ATAN2){
			throw new ExpressionException("cannot translate atan(a,b) into MathML");
		}else if (funcNode.getFunction() == ASTFuncNode.MIN){
			/* a < b ? a : b;
				 <piecewise>                           
				    <piece>                            
				       <ci> a < /ci>                        
				       <apply>                                 
				         (a < b)                  
				       </apply>                            
				    </piece>                                  
				    <otherwise>                                    
				       <ci> b </ci>
				    </othewise>                       
				 </piecewise>
			 */                             

			// Construct the piecewise element : create piece and otherwise separately and add.
			Element piecewiseElement = new org.jdom.Element(MathMLTags.PIECEWISE);
			// construct the piece element :  
			Element pieceElement  = new org.jdom.Element(MathMLTags.PIECE);
			pieceElement.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
			Element applyElement = new org.jdom.Element(MathMLTags.APPLY);
			Element condnElement = new org.jdom.Element(MathMLTags.LESS);
			applyElement.addContent(condnElement);
			applyElement.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
			applyElement.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
			pieceElement.addContent(applyElement);
			// construct the otherwise element : add
			Element otherwiseElement = new org.jdom.Element(MathMLTags.OTHERWISE);
			otherwiseElement.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
			// Now put together the piecewise element with the piece and otherwise. 
			piecewiseElement.addContent(pieceElement);
			piecewiseElement.addContent(otherwiseElement);
			return castChild(piecewiseElement, desiredMathType, MathType.REAL);
		}else if (funcNode.getFunction() == ASTFuncNode.MAX){
			/* a < b ? b : a;
				 <piecewise>                           
				    <piece>                            
				       <ci> b < /ci>                        
				       <apply>                                 
				         (a < b)                  
				       </apply>                            
				    </piece>                                  
				    <otherwise>                                    
				       <ci> a </ci>
				    </othewise>                       
				 </piecewise>
			 */                             

			// Construct the piecewise element : create piece and otherwise separately and add.
			Element piecewiseElement = new org.jdom.Element(MathMLTags.PIECEWISE);
			// construct the piece element :  
			Element pieceElement  = new org.jdom.Element(MathMLTags.PIECE);
			pieceElement.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
			Element applyElement = new org.jdom.Element(MathMLTags.APPLY);
			Element condnElement = new org.jdom.Element(MathMLTags.LESS);
			applyElement.addContent(condnElement);
			applyElement.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
			applyElement.addContent(getMathML(node.jjtGetChild(1), MathType.REAL));
			pieceElement.addContent(applyElement);
			// construct the otherwise element : add
			Element otherwiseElement = new org.jdom.Element(MathMLTags.OTHERWISE);
			otherwiseElement.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
			// Now put together the piecewise element with the piece and otherwise. 
			piecewiseElement.addContent(pieceElement);
			piecewiseElement.addContent(otherwiseElement);
			return castChild(piecewiseElement, desiredMathType, MathType.REAL);
		}else{
			throw new ExpressionException("cannot translate "+funcNode.getName()+" into MathML");
		}
			
	}else if (node instanceof ASTIdNode){
		org.jdom.Element idNode = null;
		String nodeName = ((ASTIdNode)node).name;
		if (nodeName.equals("t")) {
			idNode = new org.jdom.Element(MathMLTags.CSYMBOL);
			idNode.setAttribute(MathMLTags.ENCODING, "text");
			idNode.setAttribute(MathMLTags.DEFINITIONURL, "http://www.sbml.org/sbml/symbols/time");
			idNode.setText(nodeName);
		} else {
			idNode = new org.jdom.Element(MathMLTags.IDENTIFIER);
			idNode.setText(nodeName);
		}
		return castChild(idNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTInvertTermNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.DIVIDE));
		org.jdom.Element unityNode = new org.jdom.Element(MathMLTags.CONSTANT);
		//unityNode.setAttribute(new org.jdom.Attribute(MathMLTags.CellML_units,MathMLTags.DIMENSIONLESS,org.jdom.Namespace.getNamespace(cbit.util.XMLTags.CELLML_NAMESPACE_PREFIX, cbit.util.XMLTags.CELLML_NAMESPACE_URI)));
		unityNode.setText("1.0");
		applyNode.addContent(unityNode);
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
		return castChild(applyNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTMinusTermNode){
		org.jdom.Element applyNode = new org.jdom.Element(MathMLTags.APPLY);
		applyNode.addContent(new org.jdom.Element(MathMLTags.MINUS));
		applyNode.addContent(getMathML(node.jjtGetChild(0), MathType.REAL));
		return castChild(applyNode, desiredMathType, MathType.REAL);
	}else if (node instanceof ASTMultNode){
		// See ASTMultNode.infix for C_language fix.
		// If children nodes are boolean, collect them with '&&' condition; else collect them as product of the values. 
		int numChildren = node.jjtGetNumChildren();
		boolean[] boolChildFlags = new boolean[numChildren];
		int numReal = 0;
		int numBoolean = 0;
		for (int i=0;i<numChildren;i++){
			if (node.jjtGetChild(i).isBoolean()) {
				boolChildFlags[i] = true;
				numBoolean++;
			} else {
				numReal++;
			}
		}
		Element resultantElement = null;
		Element valueParentElement  = null;
		Element condnParentElement  = null;
		Element pieceElement = new Element(MathMLTags.PIECE);
		if (numReal > 0 && numBoolean > 0) {
			if (numReal > 1) {
				valueParentElement = new Element(MathMLTags.APPLY);
				valueParentElement.addContent(new Element(MathMLTags.TIMES));
				pieceElement.addContent(valueParentElement);
			} else {
				valueParentElement = pieceElement;
			}
			for (int i = 0; i < numChildren; i++){
				if (!boolChildFlags[i]) {
					valueParentElement.addContent(getMathML(node.jjtGetChild(i), MathType.REAL));
				} 
			}
			if (numBoolean > 1) {
				condnParentElement = new Element(MathMLTags.APPLY);
				condnParentElement.addContent(new Element(MathMLTags.AND));
				pieceElement.addContent(condnParentElement);
			} else {
				condnParentElement = pieceElement;
			}
			for (int i = 0; i < numChildren; i++){
				if (boolChildFlags[i]) {
					condnParentElement.addContent(getMathML(node.jjtGetChild(i), MathType.BOOLEAN));
				} 
			}
			// Construct the piecewise element : create piece and otherwise separately and add.
			resultantElement = new org.jdom.Element(MathMLTags.PIECEWISE);
			// 'piece' element content already added above;  construct the otherwise element
			Element otherwiseElement = new org.jdom.Element(MathMLTags.OTHERWISE);
			Element constElement_0 = new org.jdom.Element(MathMLTags.CONSTANT);
			constElement_0.addContent("0.0");
			otherwiseElement.addContent(constElement_0);
			// Now put together the piecewise element with the piece and otherwise. 
			resultantElement.addContent(pieceElement);
			resultantElement.addContent(otherwiseElement);
			resultantElement = castChild(resultantElement, desiredMathType, MathType.REAL);
		} else if (numReal > 0){
			resultantElement = new org.jdom.Element(MathMLTags.APPLY);
			resultantElement.addContent(new org.jdom.Element(MathMLTags.TIMES));
			for (int i = 0; i < node.jjtGetNumChildren(); i++){
				resultantElement.addContent(getMathML(node.jjtGetChild(i), MathType.REAL));	
			}
			resultantElement = castChild(resultantElement, desiredMathType, MathType.REAL);
		} else if (numBoolean > 0){
			resultantElement = new org.jdom.Element(MathMLTags.APPLY);
			resultantElement.addContent(new org.jdom.Element(MathMLTags.AND));
			for (int i = 0; i < node.jjtGetNumChildren(); i++){
				resultantElement.addContent(getMathML(node.jjtGetChild(i), MathType.BOOLEAN));	
			}
			resultantElement = castChild(resultantElement, desiredMathType, MathType.BOOLEAN);
		}
		return resultantElement;
	}else{
		throw new ExpressionException("node type "+node.getClass().toString()+" not supported yet");
	}		
}
}
