package com.ideas2it.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represnts a sport that can be associated with employees.
 * A sport has a unique Identifier, a name and a list of employees who participate in it.
 * @author  Kishore
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "SPORT")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "sports")
    private Set<Employee> employees = new HashSet<>();

}
