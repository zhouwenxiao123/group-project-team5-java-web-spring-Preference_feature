package com.example.mealit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Preferences")
public class Preferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preferences_id")
    private Long preferencesId;

    @Column
    private Boolean gluten;
    @Column
    private Boolean vegan;
    @Column
    private Boolean vegetarian;
    @Column
    private Boolean nuts;
    @Column
    private Boolean seafood;
    @Column
    private Boolean shellfish;
    @Column
    private Boolean milk;
    @Column
    private Boolean eggs;
    @Column
    private Boolean peanuts;
    @Column
    private Boolean wheat;
    @Column
    private Boolean soybeans;

    @OneToOne(
            //cascade = CascadeType.ALL,  这里不需要级联User
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;



}
