package com.identifier.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

import com.identifier.generated.FrameType;
import com.identifier.generated.ObjectFactory;
import com.identifier.generated.PowertrainType;
import com.identifier.generated.VehicleType;
import com.identifier.generated.VehiclesType;
import com.identifier.generated.WheelType;
import com.identifier.generated.WheelsType;
import com.identifier.report.ReportPrinter;
import com.identifier.transformer.XMLUnmarshaller;
import com.identifier.transformer.XSLTTransformer;
import com.identifier.utils.EmptyChecker;

/**
 * Starting point of application which integrates all tasks to get desired report
 * @author Amit Dusane
 *
 */
public class VehicleIdentifier {
	final static Logger log = Logger.getLogger(VehicleIdentifier.class);
	
	private static final ObjectFactory objectFactory = new ObjectFactory();
	
	private static Map<String, VehicleType> availableVehicles = new LinkedHashMap<String, VehicleType>();
	
	public static final String INPUT_XML_PATH = "./src/main/resources/vehicles.xml";
	
	public static final String XSL_PATH = "./src/main/resources/vehicles.xsl";
	
	public static final String TRANSFORMED_XML_PATH = "./src/main/resources/transformed_vehicles.xml";	
	
	public static final String GENERATED_PACKAGE = "com.identifier.generated";
	
	static{
		createVehicleTypes();
	}
	
	public static void main(String[] args) {		
		
		try {
			XSLTTransformer.transform(INPUT_XML_PATH, XSL_PATH, TRANSFORMED_XML_PATH);
		} catch (TransformerConfigurationException e1) {
			log.error("Error occured while transforming xml - " + e1);
		} catch (TransformerException e1) {
			log.error("Error occured while transforming xml - " + e1);
		}
				
		VehiclesType vehiclesRoot = null;
		
		try {
			vehiclesRoot = XMLUnmarshaller.unmarshallXML(TRANSFORMED_XML_PATH, GENERATED_PACKAGE);
		} catch (JAXBException e) {
			log.error("Error occurred while unmarshalling xml - " + e);
		}
				
		List<VehicleType> inputVehicles = EmptyChecker.isValued(vehiclesRoot) ? vehiclesRoot.getVehicle() : new ArrayList<VehicleType>();
		
		Map<String, List<VehicleType>> resultVehicleMap = performIdentification(inputVehicles);
		
		ReportPrinter.printReport(inputVehicles, resultVehicleMap, availableVehicles);
		
	}

	/**
	 * Creates in memory templates of vehicle types, which will be compared against transformed xml
	 */
	private static void createVehicleTypes() {		
		
		VehicleType bigWheel = createVehicleType("Big Wheel", createFrameType("PLASTIC"), createPowertrainType("HUMAN"), createWheelsType(Arrays.asList(createWheelType("FRONT", "PLASTIC"), createWheelType("LEFT REAR", "PLASTIC"), createWheelType("RIGHT REAR", "PLASTIC"))));
		VehicleType bicycle = createVehicleType("Bicycle", createFrameType("METAL"), createPowertrainType("HUMAN"), createWheelsType(Arrays.asList(createWheelType("FRONT", "METAL"), createWheelType("REAR", "METAL"))));
		VehicleType motorcycle = createVehicleType("Motorcycle", createFrameType("METAL"), createPowertrainType("INTERNAL COMBUSTION"), createWheelsType(Arrays.asList(createWheelType("FRONT", "METAL"), createWheelType("REAR", "METAL"))));
		VehicleType hangGlider = createVehicleType("Hang Glider", createFrameType("PLASTIC"), createPowertrainType("BERNOULLI"), createWheelsType(new ArrayList<WheelType>()));
		VehicleType car = createVehicleType("Car", createFrameType("METAL"), createPowertrainType("INTERNAL COMBUSTION"), createWheelsType(Arrays.asList(createWheelType("FRONT RIGHT", "METAL"), createWheelType("FRONT LEFT", "METAL"), createWheelType("REAR LEFT", "METAL"), createWheelType("REAR RIGHT", "METAL"))));
				
		availableVehicles.put("Big Wheel", bigWheel);
		availableVehicles.put("Bicycle", bicycle);
		availableVehicles.put("Motorcycle", motorcycle);
		availableVehicles.put("Hang Glider", hangGlider);
		availableVehicles.put("Car", car);				
	}

	private static VehicleType createVehicleType(String id,
			FrameType frameType, PowertrainType powertrainType,
			WheelsType wheelsType) {
		
		VehicleType vehicleType = objectFactory.createVehicleType();
		vehicleType.setId(id);
		vehicleType.setFrame(frameType);
		vehicleType.setPowertrain(powertrainType);
		vehicleType.setWheels(wheelsType);
		
		return vehicleType;
	}

	private static PowertrainType createPowertrainType(String type) {
		PowertrainType pType = objectFactory.createPowertrainType();
		pType.setType(type);
		
		return pType;
	}

	private static FrameType createFrameType(String material) {
		
		FrameType frameType = objectFactory.createFrameType();
		frameType.setMaterial(material);
		
		return frameType;
	}

	private static WheelsType createWheelsType(List<WheelType> wheelList) {
		WheelsType wheelsType = objectFactory.createWheelsType();
		wheelsType.getWheel().addAll(wheelList);
		return wheelsType;
	}

	private static WheelType createWheelType(String position, String material) {
		WheelType wheelType = objectFactory.createWheelType();
		wheelType.setPosition(position);
		wheelType.setMaterial(material);
		return wheelType;
	}

	/**
	 * Performs vehicle identification on vehiclesFromXML
	 * @param vehiclesFromXML
	 * @return
	 */
	public static Map<String, List<VehicleType>> performIdentification(
			List<VehicleType> vehiclesFromXML) {
		
		Map<String, List<VehicleType>> resultMap = new HashMap<String, List<VehicleType>>();
		
		if(EmptyChecker.isEmpty(vehiclesFromXML)){
			log.warn("There are no vehicles in supplied list of vehicles. Skipping identification process.");
			return resultMap;
		}
		
		for(VehicleType xmlVehicle : vehiclesFromXML){
			Collections.sort(xmlVehicle.getWheels().getWheel());
			for(String vehicleId : availableVehicles.keySet()){
				
				VehicleType availableVehicle = availableVehicles.get(vehicleId);
				Collections.sort(availableVehicle.getWheels().getWheel());

				if(EmptyChecker.isValued(availableVehicle) && xmlVehicle.equals(availableVehicle)){
					
					List<VehicleType> oldVehicleList = resultMap.get(vehicleId);
					
					if(EmptyChecker.isValued(oldVehicleList)){
						oldVehicleList.add(xmlVehicle);
					} else {
						List<VehicleType> newVehicleList = new ArrayList<VehicleType>();
						newVehicleList.add(xmlVehicle);
						resultMap.put(vehicleId, newVehicleList);
					}					
					break;
				}
			}
		}
		
		return resultMap;
	}

}
