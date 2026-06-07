package com.example.FoodDeliveryApp.entity;

import com.example.FoodDeliveryApp.enums.roles;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "users")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    //Builder annotation helps avoid the mistakes in constructor when you didnot give the correct parenthesis in the constructor it gives an error
    // so builder annotation helps automatically  do it easily
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        //column annotaiton for  avoid duplicates by giving the  null,unique value instead of this annotation you can't do that kind of sql constraints
        private String fullName;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String phoneNumber;

        @Enumerated(EnumType.STRING)
        private roles role;

        private boolean enabled = true;
        //this line for  block the user when the user is malpractice or when delivery partner leave leave the company
        //instead of delete

    }

