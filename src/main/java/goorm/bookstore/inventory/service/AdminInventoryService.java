package goorm.bookstore.inventory.service;


import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.inventory.dto.AddInventoryDto;
import goorm.bookstore.inventory.dto.InventoryForAdminDto;
import goorm.bookstore.inventory.dto.UpdateInventoryDto;
import goorm.bookstore.inventory.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminInventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public AdminInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void save(AddInventoryDto addInventoryDto, String username) {

        String isbn = addInventoryDto.getIsbn();
        Inventory findInventory = inventoryRepository.findInventoryByIsbn(isbn).orElse(null);
        if (findInventory != null) {
            int quantity = findInventory.getQuantity() + addInventoryDto.getQuantity();
            findInventory.setQuantity(quantity);
        } else {
            // addInventoryDto -> Inventory
            Inventory inventory = Inventory.builder()
                    .isbn(isbn)
                    .authors(String.join(",", addInventoryDto.getAuthors()))
                    .translators(String.join(",", addInventoryDto.getTranslators()))
                    .price(addInventoryDto.getPrice())
                    .salePrice(addInventoryDto.getSalePrice())
                    .thumbnail(addInventoryDto.getThumbnail())
                    .url(addInventoryDto.getUrl())
                    .createdBy(username)
                    .lastModifiedBy(username)
                    .title(addInventoryDto.getTitle())
                    .contents(addInventoryDto.getContents())
                    .publisher(addInventoryDto.getPublisher())
                    .datetime(addInventoryDto.getDatetime())
                    .quantity(addInventoryDto.getQuantity())
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    public void update(UpdateInventoryDto updateInventoryDto, String username) {
        Long inventoryId = updateInventoryDto.getInventoryId();
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new EntityNotFoundException("해당되는 유저가 없습니다."));
            inventory.setQuantity(updateInventoryDto.getQuantity());
            inventory.setStatus(updateInventoryDto.getStatus());
    }

    public InventoryForAdminDto findById(Long id) {
        Inventory findInventory = inventoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당되는 도서가 없습니다."));

        return InventoryForAdminDto.builder()
                .inventoryId(findInventory.getId())
                .title(findInventory.getTitle())
                .isbn(findInventory.getIsbn())
                .authors(findInventory.getAuthors())
                .publisher(findInventory.getPublisher())
                .createdAt(findInventory.getCreatedAt())
                .lastModifiedAt(findInventory.getLastModifiedAt())
                .createdBy(findInventory.getCreatedBy())
                .lastModifiedBy(findInventory.getLastModifiedBy())
                .price(findInventory.getPrice())
                .salePrice(findInventory.getSalePrice())
                .thumbnail(findInventory.getThumbnail())
                .quantity(findInventory.getQuantity())
                .status(findInventory.getStatus())
                .translators(findInventory.getTranslators()).build();
    }

    public List<InventoryForAdminDto> findAll() {

        return inventoryRepository.findAll().stream().map(inventory -> InventoryForAdminDto.builder()
                .inventoryId(inventory.getId())
                .title(inventory.getTitle())
                .isbn(inventory.getIsbn())
                .authors(inventory.getAuthors())
                .publisher(inventory.getPublisher())
                .createdAt(inventory.getCreatedAt())
                .lastModifiedAt(inventory.getLastModifiedAt())
                .price(inventory.getPrice())
                .salePrice(inventory.getSalePrice())
                .thumbnail(inventory.getThumbnail())
                .quantity(inventory.getQuantity())
                .status(inventory.getStatus())
                .translators(inventory.getTranslators())
                .build()
        ).toList();
    }

    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }
}
