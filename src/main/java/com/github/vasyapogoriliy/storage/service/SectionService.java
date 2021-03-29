package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Section;
import com.github.vasyapogoriliy.storage.model.Storage;
import com.github.vasyapogoriliy.storage.repository.ISectionRepository;
import com.github.vasyapogoriliy.storage.repository.IStorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService implements ISectionService {

    private final IStorageRepository storageRepository;
    private final ISectionRepository sectionRepository;

    public SectionService(IStorageRepository storageRepository, ISectionRepository sectionRepository) {
        this.storageRepository = storageRepository;
        this.sectionRepository = sectionRepository;
    }


    @Override
    public List<Section> getAll(Long storageId) {
        return sectionRepository.findAllByStorageId(storageId);
    }

    @Override
    public Section findByIdAndStorageId(Long sectionId, Long storageId) {
        return sectionRepository.findByIdAndStorageId(sectionId, storageId).orElse(null);
    }

    @Override
    public boolean save(Section section, Long storageId) {
        Storage storage = storageRepository.getOne(storageId);
        List<Section> sections = storage.getSections();

        for (Section sectionToCompare : sections) {
            if (sectionToCompare.getName().equals(section.getName())) return false;
        }

        section.setStorage(storage);
        sectionRepository.save(section);

        return true;
    }

    @Override
    public boolean update(Section section, Long sectionId, Long storageId) {
        if (!sectionRepository.existsByIdAndStorageId(sectionId, storageId)) {
            return false;
        }

        Storage storage = storageRepository.getOne(storageId);
        section.setStorage(storage);
        section.setId(sectionId);

        List<Section> sections = sectionRepository.findAllByStorageId(storageId);

        for (Section sectionToCompare : sections) {
            if (sectionToCompare.getName().equals(section.getName())) return false;
        }

        sectionRepository.save(section);

        return true;
    }

    @Override
    public void delete(Section sectionToDelete) {
        sectionRepository.delete(sectionToDelete);
    }
}
