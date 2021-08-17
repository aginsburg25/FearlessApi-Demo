
package com.example.fearlessdemoapi.database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Setter
@Getter
@Entity (name = "FearlessInventory")
public class Item {


    @Id
    @Column
    private Integer id;

    @Column
    private String name;

}

