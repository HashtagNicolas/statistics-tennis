package com.mas.statistics.tennis.service.impl;


import com.mas.statistics.tennis.client.DataClientService;
import com.mas.statistics.tennis.helper.OperationsHelper;
import com.mas.statistics.tennis.model.Country;
import com.mas.statistics.tennis.model.Data;
import com.mas.statistics.tennis.model.Player;
import com.mas.statistics.tennis.service.StatisticsService;
import com.mas.statistics.tennis.util.ConstsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    private double bestWinRatio = 0;
    private String countryBestWinRatio = "";
    private final DataClientService dataClientService;

    public StatisticsServiceImpl(DataClientService dataClientService) {
        this.dataClientService = dataClientService;
    }

    @Override
    public Map<String, String> getStatistics() {
        Map<String,String> statisticsMap = new HashMap<>();
        List<Player> playerList = dataClientService.getAllPlayers();
        List<Data> dataPlayersList = playerList.stream().map(Player::getData).collect(Collectors.toList());

        String bestCountryCodeWinRatio = getBestCountryCodeWinRatio(playerList);
        statisticsMap.put(ConstsUtils.BEST_COUNTRY_RATIO_LABEL,bestCountryCodeWinRatio);

        Integer medianHeightOfPlayers = getMedianHeightOfDataList(dataPlayersList);
        statisticsMap.put(ConstsUtils.MEDIAN_HEIGHT_PLAYERS_LABEL, String.valueOf(medianHeightOfPlayers));

        Double imcAverageOfPlayers = getAvgIMCPlayers(dataPlayersList);
        statisticsMap.put(ConstsUtils.AVG_IMC_PLAYERS_LABEL, String.valueOf(imcAverageOfPlayers));

        LOGGER.info("getStatistics - Stats = {} ",statisticsMap);
        return statisticsMap;
    }
    private Double getAvgIMCPlayers(List<Data> dataPlayersList) {
        return dataPlayersList.stream()
                .mapToDouble(e-> OperationsHelper
                        .getIMC((double) e.getHeight() / ConstsUtils.HUNDRED_NUMBER_LABEL,
                                (double) e.getWeight() / ConstsUtils.THOUSAND_NUMBER_LABEL)).average()
                .orElseThrow(() -> new IllegalArgumentException("List data is empty"));
    }
    private Integer getMedianHeightOfDataList(List<Data> dataList){
        dataList.sort(Comparator.comparing(Data::getHeight));
        LOGGER.debug("getMedianHeightOfDataList - data sort Height : {}",
                dataList.stream().map(Data::getWeight).collect(Collectors.toList()));
        return dataList.get(OperationsHelper.getMedian(dataList.size())).getHeight();
    }
    private String getBestCountryCodeWinRatio(List<Player> playerList) {
        List<String> codeCountryList = playerList.stream().map(Player::getCountry).distinct()
                .map(Country::getCode).collect(Collectors.toList());

        for (String codeCountry: codeCountryList) {
            List<Player> playerListByCountry = playerList.stream()
                    .filter(e -> Objects.equals(e.getCountry().getCode(), codeCountry)).collect(Collectors.toList());

            double ratio = calculateRatioByPlayers(playerListByCountry);
            countryBestWinRatio = getBestCountryWinRatio(codeCountry, ratio);
        }
        LOGGER.debug("getBestCountryCodeWinRatio - country best ratio : {}", countryBestWinRatio);
        return dataClientService.getCountryLabelByCountryCodeISO3(countryBestWinRatio);
    }
    private String getBestCountryWinRatio(String codeCountry, double ratio) {
        LOGGER.debug("getBestCountryWinRatio - codeCountry : {} - ratio : {}",codeCountry,ratio);
        if(ratio > bestWinRatio) {
            bestWinRatio = ratio;
            countryBestWinRatio = codeCountry;
        }
        return countryBestWinRatio;
    }
    private Double calculateRatioByPlayers( List<Player> playerList) {
        double sumWinningGame = playerList.stream()
                .mapToInt(e -> e.getData().getLast().stream().mapToInt(Integer::intValue).sum()).sum();

        int sumLastGamePlaying = playerList.stream()
                .mapToInt(e -> e.getData().getLast().size())
                .sum();
        LOGGER.debug("calculateRatioByPlayers - sumWinningGame : {} - sumLastGamePlaying : {}"
                ,sumWinningGame,sumLastGamePlaying);
        return OperationsHelper.getRatio(sumWinningGame,sumLastGamePlaying);
    }
}
