package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.SectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISectionItemRepository extends JpaRepository<SectionItem, Long> {

    List<SectionItem> findAllBySectionId(Long sectionId);

    boolean existsByIdAndSectionId(Long sectionItemId, Long sectionId);

    Optional<SectionItem> findByProductNameAndProductWeightAndProductPrice(String name, int weight, int price);

}
