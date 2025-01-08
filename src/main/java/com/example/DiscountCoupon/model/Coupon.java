package com.example.DiscountCoupon.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private String code;
    private Double discountValue;
    private Double discountPercentage;
    private Double minimumCartValue;
    private LocalDateTime expiryDate;


    @ElementCollection
    private List<Long> applicableProductIds;

    @ElementCollection
    private List<Long> buyProductIds;

    @ElementCollection
    private List<Long> getProductIds;

    private Integer buyQuantity;
    private Integer getQuantity;
    private Integer repetitionLimit;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
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

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
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