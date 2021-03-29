package com.github.vasyapogoriliy.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
@Data
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SectionItem> sectionItems = new ArrayList<>();

}
