package com.project.final_project.item.domain;

import com.project.final_project.item.dto.ItemDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "item_name")
  private String itemName;

  @Column(name = "item_count")
  private Integer count;

  @Column(name = "item_price")
  private Integer price;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "item_type")
  private String itemType;

  public Item(String itemName, Integer count, Integer price, Integer userId, Integer mapId,
      String mapType, String itemType) {
    this.itemName = itemName;
    this.count = count;
    this.price = price;
    this.userId = userId;
    this.itemType = itemType;
  }

  public Item(ItemDTO dto) {
    this.itemName = dto.getItemName();
    this.count = dto.getCount();
    this.price = dto.getPrice();
    this.userId = dto.getUserId();
    this.itemType = dto.getItemType();
  }
}
