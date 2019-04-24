package com.bruno.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

public abstract class AbstractEntity extends AbstractPersistable<Integer> {

    @Override
    @JsonIgnore
    public boolean isNew() {
        return super.isNew();
    }
}
