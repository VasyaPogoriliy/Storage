package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Product;
import com.github.vasyapogoriliy.storage.model.Section;
import com.github.vasyapogoriliy.storage.model.SectionItem;
import com.github.vasyapogoriliy.storage.model.Storage;
import com.github.vasyapogoriliy.storage.repository.ISectionItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionItemService implements ISectionItemService {

    private final ISectionItemRepository sectionItemRepository;
    private final SectionService sectionService;
    private final StorageService storageService;

    public SectionItemService(ISectionItemRepository sectionItemRepository,
                              SectionService sectionService,
                              StorageService storageService) {
        this.sectionItemRepository = sectionItemRepository;
        this.sectionService = sectionService;
        this.storageService = storageService;
    }

    @Override
    public List<SectionItem> getAll(Long sectionId, Long storageId) {
        List<SectionItem> sectionItems = sectionItemRepository.findAllBySectionId(sectionId);

        return sectionItems.stream()
                .filter(i -> i.getSection().getStorage().getId().equals(storageId))
                .collect(Collectors.toList());
    }

    @Override
    public SectionItem getById(Long sectionItemId, Long sectionId, Long storageId) {
        List<SectionItem> sectionItems = getAll(sectionId, storageId);
        return sectionItems.stream().filter(i -> i.getId().equals(sectionItemId)).findFirst().orElse(null);
    }

    @Override
    public boolean save(SectionItem sectionItem, Long sectionId, Long storageId) {

        Section section = sectionService.findByIdAndStorageId(sectionId, storageId);
        Storage storage = section.getStorage();

        int newCapacity = storage.getCapacity() - sectionItem.getAmount();

        if (newCapacity >= 0) {
            storage.setCapacity(newCapacity);
        } else {
            return false;
        }


        List<SectionItem> sectionItems = section.getSectionItems();

        for (SectionItem sectionItemToCompare : sectionItems) {
            if (!sectionItemToCompare.getProduct().getName().equals(sectionItem.getProduct().getName())) {
                continue;
            }
            if (sectionItemToCompare.getProduct().getWeight() == sectionItem.getProduct().getWeight() &&
                    sectionItemToCompare.getProduct().getPrice() == sectionItem.getProduct().getPrice()) {

                sectionItem.setId(sectionItemToCompare.getId());
                sectionItem.setProduct(sectionItemToCompare.getProduct());
                sectionItem.setSection(section);
                sectionItem.setAmount(sectionItemToCompare.getAmount() + sectionItem.getAmount());

                storageService.update(storage, storageId);
                sectionItemRepository.delete(sectionItemToCompare);
                sectionItemRepository.save(sectionItem);
                return true;
            } else {
                return false;
            }
        }

        sectionItem.setSection(section);
        sectionItemRepository.save(sectionItem);
        storageService.update(storage, storageId);

        return true;
    }

    @Override
    public boolean update(SectionItem sectionItem, Long sectionItemId, Long sectionId, Long storageId) {
        if (!sectionItemRepository.existsByIdAndSectionId(sectionItemId, sectionId)) {
            return false;
        }

        Section section = sectionService.findByIdAndStorageId(sectionId, storageId);

        List<SectionItem> sectionItems = section.getSectionItems();

        sectionItem.setId(sectionItemId);
        sectionItem.setSection(section);

        for (SectionItem sectionItemToCompare : sectionItems) {
            if (!sectionItemToCompare.getProduct().getName().equals(sectionItem.getProduct().getName())) {
                continue;
            }
            if ((sectionItemToCompare.getProduct().getWeight() == sectionItem.getProduct().getWeight() &&
                    sectionItemToCompare.getProduct().getPrice() == sectionItem.getProduct().getPrice() &&
                    sectionItemToCompare.getAmount() == sectionItem.getAmount()) ||
                    !sectionItemToCompare.getId().equals(sectionItem.getId())) {
                return false;
            } else {

                Storage storage = section.getStorage();

                int newCapacity = storage.getCapacity() - (sectionItem.getAmount() - sectionItemToCompare.getAmount());

                if (newCapacity >= 0) {
                    storage.setCapacity(newCapacity);
                } else {
                    return false;
                }

                storageService.update(storage, storageId);

                Product product = sectionItem.getProduct();
                product.setId(sectionItemToCompare.getProduct().getId());
                sectionItem.setProduct(product);
            }
        }

        sectionItemRepository.save(sectionItem);

        return true;
    }

    @Override
    public void delete(SectionItem sectionItemToDelete) {
        Storage storage = sectionItemToDelete.getSection().getStorage();

        int newCapacity = storage.getCapacity() + sectionItemToDelete.getAmount();

        storage.setCapacity(newCapacity);
        storageService.update(storage, sectionItemToDelete.getSection().getStorage().getId());

        sectionItemRepository.delete(sectionItemToDelete);
    }
}
