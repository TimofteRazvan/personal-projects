package com.example.FirstSpring.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String city;

    // ALL propagates all operations to the children (AKA saves & deletes children along with parent)
    // PERSIST propagates only the save operations, doesn't allow deletion on parent because of foreign key constraints
    // REMOVE propagates the remove operations; when combined wtih PERSIST basically works like ALL
    // Can specify multiple CASCADE options, like both PERSIST and REMOVE

    // FETCH TYPE is EAGER by default because ONEtoONE relation
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="fk_spouse")
    private Spouse spouse;

    // EAGER: Fetches even if not called for
    // A:
    // 1. No delay in data loading
    // D:
    // 1. Higher load time (if lots of data)
    // 2. Might trigger high memory usage (stored in memory irrespective of usage)

    // LAZY: Fetches only if called for
    // A:
    // 1. Seems fast by default
    // B:
    // 2. Might experience load time when trying to access dependencies
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "employee_project",
            joinColumns = @JoinColumn(name = "fk_employee"),
            inverseJoinColumns = @JoinColumn(name = "fk_project"))
    private Set<Project> projects = new HashSet<>();

    public Employee() {

    }

    public Employee(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Employee(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Spouse getSpouse() {
        return spouse;
    }

    public void setSpouse(Spouse spouse) {
        this.spouse = spouse;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getEmployees().remove(project);
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.getEmployees().add(this);
    }

    public void addAddress(Address address) {
        this.addresses = new ArrayList<>();
        this.addresses.add(address);
        address.setEmployee(this);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
        address.setEmployee(null);
    }
}
