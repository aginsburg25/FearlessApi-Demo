
package com.example.fearlessdemoapi.database.repository;

import com.example.fearlessdemoapi.database.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FearlessInventoryRepository extends CrudRepository<Item,Integer> {

}

