package com.example.naturesway.repository;

import com.example.naturesway.domain.entities.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure,String> {
}
