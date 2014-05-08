/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.xml;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Parent;
import org.vcell.util.BeanUtils;
import org.vcell.util.Matchable;

import cbit.util.xml.XmlUtil;
import cbit.vcell.biomodel.BioModel;
import cbit.vcell.biomodel.meta.xml.XMLMetaData;
import cbit.vcell.mathmodel.MathModel;
import cbit.vcell.modelopt.ParameterEstimationTaskXMLPersistence;

/**
This utility class encapsulates the functionality of comparing VCML documents. Contrary to what the class name might indicate,
this class does not extend java.util.Comparator

 * Creation date: (9/21/2004 4:04:50 PM)
 * @author: Rashad Badrawi
 */
public class VCMLComparator {

	private static boolean VERBOSE_MODE = true;	                //for now...
	public static boolean DEBUG_MODE = false;
	private static boolean ERROR_RECORDED = false;
	
	private static PrintStream ps;
	public static PrintStream getPs() {
		return ps;
	}


	public static void setPs(PrintStream ps) {
		VCMLComparator.ps = ps;
	}


	private static Hashtable<String, String> map;

	public static class VCMLElementSorter implements Comparator<Element> {
	
		public int compare(Element e1, Element e2) {

			int result;

			//sort by their element name
			String eName1 = e1.getName();
			String eName2 = e2.getName();
			result = eName1.compareTo(eName2);
			if (result != 0) {
				return result;
			}
			//Do not sort diagram tags because their order is meaningful
			if (e1.getName().equals(XMLTags.DiagramTag)) {                         //or eName2, no re-ordering for Coordinate elements. 
				return result;//0 
			}

			if (eName1.equals(XMLTags.CoordinateTag)) {                         //or eName2, no re-ordering for Coordinate elements. 
				return result; 
			}
			//if they belong to the same element, sort by their 'primary key' attribute.
			String pkName = (String)map.get(eName1);                       //or eName2
			if (pkName == null)
				pkName = XMLTags.NameAttrTag;
			int index = pkName.indexOf("&");
			if (index == -1) {
				if (pkName.equals("TEXT")) {
					result = e1.getTextTrim().compareTo(e2.getTextTrim());
				} else {
					if((e1.getAttributeValue(pkName) == null)){
						result = 0;
					}else{
						result = e1.getAttributeValue(pkName).compareTo(e2.getAttributeValue(pkName));
					}
				}
			} else {         
				java.util.StringTokenizer tokens = new java.util.StringTokenizer(pkName,"&");
    			while (tokens.hasMoreElements()){
     				String token = tokens.nextToken();
     				result = e1.getAttributeValue(token).compareTo(e2.getAttributeValue(token));
     				if (result != 0){
      					break;
     				}
    			}
			}

			return result;
		}	
	}

