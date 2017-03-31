# Vehicle Identifier

This is a JAVA application which can identify vehicle types by their components. Vehicle types are
identified by their properties, such as frame material, number and material of the wheels, and
power train. Given an input containing this information in the XML file, the application will be able to determine
what the vehicle type is (Big Wheel, Bicycle, Car, ...).

The application expects an XML file as input. An XML file containing records specifying
sample vehicles will be provided. 

The output of the application is a report on the console that lists each vehicle id and its type. The report will also provide a
summary saying how many vehicles of each type are in the XML.

The application is smart enough to accept case insensitive data as well as white spaces in input XML file.

Junits are used to test all business logic.


## How I Worked On Problem

Initially, I found the given sample XML is not well formed to represent Vehicles due to these two things,

1. powertrain tag in given sample vehicles.xml

<powertrain>
	<human />
</powertrain>

Since there are multiple values for "powertrain" tag like "Human, Internal Combustion, Bernoulli", we should have a separate tag inside "powertrain" tag like this to accommodate
this behavior.

So I changed "powertrain" tag like this in my application,

<powertrain>
	<type>human</type>
</powertrain>

2. In problem statement for "Car" vehicle type "Wheel material" is not given, so I considered it as "metal" for my application.

After considering above points I started working on problem statement as below,

- Created a vehicles.xsd file from the sample vehicles.xml received with the problem statement.
- Generated Java classes from vehicles.xsd using eclipse -> Generate -> JAXB classes
- Modified generated classes and added customized hashcode and equals methods to each generated POJO
- Created a vehicles.xsl file to transform input vehicles.xml into transformed_vehicles.xml by making all XML text data into Uppercase and removed all white spaces from XML text
- Unmarshall transformed_vehicles.xml into "VehiclesType" object using JAXB Unmarshaller
- Created sample vehicles types objects which will be the basis for comparison with input data in XML
- Implemented a logic which takes unmarshalled "VehiclesType" object, compare it with sample vehicle types and generate a report on console with summary


## Getting Started

To set up this application on your local machine please follow below steps,

- Unzip vehicleIdentifier.zip to your local machine.
- Eclipse -> Import Project -> Maven -> Existing maven project -> Give location of unzipped vehicleIdentifier folder
- Set up is done!!

To test and run the application,

Right click on project -> Run As -> Maven test

By doing this application will compile, test, and finally run the logic and print desired report on the console. 
Since maven script is written in such a way that when we run Maven test after successful tests it will automatically start running the logic of vehicle identification.


### Prerequisites

Java 1.6 and above
Eclipse
Maven

### Packaging

The application is configured to be packaged as a JAR file.

To package - Right Click on project -> Run As -> Maven package

## Running the tests

Right Click on project -> Run As -> Maven test


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

