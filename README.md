# Backend Engineering Challenge


This is a Java implementation of Unbabel's Backend Engineering Challenge.

## Build

	./mvnw clean package
	
To build this application you need to have Java 8 SDK installed and the JAVA_HOME environment variable pointing the the folder of that installation.

## Run

	java -jar target/bec-0.0.1-SNAPSHOT.jar --input_file=events.json --window_size=10
	
The application will create an output file named `out.json` with the result, in the current folder.

There's an optional parameter to explicitly set the output file:

	java -jar target/bec-0.0.1-SNAPSHOT.jar --input_file=events.json --output_file=/bla/bla/bla/out.json --window_size=10
	
## Tests

Automatic tests run when the application is built, but they can also run on demand with the following command:

	./mvnw test
	
## Considerations

+ There's an optional parameter to explicitly set the output file
+ The application is prepared to consume an input file with events that are NOT ordered in time (in other words, ordered by the timestamp attribute)
+ `pt.dcs.unbabel.bec.BecApplication` is the main class and where it all begins

## Extras

