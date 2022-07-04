package com.mas.statistics.tennis.service;

import com.mas.statistics.tennis.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    List<PlayerDTO> getListPlayersByRank();

    PlayerDTO getPlayerById(Long playerId);
}
