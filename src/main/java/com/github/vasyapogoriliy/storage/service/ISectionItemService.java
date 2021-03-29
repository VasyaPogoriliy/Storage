package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.SectionItem;

import java.util.List;

public interface ISectionItemService {

    List<SectionItem> getAll(Long sectionId, Long storageId);

    SectionItem getById(Long sectionItemId, Long sectionId, Long storageId);

    boolean save(SectionItem sectionItem, Long sectionId, Long storageId);

    boolean update(SectionItem sectionItem, Long sectionItemId, Long sectionId, Long storageId);

    void delete(SectionItem sectionItemToDelete);


}
