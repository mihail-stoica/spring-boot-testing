package com.mihailstoica.springboot.repository;

import com.mihailstoica.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*@Query(value = "select from Employee where Employee.email=:word")
    Employee getEmployeeByEmail(@Param("word") String word, Pageable pageable);*/

    Optional<Employee> findByEmail(String email);
}
