package com.ideas2it.employeemanagement.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents an Employee that can  associated with department & sports.
 * Contains details like id, name, date of birth (dob), department, email, active status and associated sports.
 * @author  Kishore
 * @version 1.0
 */
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private LocalDate dob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "emailId", unique = true)
    private String emailId;

    @Column(name = "isActive")
    private boolean isActive;

}
