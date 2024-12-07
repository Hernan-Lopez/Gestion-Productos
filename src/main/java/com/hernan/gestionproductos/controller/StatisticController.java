package com.hernan.gestionproductos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hernan.gestionproductos.entity.StatisticEntity;
import com.hernan.gestionproductos.repository.StatisticRepository;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    @Autowired
    private StatisticRepository statisticRepository;

    @GetMapping
    public ResponseEntity<List<StatisticEntity>> getStatistics() {
        List<StatisticEntity> statistics = statisticRepository.findAll();
        return ResponseEntity.ok(statistics);
    }
}
