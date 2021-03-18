package com.example.naturesway.repository;

import com.example.naturesway.domain.entities.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure,String> {
}
