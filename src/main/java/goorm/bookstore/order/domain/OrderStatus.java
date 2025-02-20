package goorm.bookstore.order.domain;

public enum OrderStatus {
    Pending("결제대기"),
    Paid("결제완료"),
    Preparing("배송준비"),
    Shipping("배송중"),
    Delivered("배송완료");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
