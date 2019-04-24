package com.bruno.data.auth;

import com.bruno.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends AbstractEntity {

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany
    private List<Role> roles;
}
