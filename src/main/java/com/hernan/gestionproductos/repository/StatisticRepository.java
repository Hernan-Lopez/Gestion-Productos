package com.hernan.gestionproductos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hernan.gestionproductos.entity.StatisticEntity;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {
	Optional<StatisticEntity> findByCategory(String category);
}