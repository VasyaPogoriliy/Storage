package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Product;
import com.github.vasyapogoriliy.storage.model.Storage;
import com.github.vasyapogoriliy.storage.repository.IStorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService implements IStorageService {

    private final IStorageRepository storageRepository;

    public StorageService(IStorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }


    @Override
    public List<Storage> getAll() {
        return storageRepository.findAll();
    }

    @Override
    public Storage getById(Long id) {
        return storageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean save(Storage storage) {
        List<Storage> storages = storageRepository.findAll();

        for (Storage storageToCompare : storages) {
            if (storageToCompare.getName().equals(storage.getName())) return false;
        }

        storageRepository.save(storage);

        return true;
    }

    @Override
    public boolean update(Storage storage, Long id) {
        if (!storageRepository.existsById(id)) {
            return false;
        }

        storage.setId(id);

        List<Storage> storages = storageRepository.findAll();

        for (Storage storageToCompare : storages) {
            if (!storageToCompare.getName().equals(storage.getName())) {
                continue;
            }
            if (storageToCompare.getCapacity() == storage.getCapacity()) {
                return false;
            }
            if (!storageToCompare.getId().equals(storage.getId())) {
                return false;
            }
        }

        storageRepository.save(storage);

        return true;
    }

    @Override
    public void delete(Long id) {
        storageRepository.deleteById(id);
    }

}