	//can also be loaded from a property file. Fills a hashtable of all the VCML elements whose 'primary key' is not 'Name'
	//but some other attribute.
	static { 
		map = new Hashtable<String, String>();
		map.put(XMLTags.ReactantTag, XMLTags.SpeciesContextRefAttrTag);
		map.put(XMLTags.ProductTag, XMLTags.SpeciesContextRefAttrTag);
		map.put(XMLTags.CatalystTag, XMLTags.SpeciesContextRefAttrTag);
		map.put(XMLTags.SpeciesContextShapeTag, XMLTags.SpeciesContextRefAttrTag);
		map.put(XMLTags.SimpleReactionShapeTag, XMLTags.SimpleReactionRefAttrTag);
		map.put(XMLTags.FluxReactionShapeTag, XMLTags.FluxReactionRefAttrTag);
		map.put(XMLTags.FeatureMappingTag, XMLTags.FeatureAttrTag);
		map.put(XMLTags.MembraneMappingTag, XMLTags.MembraneAttrTag);
		map.put(XMLTags.SpeciesContextSpecTag, XMLTags.SpeciesContextRefAttrTag);
		map.put(XMLTags.ReactionSpecTag, XMLTags.ReactionStepRefAttrTag);
		map.put(XMLTags.BoundaryTypeTag, XMLTags.BoundaryAttrTag);
		map.put(ParameterEstimationTaskXMLPersistence.ParameterMappingSpecTag, ParameterEstimationTaskXMLPersistence.ParameterReferenceAttribute);
		map.put(ParameterEstimationTaskXMLPersistence.ReferenceDataMappingSpecTag, ParameterEstimationTaskXMLPersistence.ReferenceDataColumnNameAttribute);
		map.put(ParameterEstimationTaskXMLPersistence.DataRowTag, "TEXT");
		
		map.put(XMLTags.FastInvariantTag, "TEXT");
		map.put(XMLTags.FastRateTag, "TEXT");
		map.put(XMLTags.NameTag, "TEXT");
		//a hack, for compound 'primary key'
		map.put(XMLTags.MembraneSubDomainTag, XMLTags.InsideCompartmentTag + "&" + XMLTags.OutsideCompartmentTag);
		map.put(XMLTags.CoordinateTag, XMLTags.XAttrTag + "&" + XMLTags.YAttrTag + "&" + XMLTags.ZAttrTag);
		map.put(XMLTags.VelocityTag, XMLTags.XAttrTag + "&" + XMLTags.YAttrTag + "&" + XMLTags.ZAttrTag);
		map.put(XMLTags.SurfaceDescriptionTag, XMLTags.CutoffFrequencyAttrTag + "&" + XMLTags.NumSamplesXAttrTag +
			   "&" + XMLTags.NumSamplesYAttrTag + "&" + XMLTags.NumSamplesZAttrTag);     //?

		//for stochastic model , added 19th Sept, 2006
		map.put(XMLTags.ActionTag, XMLTags.VarNameAttrTag);
		map.put(XMLTags.ProbabilityRateTag, "TEXT");
		
		map.put(XMLMetaData.NONRDF_ANNOTATION_TAG, XMLMetaData.VCID_ATTR_TAG);
		map.put(XMLMetaData.FREETEXT_TAG, "TEXT");
		ps = System.out;
	}
	
	private static boolean compareAtts(Element source,Element target) {

	    @SuppressWarnings("unchecked")
		ArrayList<Attribute> list1 = new ArrayList<Attribute>(source.getAttributes());
	    @SuppressWarnings("unchecked")
		ArrayList<Attribute> list2 = new ArrayList<Attribute>(target.getAttributes());

	    //not sure of Attribute.equals(). For now, only name and value are compared.
	    /*class AttComparator implements Comparator {

	        public int compare(Object o1, Object o2) {

	            int result = -1;

	            Attribute a1 = (Attribute) o1;
	            Attribute a2 = (Attribute) o2; 

	            if (a1.getName().equals(a2.getName()))
	                if (a1.getValue().equals(a2.getValue()))
	                    result = 0;

	            return result;
	        }
	    }
	    */
	    
	    int result;
	    boolean attFlag = true;
	    //AttComparator attComp = new AttComparator();

	    Attribute atts1[] = (Attribute[]) list1.toArray(new Attribute[list1.size()]);
	    Attribute atts2[] = (Attribute[]) list2.toArray(new Attribute[list2.size()]);

	    if (atts1.length != atts2.length) {
	    	printInfo(source, target);
		    return false;
	    }
	    
	    for (int i = 0; i < atts1.length; i++) {
	        //result = Arrays.binarySearch(atts2, atts1[i], attComp);
	        result = -1;
	        for (int j = 0; j < atts2.length; j++) {
	            if (atts2[j].getName().equals(atts1[i].getName())
	                && atts2[j].getValue().equals(atts1[i].getValue())) {
	                result = 0;
	                break;
	            }
	        }
	        if (result < 0) {
	            ps.println(
	                "Attribute: "
	                    + atts1[i].getName()
	                    + " for element: "
	                    + atts1[i].getParent()
	                    + " not equal/found.");
	            attFlag = false;
	        }
	    }
	    if(!attFlag){
	    	printInfo(source, target);
	    }
	    return attFlag;
	}

	public static boolean compareEquals(String xmlStr1, String xmlStr2, boolean bSkipVCMetaData) throws XmlParseException {
//		System.out.println("-----VCMLComparator.DEBUG_MODE="+VCMLComparator.DEBUG_MODE);
		if (xmlStr1 == null || xmlStr1.length() == 0 ||
			xmlStr2 == null || xmlStr2.length() == 0) {
			throw new XmlParseException("Invalid values for the xml strings.");
		}
		return compareXML(xmlStr1, xmlStr2, false, bSkipVCMetaData);
	}


