package com.project.final_project.lockerguestbook.repository;

import com.project.final_project.lockerguestbook.domain.LockerGuestBook;
import com.project.final_project.lockerguestbook.dto.LockerGuestBookDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerGuestBookRepository extends JpaRepository<LockerGuestBook, Integer> {


}
