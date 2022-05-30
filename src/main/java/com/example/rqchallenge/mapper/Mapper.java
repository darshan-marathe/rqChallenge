package com.example.rqchallenge.mapper;

import com.example.rqchallenge.dto.Employee;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {

     public static final String DATA = "data";
     public static final String EMPLOYEE_NAME = "employee_name";
     public static final String EMPLOYEE_SALARY = "employee_salary";
     public static final String EMPLOYEE_AGE = "employee_age";
     public static final String PROFILE_IMAGE = "profile_image";
     public static final String ID = "id";

     public static Employee mapToEmployee(String employee) {
          JSONObject jsonObject = new JSONObject(employee);
          final JSONObject data = jsonObject.getJSONObject(DATA);
          if(!data.isEmpty())
               return getEmployee(data);
          return null;
     }

     private static Employee getEmployee(JSONObject data) {
          final int id = data.getInt(ID);
          final String employeeName = data.getString(EMPLOYEE_NAME);
          final int employeeSalary = data.getInt(EMPLOYEE_SALARY);
          final int employeeAge = data.getInt(EMPLOYEE_AGE);
          final String profileImage = data.getString(PROFILE_IMAGE);
          return new Employee(id, employeeName, employeeSalary, employeeAge,profileImage);
     }

     public static List<Employee> mapToEmployees(String employees) {
          List<Employee> employeeList = new ArrayList<Employee>();
          final JSONObject jsonObject = new JSONObject(employees);
          final JSONArray jsonArray = jsonObject.getJSONArray(DATA);
          for (int i = 0; i < jsonArray.length(); i++){
               Employee employee = getEmployee(jsonArray.getJSONObject(i));
               employeeList.add(employee);
          }
          return employeeList;
     }

     public static Map<String, Object> toMap(Employee employee) {
          Map<String,Object> map = new HashMap<>();
          map.put("name",employee.getName());
          map.put("age",employee.getAge());
          map.put("salary",employee.getSalary());
          return map;
     }
}
