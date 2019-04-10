package com.test.csv.repisitory;

import com.test.csv.model.UserForm;
import org.springframework.data.repository.CrudRepository;

public interface UserFormRepository extends CrudRepository<UserForm, Integer> {
}