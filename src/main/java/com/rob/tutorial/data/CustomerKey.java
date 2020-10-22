package com.rob.tutorial.data;

import java.io.Serializable;
import java.util.Objects;

public class CustomerKey implements Serializable {

    public static final long serialVersionUID = 933739576868553382l;

    private long id;
    private String country;

    public CustomerKey(long id, String country) {
        this.id = id;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerKey that = (CustomerKey) o;
        return id == that.id &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }

}
