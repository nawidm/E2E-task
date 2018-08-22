About

Project scans a configured directory for file types with a specific mime type which at the moment is only configured for excel files.
It then gathers the relevant vehicle registration from the file and inputs this into the Gov website for vehicle enquiries. It then tests
if the vehicle details on the website match with the details in the file. It does have support for multiple rows of vehicle details in a single file
but doesnt yet work for multiple files. 

Setup

IMPORTANT - If you are using an operating system other than Mac OS, you will have to replace the chromedriver with the correct one
for your operating system and you will also have to update the path for this chromedriver on line 48 in StepDefinition.java in package src/main/java/stepDefinitions.

When you have loaded project into your IDE, right click the project and run maven install.

To run the test, right click on CucumberRunner.java in package src/test/java/testRunner and run as JUnit test.

