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
     * Spring Data JPA custom query using JPQL with index parameters
     */
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Spring Data JPA custom query using JPQL with named parameters
     */
    @Query("select e from Employee e where e.firstName =:firstName and e.email =:email")
    Employee findByFirstNameAndEmail(@Param("firstName") String firstName, @Param("email") String email);

    /**
     * Spring Data JPA custom query using native SQL with indexed parameters
     */
    @Query(value = "select * from employees e where e.last_name = ?1 and e.email = ?2", nativeQuery = true)
    Employee findByLastNameAndEmail(String lastName, String email);

    /**
     * Spring Data JPA custom query using native SQL with named parameters
     */
    @Query(value = "select * from employees e where e.first_name =:firstName " +
            "and e.last_name =:lastName and e.email =:email", nativeQuery = true)
    Employee findByFirstNameAndLastNameAndEmail(@Param("firstName") String firstName,
                                                @Param("lastName") String lastName, @Param("email") String email);


}
