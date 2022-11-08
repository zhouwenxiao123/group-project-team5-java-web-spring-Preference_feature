package com.example.mealit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"User\"")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private Long phoneNumber;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(
            mappedBy = "user"
    )
    private Preferences preferences;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }
    )
    private List<Role> roles = new ArrayList<>();

    private boolean enabled;
    @OneToMany(targetEntity= Order.class,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Order> orders ;

    public void addOrders(Order order){
        if (orders == null){
            orders = new ArrayList<>();
        }
        orders.add(order);
    }

    @OneToMany(targetEntity= GroceryList.class,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<GroceryList> groceryLists;

    public void addGroceryList(GroceryList groceryList){
        if(groceryList == null){
            groceryList = new GroceryList();
        }
        groceryLists.add(groceryList);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
