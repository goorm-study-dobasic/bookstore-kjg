package goorm.bookstore.inventory.service;

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
        // inventoryRepository.findAll();
        return null;
    }

}
