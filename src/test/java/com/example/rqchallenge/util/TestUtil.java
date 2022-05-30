package com.example.rqchallenge.util;

public class TestUtil {
    public static String getEmptyEmployee(){
        return "{status: \"success\",data: {},message: \"Successfully! Record has been fetched.\"}";
    }

    public static String getEmployee(){
        return "{status: \"success\",data: {id: 1, employee_name: \"Tiger Nixon\",employee_salary: 320800,employee_age: 61,profile_image: \"\"},message: \"Successfully! Record has been fetched.\"}";
    }

    public static String getEmployees_empty_list(){
        return "{status: \"success\",data: [],message: \"Successfully! Record has been fetched.\"}";

    }
    public static String getEmployees(){
        return "{status: \"success\",data: [{id: 1, employee_name: \"Tiger Jcb\",employee_salary: 320800,employee_age: 61,profile_image: \"\"},{id: 2, employee_name: \"Tiger Kaka\",employee_salary: 3100000,employee_age: 61,profile_image: \"\"},{id: 3, employee_name: \"John Nixon\",employee_salary: 3300000,employee_age: 61,profile_image: \"\"}],message: \"Successfully! Record has been fetched.\"}";

    }
}
