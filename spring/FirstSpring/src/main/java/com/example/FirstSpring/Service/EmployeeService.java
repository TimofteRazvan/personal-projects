package com.example.FirstSpring.Service;

import com.example.FirstSpring.Entity.Employee;
import com.example.FirstSpring.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    List<Employee> employeeList = new ArrayList<Employee>(Arrays.asList(
            new Employee(1, "First", "Bucuresti"),
            new Employee(2, "Second", "Hunedoara")
    ));

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees () {
        return employeeRepository.findAll();
        //return employeeList;
    }

    public Employee getEmployee(int id) {
        /*
        return employeeList.stream()
                .filter(e -> (e.getId() == id))
                .findFirst().get();
         */
        //return employeeRepository.getById(id);
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found.\n"));
    }

    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(employeeRepository.getById(id));
    }
}
