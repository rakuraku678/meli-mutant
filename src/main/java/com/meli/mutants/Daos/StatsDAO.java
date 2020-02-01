package com.meli.mutants.Daos;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.meli.mutants.models.Stats;


@Repository
public class StatsDAO extends BasicDAO<Stats, ObjectId>{

    @Autowired
    protected StatsDAO(Datastore ds) {
        super(ds);
    }

}