The Bank Project ( Backend )

To get the backend project, enter the command below on the command line or use the link with a git software providing GUI.

	git clone https://github.com/cybora/bank_project


To run the project, go to the main folder and type the command below

	mvn spring-boot:run

To use the application, use a browser or a Postman like application in order to make the rest requests.
Assuming the port used by the backend application is 8080, you may change it via the application.properties file in the resource file of the project )

To get the information of a customer, use the rest command below.
Replace the CUSTOMER_ID with the id of the customer you want to get the details about. 

http://localhost:8080/customerDetails/CUSTOMER_ID

To create a secondary account of a customer, use the rest command below.
Replace the CUSTOMER_ID with the id of the customer you want to create a secondary account.
Replace the INITIAL_CREDIT with the amount of the credit you want to make transaction to the secondary account on creation.

http://localhost:8080/createSecondaryAccount/CUSTOMER_ID/INITIAL_CREDIT

You can track the record from the h2 console via accessing " http://localhost:8080/h2-console "
