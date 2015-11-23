# Assignment 2 INTROSDE 2015

[Educational porpouses only]

This is the development of the seccond assignment given on the Introduction to Service Design Engineering course at University of Trento during Computer Science MSC.

The basic function is to provide an RESTFUL service that is able to manage simple Person's model of a Health Insurance company (assuming).

## Authors
This project was developed by Charles Ferrari and the  client interface is connecting to Damiano Fosssa server.

[See Damiano's Github repository][1]

### Servers 

* [This server on Heroku] [2]
* [The server used on the Client (Damiano's server))][3]

### Instructions for the Server
 * Git clone the folder to your machine
 * Make sure you have ant installed
 * Go to the folder and in your terminal, type down ant install
 * For using locally, with postman or similar, copy the given url to your tool. 
 ####The basic REST functions are (XML and JSON):
 
 * GET/POST for [address]/person
 * GET/PUT/DELETE for [address]/person/[id]
 * GET [address] /measureTypes
 * GET/POST [address] /person/[id]/[measureType]
 * GET [address] /person/[id]/[measureType]/[measureTypeId]
 
### Instructions for the Client
* After downloading the server
* GO to the client folder (assignment-client)
* Type ant execute.client
 
### Extra information
An instance of the client log is also on the folder, on the root called "client-server.log"
    
[1]: https://github.com/DamianFox/test2
[2]: http://agile-tundra-4340.herokuapp.com/sdelab
[3]: https://secret-bastion-8847.herokuapp.com/sdelab
