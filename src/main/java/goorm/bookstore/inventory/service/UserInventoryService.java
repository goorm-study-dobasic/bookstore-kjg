package goorm.bookstore.inventory.service;

import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.inventory.dto.InventoryForUserDto;
import goorm.bookstore.inventory.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
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
                .inventoryId(inventory.getId())
                .title(inventory.getTitle())
                .authors(inventory.getAuthors())
                .publisher(inventory.getPublisher())
                .salePrice(inventory.getSalePrice())
                .status(inventory.getStatus())
                .thumbnail(inventory.getThumbnail())
                .build()).toList();
    }

    public InventoryForUserDto find(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException("해당되는 책이 없습니다."));

        // 인벤토리 -> dto 변환.
        return Inventory.getInventoryUserDto(inventory);
    }

}
