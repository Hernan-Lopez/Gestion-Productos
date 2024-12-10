package com.hernan.gestionproductos.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.hernan.gestionproductos.entity.StatisticEntity;
import com.hernan.gestionproductos.repository.StatisticRepository;

class StatisticServiceImplTest extends AsyncConfigurerSupport {

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @Mock
    private StatisticRepository statisticRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.initialize();
        statisticService = new StatisticServiceImpl();
        statisticService.statisticRepository = statisticRepository;
    }

    @Test
    void testUpdateStatistics_NewCategory() throws Exception {
        String category = "Electronics";
        int delta = 5;

        when(statisticRepository.findByCategory(category)).thenReturn(Optional.empty());
        when(statisticRepository.save(any(StatisticEntity.class))).thenReturn(new StatisticEntity());

        CompletableFuture.runAsync(() -> statisticService.updateStatistics(category, delta)).get();

        verify(statisticRepository).findByCategory(category);
        verify(statisticRepository).save(any(StatisticEntity.class));
    }

    @Test
    void testUpdateStatistics_ExistingCategory() throws Exception {
        String category = "Books";
        int delta = 3;

        StatisticEntity existingStatistic = new StatisticEntity();
        existingStatistic.setCategory(category);
        existingStatistic.setProductCount(10);

        when(statisticRepository.findByCategory(category)).thenReturn(Optional.of(existingStatistic));
        when(statisticRepository.save(any(StatisticEntity.class))).thenReturn(existingStatistic);

        CompletableFuture.runAsync(() -> statisticService.updateStatistics(category, delta)).get();

        verify(statisticRepository).findByCategory(category);
        verify(statisticRepository).save(any(StatisticEntity.class));
    }

    @Test
    void testUpdateStatistics_ProductCountDoesNotGoBelowZero() throws Exception {
        String category = "Toys";
        int delta = -15;

        StatisticEntity existingStatistic = new StatisticEntity();
        existingStatistic.setCategory(category);
        existingStatistic.setProductCount(10);

        when(statisticRepository.findByCategory(category)).thenReturn(Optional.of(existingStatistic));
        when(statisticRepository.save(any(StatisticEntity.class))).thenReturn(existingStatistic);

        CompletableFuture.runAsync(() -> statisticService.updateStatistics(category, delta)).get();

        verify(statisticRepository).findByCategory(category);
        verify(statisticRepository).save(any(StatisticEntity.class));

        assert existingStatistic.getProductCount() >= 0;
    }
}
