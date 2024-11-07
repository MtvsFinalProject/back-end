package com.project.final_project.schoolbackgroundvote.repository;

import com.project.final_project.schoolbackgroundvote.domain.SchoolBackgroundVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolBackgroundColorVoteRepository extends JpaRepository<SchoolBackgroundVote, Integer> {

}
