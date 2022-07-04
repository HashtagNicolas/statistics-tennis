package com.mas.statistics.tennis.controller;

import com.mas.statistics.tennis.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatisticsApiDelegateImpl implements StatisticsApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsApiDelegateImpl.class);
    private final StatisticsService statisticsService;

    public StatisticsApiDelegateImpl(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public ResponseEntity<Map<String, String>> getStatistics() {
        LOGGER.info("Start service : getStatistics");
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}