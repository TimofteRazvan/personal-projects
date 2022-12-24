package com.example.FirstSpring;

import com.example.FirstSpring.Entity.Address;
import com.example.FirstSpring.Entity.Employee;
import com.example.FirstSpring.Entity.Project;
import com.example.FirstSpring.Entity.Spouse;
import com.example.FirstSpring.Service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication

@SpringBootApplication
public class FirstSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringApplication.class, args);
	}

	// Used to show the SQL queries in the console
	@Bean
	public CommandLineRunner initialCreate(EmployeeService employeeService) {
		return (args) -> {
			Address address1 = new Address("Line1", "Line2", "ZipCode1", "City1", "State1", "Country1");
			Project project1 = new Project("Name1", "Client Name1");
			Spouse spouse1 = new Spouse("Name1", "Mobile1", 20);

			Employee employee = new Employee("Employee1", "City1");
			employee.addProject(project1);
			employee.addAddress(address1);
			employee.setSpouse(spouse1);

			employeeService.createEmployee(employee);

			System.out.println("Getting employee...\n");
			Employee employee1 = employeeService.getEmployee(1);
		};
	}
}
