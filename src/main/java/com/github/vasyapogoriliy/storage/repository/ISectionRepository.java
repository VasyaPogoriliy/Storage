package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISectionRepository extends JpaRepository<Section, Long> {

    List<Section> findAllByStorageId(Long storageId);

    Optional<Section> findByIdAndStorageId(Long sectionId, Long storageId);

    boolean existsByIdAndStorageId(Long sectionId, Long storageId);


}
