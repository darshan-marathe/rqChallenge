package com.example.rqchallenge.service;

import com.example.rqchallenge.client.DummyClient;
import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.employees.EmployeeController;
import com.example.rqchallenge.exception.EmployeeNotFoundException;
import com.example.rqchallenge.mapper.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private static final Logger LOGGER = LogManager.getLogger(EmployeeService.class);
    DummyClient dummyClient;

    @Autowired
    public EmployeeService(DummyClient dummyClient) {
        this.dummyClient = dummyClient;
    }

    public List<Employee> getAllEmployees(){
        LOGGER.debug("getAllEmployees()");
        final String allEmployees = dummyClient.getAllEmployees();
        return Mapper.mapToEmployees(allEmployees);
    }

    public Employee findEmployeesById(String employeeId){
        LOGGER.debug("findEmployeesById ",employeeId);
        final String employeeJson = dummyClient.getEmployeeById(employeeId);
        final Employee employee = Mapper.mapToEmployee(employeeJson);
        LOGGER.debug("employee {} for id : {} ",employee, employeeId);
        if(null != employee)
            return employee;
        throw new EmployeeNotFoundException(employeeId);
    }

    public List<Employee> searchByName(String searchString){
        LOGGER.debug("searchByName {}",searchString);
        final List<Employee> collect = getAllEmployees().stream()
                .filter(emp -> emp.getName().contains(searchString))
                .collect(Collectors.toList());
        LOGGER.debug("employee count {}, matches name {}",collect.size(),searchString);
        return collect;
    }

    public int getHighestSalaryOfEmployees(){
        LOGGER.debug("getHighestSalaryOfEmployees ");
        final List<Employee> collect = getAllEmployees().stream().sorted().limit(1).collect(Collectors.toList());
        LOGGER.debug("employee with Highest Salary {}",collect);
        return collect.isEmpty() ? 0 : collect.get(0).getSalary();
    }

    public List<String> getTop10HighestEarningEmployeeNames(){
        LOGGER.debug("getTop10HighestEarningEmployeeNames");
        final List<Employee> collect = getAllEmployees().stream().sorted().limit(10).collect(Collectors.toList());
        return collect.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

    }

    public Employee createEmployee(Employee employee) {
        LOGGER.debug("createEmployee {}",employee);
        Map<String,Object> map = Mapper.toMap(employee);
        final String saveEmployee = dummyClient.saveEmployee(map);
        final Employee employee1 = Mapper.mapToEmployee(saveEmployee);
        LOGGER.debug("Employee created with id : {}",employee.getId());
        return employee1;
    }

    public void deleteEmployeeById(String id) {
        LOGGER.debug("deleteEmployeeById {}",id);
        dummyClient.deleteEmployeeById(id);
        LOGGER.debug("employee deleted {}",id);
    }
}
