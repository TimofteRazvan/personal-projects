package com.example.FirstSpring.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="spouse")
public class Spouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private int age;

    @OneToOne(mappedBy = "spouse")
    private Employee employee;

    public Spouse() {

    }

    public Spouse(String name, String phone, int age) {
        this.name = name;
        this.phone = phone;
        this.age = age;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
