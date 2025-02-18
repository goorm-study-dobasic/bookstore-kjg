package goorm.bookstore.inventory.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InventoryStatus {
    ON_SALES("정상판매"), OOS("재고없음"), OOP("절판");

    private final String status;

    InventoryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static InventoryStatus fromString(String status) {
        for (InventoryStatus inventoryStatus : InventoryStatus.values()) {
            if (inventoryStatus.getStatus().equals(status)) {
                return inventoryStatus;
            }
        }
        return null;
    }
}
