package com.example.postgres_hibernate.postgres_hibernate.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema="new_hibernate", name = "purchase")

public class Purchase {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int sum_of_purchase;

    public Purchase(Date date, Customer customer, int sum_of_purchase) {
        this.date = date;
        this.customer = customer;
        this.sum_of_purchase = sum_of_purchase;
    }

    public Purchase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSum_of_purchase() {
        return sum_of_purchase;
    }

    public void setSum_of_purchase(int sum_of_purchase) {
        this.sum_of_purchase = sum_of_purchase;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
