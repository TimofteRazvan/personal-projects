package com.example.FirstSpring.Service;

import com.example.FirstSpring.Entity.Address;
import com.example.FirstSpring.Entity.Employee;
import com.example.FirstSpring.Entity.Project;
import com.example.FirstSpring.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Transactional
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
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found.\n"));
        // Will lazily load AFTER fetching the Employee
        System.out.println("Fetching Projects in Service Class...\n");
        Set<Project> projects = employee.getProjects();
        for (Project project : projects) {
            System.out.println(project.getClientName());
        }
        return employee;
    }

    public void createEmployee(Employee employee) {
        ArrayList<Address> addressArrayList = new ArrayList<>();
        for (Address address : employee.getAddresses()) {
            addressArrayList.add(new Address(address.getLine1(),
                    address.getLine2(),
                    address.getZipCode(),
                    address.getCity(),
                    address.getState(),
                    address.getCountry(),
                    employee));
        }
        employee.setAddresses(addressArrayList);
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(employeeRepository.getById(id));
    }
}
