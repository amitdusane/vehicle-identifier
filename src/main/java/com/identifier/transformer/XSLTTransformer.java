package com.identifier.transformer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * A Transformer which transforms dataXML into outputXML using inputXSL
 * @author Amit Dusane
 *
 */
public class XSLTTransformer {

	/**
	 * Transforms dataXML into outputXML using inputXSL
	 * @param dataXML
	 * @param inputXSL
	 * @param outputXML
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static void transform(String dataXML, String inputXSL, String outputXML)	throws TransformerConfigurationException,TransformerException{

		TransformerFactory factory = TransformerFactory.newInstance(); 

		StreamSource xslStream = new StreamSource(inputXSL);

		Transformer transformer = factory.newTransformer(xslStream);

		StreamSource in = new StreamSource(dataXML);

		StreamResult out = new StreamResult(outputXML);

		transformer.transform(in, out);	

	}

}
