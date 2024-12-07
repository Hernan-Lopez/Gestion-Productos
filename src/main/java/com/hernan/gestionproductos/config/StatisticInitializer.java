package com.hernan.gestionproductos.config;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hernan.gestionproductos.entity.ProductEntity;
import com.hernan.gestionproductos.entity.StatisticEntity;
import com.hernan.gestionproductos.repository.ProductRepository;
import com.hernan.gestionproductos.repository.StatisticRepository;

@Component
public class StatisticInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public void run(String... args) throws Exception {
        statisticRepository.deleteAll();

        Map<String, Long> categoryCounts = productRepository.findAll().stream()
            .collect(Collectors.groupingBy(ProductEntity::getCategory, Collectors.counting()));

        categoryCounts.forEach((category, count) -> {
            StatisticEntity statistic = new StatisticEntity();
            statistic.setCategory(category);
            statistic.setProductCount(count.intValue());
            statisticRepository.save(statistic);
        });

        System.out.println("Estadísticas iniciales creadas.");
    }
}
