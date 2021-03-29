package com.github.vasyapogoriliy.storage.controller;

import com.github.vasyapogoriliy.storage.model.SectionItem;
import com.github.vasyapogoriliy.storage.service.SectionItemService;
import com.github.vasyapogoriliy.storage.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/storages/{storageId}/sections/{sectionId}/products")
public class SectionItemController {

    private final SectionItemService sectionItemService;
    private final SectionService sectionService;

    public SectionItemController(SectionItemService sectionItemService,
                                 SectionService sectionService) {
        this.sectionItemService = sectionItemService;
        this.sectionService = sectionService;
    }

    @GetMapping("")
    public ResponseEntity<List<SectionItem>> getAllSectionItems(@PathVariable("sectionId") Long sectionId,
                                                                @PathVariable("storageId") Long storageId) {
        if (sectionService.findByIdAndStorageId(sectionId, storageId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<SectionItem> sectionItems = sectionItemService.getAll(sectionId, storageId);

        if (sectionItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(sectionItems, HttpStatus.OK);
    }

    @GetMapping("/{sectionItemId}")
    public ResponseEntity<SectionItem> getSectionItem(@PathVariable("sectionItemId") Long sectionItemId,
                                                      @PathVariable("sectionId") Long sectionId,
                                                      @PathVariable("storageId") Long storageId) {
        if (sectionService.findByIdAndStorageId(sectionId, storageId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (sectionItemId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        SectionItem sectionItem = sectionItemService.getById(sectionItemId, sectionId, storageId);

        if (sectionItem == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(sectionItem, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<SectionItem> addSectionItem(@RequestBody @Valid SectionItem sectionItem,
                                                      @PathVariable("sectionId") Long sectionId,
                                                      @PathVariable("storageId") Long storageId) {
        if (sectionService.findByIdAndStorageId(sectionId, storageId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (sectionItem == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!sectionItemService.save(sectionItem, sectionId, storageId))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(sectionItem, HttpStatus.CREATED);
    }

    @PutMapping("/update/{sectionItemId}")
    public ResponseEntity<SectionItem> updateSectionItem(@PathVariable("sectionItemId") Long sectionItemId,
                                                         @PathVariable("sectionId") Long sectionId,
                                                         @PathVariable("storageId") Long storageId,
                                                         @RequestBody @Valid SectionItem sectionItem) {
        if (sectionService.findByIdAndStorageId(sectionId, storageId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (sectionItem == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!sectionItemService.update(sectionItem, sectionItemId, sectionId, storageId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(sectionItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{sectionItemId}")
    public ResponseEntity<SectionItem> deleteSectionItem(@PathVariable("sectionItemId") Long sectionItemId,
                                                         @PathVariable("sectionId") Long sectionId,
                                                         @PathVariable("storageId") Long storageId) {
        if (sectionService.findByIdAndStorageId(sectionId, storageId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SectionItem sectionItemToDelete = sectionItemService.getById(sectionItemId, sectionId, storageId);

        if (sectionItemToDelete == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        sectionItemService.delete(sectionItemToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
