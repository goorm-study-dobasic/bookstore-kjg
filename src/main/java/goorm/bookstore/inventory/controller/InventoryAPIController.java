package goorm.bookstore.inventory.controller;

import goorm.bookstore.inventory.service.AdminInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequestMapping("/api/inventory")
@RestController
public class InventoryAPIController {

    private final AdminInventoryService adminInventoryService;

    @Autowired
    public InventoryAPIController(AdminInventoryService adminInventoryService) {
        this.adminInventoryService = adminInventoryService;
    }

    @GetMapping("/stock")
    public ResponseEntity<Map<String, Integer>> returnStockAPI(@RequestParam("inventoryId") Long id) {
        int quantity = adminInventoryService.findById(id).getQuantity();
        Map<String, Integer> response = Collections.singletonMap("quantity", quantity);
        return ResponseEntity.ok(response);
    }

}
