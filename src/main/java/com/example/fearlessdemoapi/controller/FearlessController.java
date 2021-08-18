package com.example.fearlessdemoapi.controller;

import com.example.fearlessdemoapi.database.model.Item;
import com.example.fearlessdemoapi.database.service.FearlessInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/items")
@Slf4j
public class FearlessController {

    @Autowired
    FearlessInventoryService fearlessInventoryService;

    /**
     * This method will return a list of all
     * current items in the inventory DB.
     * If the DB is empty will return No Content
     * @return ResponseEntity
     */
    @GetMapping("/getCurrentItems")
    public ResponseEntity<?> getCurrentItems(){
        List<Item> currentItems = new ArrayList<>();
        try {
             currentItems = (List<Item>) fearlessInventoryService.getCurrentItems();
             if(currentItems.isEmpty()){
                 return new ResponseEntity<>("Inventory DB is currently empty",HttpStatus.NO_CONTENT);
             }
        }catch (Exception e){
            log.error("Error retrieving all items from inventory with error: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Successfully retrieved {} items from inventory",currentItems.size());
        return new ResponseEntity<>(currentItems,HttpStatus.OK);
    }

    /**
     * This method will accept a list of json objects
     * and set that as the current items in inventory.
     * To do so it will first delete all items and then
     * set to the new items
     * @param items List<items>
     * @return ResponseEntity
     */
    @PostMapping(value = "/setItems",consumes = "application/json")
    public ResponseEntity<?> setItems(@RequestBody List<Item> items){
        log.info("Setting new list of items to inventory");
        try{
            fearlessInventoryService.deleteAll();
            fearlessInventoryService.saveAll(items);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method will accept an item and save it in
     * the inventory DB
     * @param item Item
     * @return ResponseEntity
     */
    @PostMapping(value = "/saveItem",produces = MediaType.APPLICATION_JSON_VALUE,consumes = "application/json")
    public ResponseEntity<?> saveItem(@RequestBody Item item){
        try {
            fearlessInventoryService.saveItem(item);
        } catch (Exception e){
            log.info("Error saving item to DB with error: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }



    /**
     * This method will delete all items currently in
     * the inventory
     * @return ResponseEntity
     */
    @DeleteMapping("/removeAll")
    public ResponseEntity<?> deleteItems(){
        try{
            fearlessInventoryService.deleteAll();
        } catch (Exception e){
            log.error("Unable to delete all items from inventory");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method will delete an item by ID
     * if any error occurrs will return 500
     * @param id id
     * @return ResponseEntity
     */
    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        try{
            fearlessInventoryService.deleteById(id);
        }catch(Exception e){
            log.error("Unable to delete item with id: {}",id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * This method will query the DB by ID and return
     * that item
     * If an item with that ID was not found will return
     * 404 - for item not found
     * @param id ID
     * @return ResponseEntity
     */
    @GetMapping("/getItem/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id){
        Item item = new Item();
        try{
            item =fearlessInventoryService.findById(id);
            if(item==null){
                log.info("No item found with id: {}",id);
                return new ResponseEntity<>("Item Not Found",HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            log.error("Unable to find by id {}", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(item,HttpStatus.OK);
    }



}
