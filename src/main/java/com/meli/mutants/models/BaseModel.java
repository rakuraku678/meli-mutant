package com.meli.mutants.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseModel {

	@Id
	private ObjectId id;

	@JsonProperty(value = "Id")
	public String getIdAsStr() {
		return id != null ? id.toString() : null;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}