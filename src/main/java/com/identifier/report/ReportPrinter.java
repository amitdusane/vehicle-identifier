package com.identifier.report;

import java.util.List;
import java.util.Map;

import com.identifier.generated.VehicleType;

public class ReportPrinter {
	
	public static void printReport(
			List<VehicleType> inputVehicles, Map<String, List<VehicleType>> resultVehicleMap, Map<String, VehicleType> availableVehicles) {
		
		
		System.out.println("-----------------------------------------------------------------------------");
		System.out.format("%37s\n", "REPORTS");
		System.out.println("-----------------------------------------------------------------------------\n\n");
		
		
		
		System.out.println("-----------------------------------------------------------------------------");
		System.out.format("%25s%25s\n", "Vehicle ID", "Vehicle Type");
		System.out.println("-----------------------------------------------------------------------------");
		
		
		
		for(VehicleType inputVehicle: inputVehicles){
			boolean vehicleFound = false;
			
			for(String vehicleId: resultVehicleMap.keySet()){
				
				List<VehicleType> vehicleList = resultVehicleMap.get(vehicleId);
				boolean vehicleFoundInList = false;
				
				for(VehicleType vehicle: vehicleList){
					
					if(inputVehicle.equals(vehicle)){
						vehicleFoundInList = true;
						break;
					}
					
				}
				
				if(vehicleFoundInList){
					System.out.format("%25s%25s\n", inputVehicle.getId(), vehicleId);
					vehicleFound = true;
					break;
				}
				
			}
			
			if(!vehicleFound){
				System.out.format("%25s%25s\n", inputVehicle.getId(), "Unknown");
			}
		}
		
		
		
		
		/*for(VehicleType inputVehicle: inputVehicles){
			for(String vehicleId: resultVehicleMap.keySet()){
				List<VehicleType> vehicleList = resultVehicleMap.get(vehicleId);
				
				for(VehicleType vehicle: vehicleList){
					if(inputVehicle.equals(vehicle)){
						System.out.format("%25s%25s\n", inputVehicle.getId(), vehicleId);
						break;
					}
					
				}
			}
		}*/		
		System.out.println("-----------------------------------------------------------------------------\n\n");		
		System.out.format("%25s\n", "Summary: Number of vehicles of each types in the input XML");				
		System.out.println("-----------------------------------------------------------------------------");
		
		System.out.format("%25s%25s%25s\n", "Vehicle Type", "Present/Absent", "No of Time(s)");
		System.out.println("-----------------------------------------------------------------------------");
		
		
		for(String vehicleId: availableVehicles.keySet()){
			
			if(resultVehicleMap.get(vehicleId) != null){
				
				System.out.format("%25s%25s%25s\n", vehicleId, "Present", resultVehicleMap.get(vehicleId).size());
				//System.out.println(vehicleId + " \t Present \t " + resultVehicleMap.get(vehicleId).size());
			} else {
				System.out.format("%25s%25s%25s\n", vehicleId, "Absent", "-");
			}
		}
		System.out.println("-----------------------------------------------------------------------------");
		
	}

}
