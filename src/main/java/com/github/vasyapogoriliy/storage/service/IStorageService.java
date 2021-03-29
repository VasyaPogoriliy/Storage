package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Storage;

import java.util.List;

public interface IStorageService {

    List<Storage> getAll();

    Storage getById(Long id);

    boolean save(Storage storage);

    boolean update(Storage storage, Long id);

    void delete(Long id);

}
