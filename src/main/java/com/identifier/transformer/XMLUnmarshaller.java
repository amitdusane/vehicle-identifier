package com.identifier.transformer;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.identifier.generated.VehiclesType;
import com.identifier.utils.EmptyChecker;

/**
 * Unmarshall inputXML to <code>VehiclesType</code> using given package
 * @author Amit Dusane
 *
 */
public class XMLUnmarshaller {
	final static Logger log = Logger.getLogger(XMLUnmarshaller.class);
	/**
	 * Unmarshall specified xml into <code>VehiclesType</code>
	 * @param inputXMLPath
	 * @return VehiclesType
	 * @throws JAXBException
	 */
	public static VehiclesType unmarshallXML(String inputXMLPath, String packageName) throws JAXBException {		
		
		if(EmptyChecker.isEmpty(inputXMLPath) || EmptyChecker.isEmpty(packageName)){
			log.warn("Input xml path or/and Package name is/are not provided. Unmarshalling failed.");
			return null;
		}
		
		Unmarshaller unMarshaller = createUnmarshaller(packageName);
		
		@SuppressWarnings("unchecked")
		JAXBElement<VehiclesType> vehiclesElement = (JAXBElement<VehiclesType>) unMarshaller.unmarshal(new File(inputXMLPath));
		
		return EmptyChecker.isValued(vehiclesElement) ? vehiclesElement.getValue() : null;
	}

	/**
	 * Creates new <code>Unmarshaller</code>
	 * @param packageName
	 * @return
	 * @throws JAXBException
	 */
	private static Unmarshaller createUnmarshaller(String packageName) throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(packageName);		
		Unmarshaller unMarshaller= EmptyChecker.isValued(jaxbContext) ? jaxbContext.createUnmarshaller() : null;
		
		return EmptyChecker.isValued(unMarshaller) ? unMarshaller : null;
	}	
}