	public static boolean compareMatchables(Matchable m1, Matchable m2, String type, boolean bSkipVCMetaData) {

		Element source = null, target = null; 
		try { 
			Xmlproducer producer = new Xmlproducer(true);
			if (type.equals(XMLTags.BioModelTag)) {
				source = producer.getXML((BioModel)m1);
				target = producer.getXML((BioModel)m2);
			} else if (type.equals(XMLTags.MathModelTag)) {
				source = producer.getXML((MathModel)m1);
				target = producer.getXML((MathModel)m2);
			} else {
				throw new IllegalArgumentException("Accepted matchable types are biomodel and mathmodel");
			}
			String sourceXMLStr = XmlUtil.xmlToString(source);
			String targetXMLStr = XmlUtil.xmlToString(target);
			boolean result = compareXML(sourceXMLStr, targetXMLStr, true, bSkipVCMetaData);
			if (!result && VERBOSE_MODE) {
				ps.println(sourceXMLStr);
				ps.println(targetXMLStr);
			}
			return result;
		} catch (Exception e) {         					//ExpressionException, XmlParseException 
			e.printStackTrace();
			return false;
		}
	}

	private static void printInfo(Element source, Element target){
		//
//		System.out.println("VCMLComparator.DEBUG_MODE="+VCMLComparator.DEBUG_MODE);
		if(VCMLComparator.DEBUG_MODE &&  !VCMLComparator.ERROR_RECORDED){
			VCMLComparator.ERROR_RECORDED = true;
			System.err.println("-source parent="+source.getParent());
			System.err.println("-target parent="+target.getParent());

			System.err.println("--source ="+source);
			System.err.println("--target ="+target);

			printAttributeList("source",source);
			printAttributeList("target",target);
			
			System.out.println("failed");
		}
	}

