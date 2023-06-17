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
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="expense_id",nullable = false,unique = true)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "expense_date")
    private Date expenseDate;
    @Column(name = "expense_amount")
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
