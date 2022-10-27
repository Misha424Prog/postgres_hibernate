package com.example.postgres_hibernate.postgres_hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (schema="new_hibernate", name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String lastName;
    private int age;
    private long sumBuy;

    public Customer(String name, String lastName, int age, long sumBuy) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.sumBuy = sumBuy;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getSumBuy() {
        return sumBuy;
    }

    public void setSumBuy(long sumBuy) {
        this.sumBuy = sumBuy;
    }
}
