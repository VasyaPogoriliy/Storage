package com.github.vasyapogoriliy.storage.controller;

import com.github.vasyapogoriliy.storage.model.Section;
import com.github.vasyapogoriliy.storage.service.SectionService;
import com.github.vasyapogoriliy.storage.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/storages/{storageId}/sections")
public class SectionController {

    private final StorageService storageService;
    private final SectionService sectionService;

    public SectionController(StorageService storageService, SectionService sectionService) {
        this.storageService = storageService;
        this.sectionService = sectionService;
    }

    @GetMapping("")
    public ResponseEntity<List<Section>> getAllSections(@PathVariable("storageId") Long storageId) {
        if (storageService.getById(storageId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Section> sections = sectionService.getAll(storageId);

        if (sections.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<Section> getSection(@PathVariable("sectionId") Long sectionId,
                                              @PathVariable("storageId") Long storageId) {
        if (storageService.getById(storageId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (sectionId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Section section = sectionService.findByIdAndStorageId(sectionId, storageId);

        if (section == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(section, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Section> addSection(@RequestBody @Valid Section section,
                                              @PathVariable("storageId") Long storageId) {
        if (storageService.getById(storageId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (section == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!sectionService.save(section, storageId)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(section, HttpStatus.CREATED);
    }

    @PutMapping("/update/{sectionId}")
    public ResponseEntity<Section> updateSection(@PathVariable("sectionId") Long sectionId,
                                                 @RequestBody @Valid Section section,
                                                 @PathVariable("storageId") Long storageId) {
        if (storageService.getById(storageId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (section == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!sectionService.update(section, sectionId, storageId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{sectionId}")
    public ResponseEntity<Section> deleteSection(@PathVariable("sectionId") Long sectionId,
                                                 @PathVariable("storageId") Long storageId) {
        if (storageService.getById(storageId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Section sectionToDelete = sectionService.findByIdAndStorageId(sectionId, storageId);

        if (sectionToDelete == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        sectionService.delete(sectionToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
