package com.identifier.transformer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.junit.Test;

import com.identifier.constant.TestConstant;
import com.identifier.generated.VehiclesType;

public class XMLUnmarshallerTest {
	
	@Test
	public void testUnmarshallXML_ValidXML() throws JAXBException {
		
			VehiclesType vehicleType = XMLUnmarshaller.unmarshallXML(TestConstant.VALID_XML, TestConstant.VALID_PACKAGE);
			assertNotNull(vehicleType);		
	}
	
	@Test(expected=UnmarshalException.class)
	public void testUnmarshallXML_MalformedXML() throws JAXBException {	
		
			XMLUnmarshaller.unmarshallXML(TestConstant.MALFORMED_XML, TestConstant.VALID_PACKAGE);				
	}
	
	@Test()
	public void testUnmarshallXML_MissingXML() throws JAXBException {	
		
			assertNull(XMLUnmarshaller.unmarshallXML(TestConstant.MISSING_XML, TestConstant.VALID_PACKAGE));				
	}
	
	@Test
	public void testUnmarshallXML_MissingPackage() throws JAXBException {
		
		assertNull(XMLUnmarshaller.unmarshallXML(TestConstant.VALID_XML, TestConstant.MISSING_PACKAGE));
	}
	
	@Test(expected=JAXBException.class)
	public void testUnmarshallXML_IncorrectPackage() throws JAXBException {
		
		XMLUnmarshaller.unmarshallXML(TestConstant.VALID_XML, TestConstant.MALFORMED_PACKAGE);
	}

}
