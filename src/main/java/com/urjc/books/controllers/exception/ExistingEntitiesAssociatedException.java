package com.urjc.books.controllers.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ExistingEntitiesAssociatedException extends Exception {

    private String associatedEntity;

    public ExistingEntitiesAssociatedException(String associatedEntity) {
        super("The entity you are trying to delete has " + associatedEntity + "associated.");
        this.associatedEntity = associatedEntity;
    }

    public String getAssociatedEntity() {
        return associatedEntity;
    }
}
