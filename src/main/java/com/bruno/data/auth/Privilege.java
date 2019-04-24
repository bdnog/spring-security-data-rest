package com.bruno.data.auth;

import com.bruno.data.AbstractEntity;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Privilege extends AbstractEntity {

    private String name;
}
