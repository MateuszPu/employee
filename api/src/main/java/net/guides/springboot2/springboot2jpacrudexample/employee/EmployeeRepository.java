package net.guides.springboot2.springboot2jpacrudexample.employee;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EmployeeRepository extends MongoRepository<Employee, String> {

}
