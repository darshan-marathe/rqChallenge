package com.example.rqchallenge.employees;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.exception.EmployeeNotFoundException;
import com.example.rqchallenge.exception.ServiceError;
import com.example.rqchallenge.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class EmployeeController implements IEmployeeController{
    private static final Logger LOGGER = LogManager.getLogger(EmployeeController.class);
   @Autowired
    EmployeeService employeeService;
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws RuntimeException {
        LOGGER.info("START : getAllEmployees");
        try {
            final List<Employee> allEmployees = employeeService.getAllEmployees();
            LOGGER.info("END : getAllEmployees");
            return ResponseEntity.ok(allEmployees);
        }catch (RuntimeException e){
            LOGGER.error("FAIL : getAllEmployees");
            throw new ServiceError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        LOGGER.info("START : getEmployeesByNameSearch {}",searchString);
        try{
            final List<Employee> employees = employeeService.searchByName(searchString);
            LOGGER.info("End : Employee : {} matches with name {}",employees.size(),searchString);
            return ResponseEntity.ok(employees);
        }catch (RuntimeException e){
            LOGGER.error("FAIL : getEmployeesByNameSearch");
            throw new ServiceError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        LOGGER.info("START : getEmployeeById {}",id);
        try {
            final Employee employee = employeeService.findEmployeesById(id);
            LOGGER.info("End : Employee found {}", employee);
            return ResponseEntity.ok(employee);
        }catch (EmployeeNotFoundException ex){
            LOGGER.error("Employee not found {}",id);
            throw ex;
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        LOGGER.info("START : getHighestSalaryOfEmployees ");
        try {
            final int highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees();
            LOGGER.info("Highest Salary {}",highestSalaryOfEmployees);
            return ResponseEntity.ok(highestSalaryOfEmployees);
        }catch (RuntimeException e){
            throw new ServiceError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        LOGGER.info("START : getTopTenHighestEarningEmployeeNames");
        try {
            final List<String> top10HighestEarningEmployeeNames = employeeService.getTop10HighestEarningEmployeeNames();
            LOGGER.info("END : get Top Ten Highest Earning Employee Names {}",top10HighestEarningEmployeeNames);
            return ResponseEntity.ok(top10HighestEarningEmployeeNames);
        }catch (RuntimeException e){
            throw new ServiceError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity.BodyBuilder createEmployee(Employee employee) {
        LOGGER.info("START : createEmployee {}",employee);
        try {
            final Employee employee1 = employeeService.createEmployee(employee);
            URI uri = new URI("/"+employee1.getId());
            LOGGER.info("END : Employee created with id {}", employee1);
            return ResponseEntity.created(uri);
        }catch (RuntimeException | URISyntaxException e){
            throw new ServiceError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity.BodyBuilder deleteEmployeeById(String id) {
        try {
            LOGGER.info("START : deleteEmployeeById",id);
            employeeService.deleteEmployeeById(id);
            LOGGER.info("END : Employee deleted with id {}",id);
            return ResponseEntity.ok();
        }catch (RuntimeException e){
                throw new ServiceError(e.getMessage());
            }

    }
}
