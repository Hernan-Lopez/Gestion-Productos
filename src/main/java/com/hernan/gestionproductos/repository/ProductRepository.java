package com.hernan.gestionproductos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hernan.gestionproductos.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByName(String name);

	@Modifying
	@Query("UPDATE ProductEntity p SET " 
			+ "p.name = COALESCE(:name, p.name), "
			+ "p.category = COALESCE(:category, p.category), " 
			+ "p.price = COALESCE(:price, p.price) "
			+ "WHERE p.id = :id")
	int update(@Param("id") Long id, @Param("name") String name, @Param("category") String category,
			@Param("price") Float price);
}
