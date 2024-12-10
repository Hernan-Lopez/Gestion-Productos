package com.hernan.gestionproductos.controller;

import com.hernan.gestionproductos.entity.StatisticEntity;
import com.hernan.gestionproductos.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticControllerTest {

    @InjectMocks
    private StatisticController statisticController;

    @Mock
    private StatisticRepository statisticRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatistics() {
        StatisticEntity statistic1 = new StatisticEntity();
        statistic1.setCategory("Electronics");
        statistic1.setProductCount(10);

        StatisticEntity statistic2 = new StatisticEntity();
        statistic2.setCategory("Books");
        statistic2.setProductCount(5);

        List<StatisticEntity> mockStatistics = Arrays.asList(statistic1, statistic2);

        when(statisticRepository.findAll()).thenReturn(mockStatistics);

        ResponseEntity<List<StatisticEntity>> response = statisticController.getStatistics();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Electronics", response.getBody().get(0).getCategory());
        assertEquals(10, response.getBody().get(0).getProductCount());

        verify(statisticRepository, times(1)).findAll();
    }
}
