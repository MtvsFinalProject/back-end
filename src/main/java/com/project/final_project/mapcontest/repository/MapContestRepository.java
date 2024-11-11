package com.project.final_project.mapcontest.repository;

import com.project.final_project.mapcontest.domain.MapContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapContestRepository extends JpaRepository<MapContest, Integer> {

}
