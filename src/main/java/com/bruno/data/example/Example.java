package com.bruno.data.example;

import com.bruno.data.AbstractEntity;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Example extends AbstractEntity {

    private String name;
}
