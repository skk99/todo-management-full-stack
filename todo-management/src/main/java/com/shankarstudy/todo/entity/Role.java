package com.shankarstudy.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Instead of this @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, we could also use one annotation
// @Data lombok annotation, but it also generates toString, hasCode, equals methods as well along with getter,
// setter and constructors. So we will use individual annotation here
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
