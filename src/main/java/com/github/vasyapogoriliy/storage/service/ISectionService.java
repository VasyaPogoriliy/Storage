package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Section;

import java.util.List;

public interface ISectionService {

    List<Section> getAll(Long storageId);

    Section findByIdAndStorageId(Long sectionId, Long storageId);

    boolean save(Section section, Long storageId);

    boolean update(Section section, Long sectionId, Long storageId);

    void delete(Section sectionToDelete);

}
