package com.example.naturesway.repository;

import com.example.naturesway.domain.entities.LivingTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivingTipRepository extends JpaRepository<LivingTip,String> {
    Optional<LivingTip> findByName(String livingTipName);
}
