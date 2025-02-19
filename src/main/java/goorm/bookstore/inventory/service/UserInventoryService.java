package goorm.bookstore.inventory.service;

import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.inventory.dto.InventoryForUserDto;
import goorm.bookstore.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public UserInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryForUserDto> findAll() {

        return inventoryRepository.findAll().stream().map(inventory -> InventoryForUserDto.builder()
                .inventoryId(inventory.getInventoryId())
                .isbn(inventory.getIsbn())
                .title(inventory.getTitle())
                .authors(inventory.getAuthors())
                .publisher(inventory.getPublisher())
                .salePrice(inventory.getSalePrice())
                .status(inventory.getStatus())
                .thumbnail(inventory.getThumbnail())
                .build()).toList();

    }

}
