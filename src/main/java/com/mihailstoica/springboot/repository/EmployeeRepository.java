package com.mihailstoica.springboot.repository;

import com.mihailstoica.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
/*
    @Query(value = "select e from Employee e where e.email=:word")
    Employee getEmployeeByEmail(@Param("word") String word, Pageable pageable);
*/
    Optional<Employee> findByEmail(String email);

    /**
     * JPQL custom query with index parameters
     */
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * JPQL custom query with named parameters
     */
    @Query("select e from Employee e where e.firstName =:firstName and e.email =:email")
    Employee findByFirstNameAndEmail(@Param("firstName") String firstName, @Param("email") String email);


}