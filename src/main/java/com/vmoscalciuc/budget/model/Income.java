package com.vmoscalciuc.budget.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="income_id",nullable = false,unique = true)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "income_date")
    private Date incomeDate;
    @Column(name = "income_amount")
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}