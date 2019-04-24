package com.bruno.data.example;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ExampleEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('CREATE_EXAMPLE')")
    public void handleBeforeCreate(Example example) {
        // since the Repository.save() is used for both creating and updating records, we need to declare
        // this method just to make sure the creation is being performed by a user with the proper permission
    }
}
