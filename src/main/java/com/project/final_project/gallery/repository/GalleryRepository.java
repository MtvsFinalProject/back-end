package com.project.final_project.gallery.repository;

import com.project.final_project.gallery.domain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {

}
