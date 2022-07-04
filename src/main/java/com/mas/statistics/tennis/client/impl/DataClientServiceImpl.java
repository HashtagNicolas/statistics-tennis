package com.mas.statistics.tennis.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mas.statistics.tennis.client.DataClientService;
import com.mas.statistics.tennis.model.Player;
import com.mas.statistics.tennis.model.Players;
import com.mas.statistics.tennis.util.ConstsUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class DataClientServiceImpl implements DataClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataClientServiceImpl.class);
    @Value("${data.tennis.players.url}")
    private String urlDataTennisPlayers;
    @Value("${data.country.info.url}")
    private String urlDataCountryInfo;
    @Override
    public List<Player> getAllPlayers() {
        final RestTemplate restTemplate = new RestTemplate();
        final String response = restTemplate.getForObject(urlDataTennisPlayers, String.class);
        ObjectMapper oMapper = new ObjectMapper();
        Players players = new Players();
        try {
            players = oMapper.readValue(response, Players.class);

        }catch (JsonProcessingException e){
            e.getStackTrace();
        }
        LOGGER.info("GetAllPlayers - size = {}", players.getPlayers().size());
        return players.getPlayers();
    }
    @Override
    public String getCountryLabelByCountryCodeISO3(String countryCodeISO3) {
        final RestTemplate restTemplate = new RestTemplate();
        ObjectMapper oMapper = new ObjectMapper();

        final String response = restTemplate.getForObject(urlDataCountryInfo+countryCodeISO3, String.class);
        JSONObject json =  new JSONObject(response);
        Object objectData = json.getJSONObject(ConstsUtils.DATA_LABEL)
                .toMap().entrySet().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Couldn't find object Data")).getValue();

        HashMap<String,String> countryData = oMapper.convertValue(objectData, new TypeReference<>() {});
        LOGGER.info("GetCountryLabelByCountryCodeISO3 - CodeISO3 = {}, CountryName = {}",
                countryCodeISO3, countryData.get(ConstsUtils.COUNTRY_LABEL));
        return  countryData.get(ConstsUtils.COUNTRY_LABEL);
    }
}
