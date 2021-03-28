package com.example.naturesway.repository;

import com.example.naturesway.domain.entities.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdventureRepository extends JpaRepository<Adventure,String> {
    Optional<Adventure> findByName(String adventureName);
}
