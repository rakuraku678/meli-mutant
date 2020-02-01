package com.meli.mutants.Daos;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.meli.mutants.models.Dna;


@Repository
public class DnaDAO extends BasicDAO<Dna, ObjectId>{

    @Autowired
    protected DnaDAO(Datastore ds) {
        super(ds);
    }
    public Dna findBySequence(String sequence) {
        return createQuery().field("sequence").equal(sequence).get();
    }
    
}