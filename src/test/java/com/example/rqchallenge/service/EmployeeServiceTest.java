package com.example.rqchallenge.service;

import com.example.rqchallenge.client.DummyClient;
import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.exception.EmployeeNotFoundException;
import com.example.rqchallenge.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private DummyClient dummyClient;
    EmployeeService employeeService;
    @BeforeEach
    void setUp(){
        dummyClient = Mockito.mock(DummyClient.class);
        employeeService = new EmployeeService(dummyClient);
    }

    @Test
    void getAllEmployees_success(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees());
        final List<Employee> allEmployees = employeeService.getAllEmployees();
        Assertions.assertEquals(3,allEmployees.size());
    }

    @Test
    void getAllEmployees_no_employee(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees_empty_list());
        final List<Employee> allEmployees = employeeService.getAllEmployees();
        Assertions.assertEquals(0,allEmployees.size());
    }

    @Test
    void getAllEmployees_exception(){
        when(dummyClient.getAllEmployees()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class,() -> employeeService.getAllEmployees());
    }

    @Test
    void findEmployeesById_success(){
        when(dummyClient.getEmployeeById("1")).thenReturn(TestUtil.getEmployee());
        final Employee employeesById = employeeService.findEmployeesById("1");
        Assertions.assertNotNull(employeesById);
    }

    @Test
    void findEmployeesById_invalid_id(){
        when(dummyClient.getEmployeeById("100")).thenReturn(TestUtil.getEmptyEmployee());
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.findEmployeesById("100"));
    }

    @Test
    void searchByName_match_found(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees());
        final List<Employee> allEmployees = employeeService.searchByName("Tiger");
        Assertions.assertEquals(2,allEmployees.size());
    }

    @Test
    void searchByName_no_match_found(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees());
        final List<Employee> allEmployees = employeeService.searchByName("kk");
        Assertions.assertEquals(0,allEmployees.size());
    }

    @Test
    void getHighestSalaryOfEmployees_found(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees());
        final int highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees();
        Assertions.assertEquals(3300000,highestSalaryOfEmployees);
    }

    @Test
    void getHighestSalaryOfEmployees_no_employee_found(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees_empty_list());
        final int highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees();
        Assertions.assertEquals(0,highestSalaryOfEmployees);
    }

    @Test
    void top10HighestEarningEmployeeNames_found(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees());
        final List<String> top10HighestEarningEmployeeNames = employeeService.getTop10HighestEarningEmployeeNames();
        Assertions.assertEquals(3,top10HighestEarningEmployeeNames.size());
    }

    @Test
    void top10HighestEarningEmployeeNames_no_employee_found(){
        when(dummyClient.getAllEmployees()).thenReturn(TestUtil.getEmployees_empty_list());
        final List<String> top10HighestEarningEmployeeNames = employeeService.getTop10HighestEarningEmployeeNames();
        Assertions.assertEquals(0,top10HighestEarningEmployeeNames.size());
    }

    @Test
    void createEmployee_success(){
        Employee employee = new Employee(1,"testt",4800000,30,"");
        when(dummyClient.saveEmployee(Mockito.any(Map.class))).thenReturn(TestUtil.getEmployee());
        final Employee employee1 = employeeService.createEmployee(employee);
        Assertions.assertNotNull(employee1);
    }

    @Test
    void createEmployee_fail(){
        Employee employee = new Employee(1,"testt",4800000,30,"");
        when((dummyClient).saveEmployee(Mockito.any(Map.class))).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeService.createEmployee(employee));
    }

    @Test
    void deleteEmployee_success(){
        doNothing().when(dummyClient).deleteEmployeeById("1");
        employeeService.deleteEmployeeById("1");
        Mockito.verify(dummyClient,Mockito.times(1)).deleteEmployeeById("1");
    }

    @Test
    void deleteEmployee_fail(){
        doThrow(new RuntimeException()).when(dummyClient).deleteEmployeeById("1");
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeService.deleteEmployeeById("1"));
    }
}
