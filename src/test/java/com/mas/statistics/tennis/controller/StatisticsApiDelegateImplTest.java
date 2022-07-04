package com.mas.statistics.tennis.controller;

import com.mas.statistics.tennis.service.StatisticsService;
import com.mas.statistics.tennis.util.ConstsUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StatisticsApiDelegateImplTest {

    @InjectMocks
    private StatisticsApiDelegateImpl statisticsApiDelegate;

    @Mock
    private StatisticsService statisticsService;

    Map<String, String> mapStatistics = getMapStatistics();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        MockMvcBuilders.standaloneSetup(statisticsApiDelegate).build();
    }

    private Map<String, String> getMapStatistics(){
        Map<String, String> mapStatistics = new HashMap<>();
        mapStatistics.put(ConstsUtils.AVG_IMC_PLAYERS_LABEL,"23.234565");
        mapStatistics.put(ConstsUtils.BEST_COUNTRY_RATIO_LABEL,"france");
        mapStatistics.put(ConstsUtils.MEDIAN_HEIGHT_PLAYERS_LABEL,"188");
        return mapStatistics;
    }

    @Test
    public void CODE_200_GetStatistics_Test() {
        ResponseEntity<Map<String,String>> expected = ResponseEntity.ok(mapStatistics);

        Mockito.when(statisticsService.getStatistics()).thenReturn(mapStatistics);

        ResponseEntity<Map<String,String>> actual = statisticsApiDelegate.getStatistics();
        Assertions.assertEquals(HttpStatus.OK,actual.getStatusCode());
        Assertions.assertEquals(expected,actual);

    }

}
