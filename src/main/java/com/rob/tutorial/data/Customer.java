package com.rob.tutorial.data;

import java.io.Serializable;

public class Customer implements Serializable {

    public static final long serialVersionUID = 722750664185503982l;

    private CustomerKey key;
    private String firstName;
    private String lastName;

    private Integer age;

    public Customer(CustomerKey key, String firstName, String lastName, int age) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public CustomerKey getKey() {
        return key;
    }

    public void setKey(CustomerKey key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
