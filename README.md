# SHACL validation for the STO data

This project uses Jena and the SHACL language to validate the RDF data based on the STO ontology.


## Running the project
To run the examples, use the file config.ttl in the main directory of the project. 
In this file you have to configure:
1 - the inputData, i.e., the path to a turtle file with the STO data; 
2 - the inputShape, i.e., the path to a turtle file where the SHACL constraints are defined; and
3 - the outputReport, i.e., the path where the turtle file of the report will be generated. 

An example is show below:
```
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .
@prefix uri:     <http://uri4uri.net/vocab.html/#>
@prefix sto:     <https://w3id.org/i40/sto#>.
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .

sto:conf 
     rdfs:label "General Configuration for the STO SHACL validation"@en ;
     uri:inputData "file:E:/experiments/shacl-sto/resources/sto.ttl";
     uri:inputShape "file:E:/experiments/shacl-sto/resources/stoShape.ttl";
     uri:outputReport "E:/experiments/shacl-sto/resources/report.ttl";
.    


