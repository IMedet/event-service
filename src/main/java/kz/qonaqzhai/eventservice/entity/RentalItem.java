package kz.qonaqzhai.eventservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rental_items")
@Getter
@Setter
public class RentalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String priceUnit;

    @Column(nullable = false)
    private String supplier;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Integer reviews;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean available;

    @Column(nullable = false)
    private Integer minQuantity;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Boolean verified;
}
