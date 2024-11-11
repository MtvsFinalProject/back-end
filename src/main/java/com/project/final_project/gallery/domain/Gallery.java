package com.project.final_project.gallery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gallery")
public class Gallery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "image_base_64")
  String imageBase64;

  @Column(name = "title")
  String title;

  @Column(name = "description")
  String description;

  @Column(name = "like_count")
  Integer likeN;

  @Column(name = "visit_count")
  Integer visitN;

}
