This sample can be used to connect to redis in a PCf environment using Auto discover process
Uses Spring Redis library to push data in to redis cache
uses Spring data list operations to insert data into cache
This stores customer history as a list in a customer key


To Assemble Run 
   MVN Clean install
   
Use CF push to deploy into PCF and bind the application to redis market place service

There is a bulk upload method to insert data and redis usage method to collect usage statistics
