package com.github.vasyapogoriliy.storage.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "section_items")
@Data
public class SectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @PositiveOrZero
    private int amount;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

}
