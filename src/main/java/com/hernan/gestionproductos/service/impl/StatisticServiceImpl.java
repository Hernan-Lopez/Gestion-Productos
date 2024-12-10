package com.hernan.gestionproductos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.hernan.gestionproductos.entity.StatisticEntity;
import com.hernan.gestionproductos.repository.StatisticRepository;
import com.hernan.gestionproductos.service.StatisticService;

@Service
@EnableAsync
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    StatisticRepository statisticRepository;

    @Override
    @Async
    public void updateStatistics(String category, int delta) {
        synchronized (this) {
            StatisticEntity statistic = statisticRepository.findByCategory(category)
                    .orElse(new StatisticEntity());

            statistic.setCategory(category);
            statistic.setProductCount(statistic.getProductCount() + delta);

            if (statistic.getProductCount() < 0) {
                statistic.setProductCount(0);
            }

            statisticRepository.save(statistic);
        }
    }
}