package com.github.vasyapogoriliy.storage.controller;

import com.github.vasyapogoriliy.storage.model.Storage;
import com.github.vasyapogoriliy.storage.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/storages")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("")
    public ResponseEntity<List<Storage>> getAllStorages() {
        List<Storage> storages = storageService.getAll();

        if (storages.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(storages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Storage> getStorage(@PathVariable("id") Long id) {
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Storage storage = storageService.getById(id);

        if (storage == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Storage> addStorage(@RequestBody @Valid Storage storage) {
        if (storage == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!storageService.save(storage)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(storage, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Storage> updateStorage(@PathVariable("id") Long id,
                                                 @RequestBody @Valid Storage storage) {
        if (storage == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!storageService.update(storage, id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Storage> deleteStorage(@PathVariable("id") Long id) {
        if (storageService.getById(id) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        storageService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
