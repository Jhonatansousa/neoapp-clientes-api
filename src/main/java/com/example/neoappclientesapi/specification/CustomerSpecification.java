package com.example.neoappclientesapi.specification;

import com.example.neoappclientesapi.entity.Customer;
import com.example.neoappclientesapi.entity.CustomerStatus;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Customer> hasCpf(String cpf) {
        return (root, query, cb) ->
                cpf == null ? null : cb.equal(root.get("cpf"), cpf);
    }

    public static Specification<Customer> hasEmail(String email) {
        return (root, query, cb) ->
                email == null ? null : cb.equal(root.get("email"), email);
    }

    public static Specification<Customer> hasStatus(CustomerStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
