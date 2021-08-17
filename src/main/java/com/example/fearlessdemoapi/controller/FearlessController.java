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

    @GetMapping("/getCurrentItems")
    public ResponseEntity<?> getCurrentItems(){
        List<Item> currentItems = new ArrayList<>();
        try {
             currentItems = (List<Item>) fearlessInventoryService.getCurrentItems();
        }catch (Exception e){
            log.error("Error retrieving all items from inventory with error: {}",e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Succefully retrieved {} items from inventory",currentItems.size());
        return new ResponseEntity<>(currentItems,HttpStatus.OK);
    }

    @PostMapping(value = "/saveItem",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveItem(@RequestBody Item item){
        try {
            fearlessInventoryService.saveItem(item);
        } catch (Exception e){
            log.info("Error saving item to DB with error: {}",e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/setItems",consumes = "application/json")
    public ResponseEntity setItems(@RequestBody List<Item> items){
        log.info("Setting new list of items to inventory");
        try{
            fearlessInventoryService.deleteAll();
            fearlessInventoryService.saveAll(items);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity deleteItems(){
        try{
            fearlessInventoryService.deleteAll();
        } catch (Exception e){
            log.error("Unable to delete all items from inventory");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id){
        try{
            fearlessInventoryService.deleteById(id);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/getItem/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id){
        Item item = new Item();
        try{
            item =fearlessInventoryService.findById(id);
            if(item==null){
                log.info("No item found with id: {}",id);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e){
            log.error("Unable to find by id {}", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(item,HttpStatus.OK);
    }



}
