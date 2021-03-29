package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStorageRepository extends JpaRepository<Storage, Long> {

}
