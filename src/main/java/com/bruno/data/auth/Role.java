package com.bruno.data.auth;

import com.bruno.data.AbstractEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends AbstractEntity {

    private String name;

    @ManyToMany
    private List<Privilege> privileges;
}
