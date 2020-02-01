package com.meli.mutants.config;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class MongoConfiguration {


    @Value("${mongo.endpoints}")
    private String mongoEndpoint;

    @Value("${mongo.dbname}")
    private String mongoDbName;

    @Bean
    public Datastore mongoDbDataStore() throws UnknownHostException {
    	System.out.println("******************************************************************************************");
    	System.out.println("*********************CONECTANDO A BASE DE DATOS*******************************************");
    	System.out.println("******************************************************************************************");
    	System.out.println("******************************************************************************************");
    	
        System.out.println("Connecting with MongoDB. dbname=" + mongoDbName + ", address="  + mongoEndpoint);
        MongoClientURI uri = new MongoClientURI(mongoEndpoint);

        MongoClient client = new MongoClient(uri);
        Datastore ds = new Morphia().mapPackage("com.meli.mutants.models").createDatastore(client, mongoDbName);
        ds.ensureIndexes();
        return ds;
    }
}