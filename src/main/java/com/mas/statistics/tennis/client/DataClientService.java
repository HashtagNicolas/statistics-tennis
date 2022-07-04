package com.mas.statistics.tennis.client;

import com.mas.statistics.tennis.model.Player;

import java.util.List;

public interface DataClientService {
    List<Player> getAllPlayers();
    String getCountryLabelByCountryCodeISO3(String countryCodeISO3);
}
