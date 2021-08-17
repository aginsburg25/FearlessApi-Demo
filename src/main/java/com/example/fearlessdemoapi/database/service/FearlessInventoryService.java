
package com.example.fearlessdemoapi.database.service;

import com.example.fearlessdemoapi.database.model.Item;
import com.example.fearlessdemoapi.database.repository.FearlessInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FearlessInventoryService {

    @Autowired
    FearlessInventoryRepository fearlessInventoryRepository;

    public void saveItem(Item item){
        log.info("Saving item with id: {}...",item.getId() );
        fearlessInventoryRepository.save(item);
        log.info("Successfully saved item with id: {}",item.getId());

    }

    public Iterable<Item> getCurrentItems(){
        log.info("Retrieving all item in current inventory..");
        return fearlessInventoryRepository.findAll();
    }

    public void deleteAll() {
        log.info("Deleting all items in inventory...");
        fearlessInventoryRepository.deleteAll();
        log.info("Succesfully deleted all items in inventory");
    }

    public void saveAll(List<Item> items) {
        log.info("Saving {} new items to inventory...", items.size());
        fearlessInventoryRepository.saveAll(items);
        log.info("Succesfully saved {} new items to inventory", items.size());
    }

    public void deleteById(Integer id) {
        fearlessInventoryRepository.deleteById(id);
        log.info("Succesfully deleted item with id: " + id);
    }

    public Item findById(Integer id) {
        Optional<Item> item = fearlessInventoryRepository.findById(id);
        log.info("Succesfully deleted item with id: " + id);
        if(item.isEmpty()){
            return null;
        }
        return item.get();
    }
}

