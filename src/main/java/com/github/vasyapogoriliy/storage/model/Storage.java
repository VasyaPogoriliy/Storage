package com.github.vasyapogoriliy.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @PositiveOrZero
    private int capacity;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Section> sections = new ArrayList<>();

}
