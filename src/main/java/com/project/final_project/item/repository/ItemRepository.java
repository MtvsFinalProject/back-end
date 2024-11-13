package com.project.final_project.item.repository;

import com.project.final_project.item.domain.Item;
import com.project.final_project.item.dto.ItemDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

  @Query("select i from Item i where i.userId = :userId")
  List<Item> getItemListByUserId(@Param("userId") Integer userId);

  @Query("select i from Item i where i.userId = :userId and i.itemType = :itemType")
  List<ItemDTO> getItemListByUserIdAndItemType(@Param("userId") Integer userId, @Param("itemType") String itemType);

}