	private static void printAttributeList(String originator,Element element){
		@SuppressWarnings("unchecked")
		List<Attribute> attributeList = element.getAttributes();
		System.err.print("--"+originator+" Attributes("+(attributeList == null?0:attributeList.size())+") = ");
		if(attributeList != null && attributeList.size() != 0){
			Attribute[] attrArr = attributeList.toArray(new Attribute[0]);
			Arrays.sort(attrArr,new Comparator<Attribute>() {
				public int compare(Attribute o1, Attribute o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			final int PRINTSIZE = 35;
			final String PRINTSIZEWARN = " (...)";
			for (int i = 0; i < attrArr.length; i++) {
				String attrNameVal = attrArr[i].getName()+":"+attrArr[i].getValue();
				if(attrNameVal.length() > PRINTSIZE){
					attrNameVal = attrNameVal.substring(0,PRINTSIZE-PRINTSIZEWARN.length())+PRINTSIZEWARN;
				}
				System.err.print(BeanUtils.forceStringSize(attrNameVal,PRINTSIZE, "", false)+(i==(attrArr.length-1)?"":" , "));
			}
			System.err.println();
		}else{
			System.err.println("empty");
		}
	}
	//the testAll boolean indicate whether to cover all elements in the test, even if the test fails. 
	private static boolean compareVCML(Element source, Element target, boolean testAll, boolean bSkipVCMetaData) {

		boolean elementFlag = true, attFlag = true, textFlag = true;
	    VCMLElementSorter elementSorter = new VCMLElementSorter();

	    if (!source.getName().equals(target.getName())) {           //wrong element.
	        ps.println("Element: "  + source.getName() + " with parent: " + source.getParent() + " is lost.");
	        printInfo(source, target);
			elementFlag = false;
			if (!testAll) {
				return elementFlag;
			}
	    }
	    if (!source.getTextTrim().equals(target.getTextTrim())) {
	    	ps.println("Element: "
	                    + source.getName()
	                    + " with parent: "
	                    + getPathToRoot(source)
	                    + " with text: "
	                    + source.getTextTrim()
	                    + " is lost.");
	    	printInfo(source, target);
	        textFlag = false;
	        if (!testAll) {
				return textFlag;
			}
	    }
	    attFlag = compareAtts(source,target);

	    if (bSkipVCMetaData) {
	    	source.removeChild(XMLTags.AnnotationTag);
	    	target.removeChild(XMLTags.AnnotationTag);
	    	
	    	source.removeChild(XMLTags.AnnotationTag, Namespace.getNamespace(XMLTags.VCML_NS));
	    	target.removeChild(XMLTags.AnnotationTag, Namespace.getNamespace(XMLTags.VCML_NS));
	    	
//	    	// legacy sbmlAnnotation "annotation" attribute??
//	    	source.removeChild(XMLTags.SbmlAnnotationTag, Namespace.getNamespace(XMLTags.VCML_NS));
//	    	target.removeChild(XMLTags.SbmlAnnotationTag, Namespace.getNamespace(XMLTags.VCML_NS));
	    	
	    	source.removeChild(XMLMetaData.VCMETADATA_TAG, Namespace.getNamespace(XMLTags.VCML_NS));
	    	target.removeChild(XMLMetaData.VCMETADATA_TAG, Namespace.getNamespace(XMLTags.VCML_NS));
	    }
	    @SuppressWarnings("unchecked")
		ArrayList<Element> children1 = new ArrayList<Element>(source.getChildren());
	    @SuppressWarnings("unchecked")
		ArrayList<Element> children2 = new ArrayList<Element>(target.getChildren());
	    if (children1.size() != children2.size()) {
	        String pkName = (String) map.get(source.getName());
	        //sometimes will fail, but better than nothing
	        if (pkName == null)
	            pkName = XMLTags.NameAttrTag;
	        ps.println("Element's children: " + source.getName() + ": "
	                + source.getAttributeValue(pkName)
	                + " are partially/completely lost");
	        printInfo(source, target);
	        return false;
	    }
	    Element e1[] = (Element[]) children1.toArray(new Element[children1.size()]);
	    Element e2[] = (Element[]) children2.toArray(new Element[children2.size()]);
	    if (e1.length > 1) {
	        Arrays.sort(e1, elementSorter);
	        Arrays.sort(e2, elementSorter);
	    }
	    boolean bChildrenSame = true;
	    for (int j = 0; j < e1.length; j++) {
	        Element child1 = e1[j];
	        Element child2 = e2[j];
	        if (!compareVCML(child1, child2, testAll, bSkipVCMetaData)){
	        	printInfo(source, target);
		        bChildrenSame = false;
				if (!testAll){
		     	   return false;
				}
	        }
	    }
	    boolean bFinalFlag = bChildrenSame && elementFlag && attFlag && textFlag;
	    
//	    if (bFinalFlag) {
//	    	return true;
//	    } else {
//	    	printInfo(source, target);
//	    	return false;
//	    }
		return bFinalFlag;
	}


	private static boolean compareXML(String xmlStr1, String xmlStr2, boolean testAll, boolean bSkipVCMetaData) throws XmlParseException {
		VCMLComparator.ERROR_RECORDED = false;
		if (xmlStr1.equals(xmlStr2)) {
//			ps.println("The xml strings are identical.");
			return true;
		}
		Element sRoot = XmlUtil.stringToXML(xmlStr1, null).getRootElement();
		Element tRoot = XmlUtil.stringToXML(xmlStr2, null).getRootElement();
		if (compareVCML(sRoot, tRoot, testAll, bSkipVCMetaData)) {
			ps.println("The two xml trees: " + sRoot.getName() + " are identical with different ordering.");
			return true;
		}
		return false;
	}


	//utility method, can be moved in the xml util class/package
	private static String getPathToRoot(Element e) {

		StringBuffer buf = new StringBuffer();
		String attName, attVal;
		
		while (e != null) {
			//will not work for compound pks, but ok for now.
			attName = (String)map.get(e.getName());
			if (attName == null)
				attName = XMLTags.NameAttrTag;
			attVal = e.getAttributeValue(attName);
			if (attVal != null) {
				buf.append(e.getName() + ": " +  attVal + "/");
			}
			Parent parent = e.getParent();
			if(parent instanceof Element) {
				e = (Element)parent;
			} else if (parent instanceof Document){
				return buf.toString();	// we reached the root already
			}  else {
				return buf.toString();	// this should not happen, anyway we return what we've got so far
			}
		}
		return buf.toString();
	}
}
