package com.bruno.api;

import com.bruno.data.example.Example;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AGENT', 'ROLE_CUSTOMER')")
public interface ExampleRepository extends Repository<Example, Integer> {

    @PreAuthorize("hasAnyRole('CREATE_EXAMPLE', 'UPDATE_EXAMPLE')")
    Example save(Example example);

    @PreAuthorize("hasRole('READ_EXAMPLE')")
    List<Example> findAll();

    @PreAuthorize("hasRole('DELETE_EXAMPLE')")
    void deleteById(Integer id);

    @RestResource(exported = false)
    Optional<Example> findById(Integer id);
}
