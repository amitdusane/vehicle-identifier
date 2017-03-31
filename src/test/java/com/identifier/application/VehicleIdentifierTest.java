package com.identifier.application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.identifier.constant.TestConstant;
import com.identifier.generated.VehicleType;
import com.identifier.generated.VehiclesType;
import com.identifier.transformer.XMLUnmarshaller;
import com.identifier.utils.EmptyChecker;

public class VehicleIdentifierTest {
	final static Logger log = Logger.getLogger(VehicleIdentifierTest.class);
	
	private static List<VehicleType> vehiclesFromXML = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
		
		/**
		 * Setting up required data to be used for JUnit testing
		 */
		VehiclesType vehiclesRoot = null;
		
		try {
			vehiclesRoot = XMLUnmarshaller.unmarshallXML(TestConstant.VALID_XML, TestConstant.VALID_PACKAGE);
		} catch (JAXBException e) {
			log.error("Error occurred while unmarshalling xml - " + e);
		}
				
		vehiclesFromXML = EmptyChecker.isValued(vehiclesRoot) ? vehiclesRoot.getVehicle() : new ArrayList<VehicleType>();
		
	}

	@Test
	public void testPerformIdentification_ValidVehicleList() {
		assertNotNull(VehicleIdentifier.performIdentification(vehiclesFromXML));	
	}
	
	@Test
	public void testPerformIdentification_MissingVehicleList() {
		assertTrue(EmptyChecker.isEmpty(VehicleIdentifier.performIdentification(null)));		
	}

}
