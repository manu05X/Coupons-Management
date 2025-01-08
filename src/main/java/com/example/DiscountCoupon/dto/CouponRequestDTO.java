package com.example.DiscountCoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequestDTO {
    private String type; // Enum type as String (e.g., BUY_X_GET_Y, CART_WISE, PRODUCT_WISE)
    private String code;
    private Double discountValue;
    private Double minimumCartValue;
    private LocalDateTime expiryDate;
    private List<Long> applicableProductIds;
    private List<Long> buyProductIds;
    private List<Long> getProductIds;
    private Integer buyQuantity;
    private Integer getQuantity;
    private Integer repetitionLimit;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getMinimumCartValue() {
        return minimumCartValue;
    }

    public void setMinimumCartValue(Double minimumCartValue) {
        this.minimumCartValue = minimumCartValue;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<Long> getApplicableProductIds() {
        return applicableProductIds;
    }

    public void setApplicableProductIds(List<Long> applicableProductIds) {
        this.applicableProductIds = applicableProductIds;
    }

    public List<Long> getBuyProductIds() {
        return buyProductIds;
    }

    public void setBuyProductIds(List<Long> buyProductIds) {
        this.buyProductIds = buyProductIds;
    }

    public List<Long> getGetProductIds() {
        return getProductIds;
    }

    public void setGetProductIds(List<Long> getProductIds) {
        this.getProductIds = getProductIds;
    }

    public Integer getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Integer buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Integer getGetQuantity() {
        return getQuantity;
    }

    public void setGetQuantity(Integer getQuantity) {
        this.getQuantity = getQuantity;
    }

    public Integer getRepetitionLimit() {
        return repetitionLimit;
    }

    public void setRepetitionLimit(Integer repetitionLimit) {
        this.repetitionLimit = repetitionLimit;
    }
}
