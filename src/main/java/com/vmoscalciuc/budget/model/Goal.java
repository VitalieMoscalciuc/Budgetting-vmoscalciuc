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
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="goal_id",nullable = false,unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "goal_date")
    private Date goalDate;

    @Column(name = "goal_amount")
    private Double amount;

    @Column(name = "investment", columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
    private double investment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
