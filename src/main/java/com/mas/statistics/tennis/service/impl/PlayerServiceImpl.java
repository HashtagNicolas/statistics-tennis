package com.mas.statistics.tennis.service.impl;


import com.mas.statistics.tennis.client.DataClientService;
import com.mas.statistics.tennis.dto.PlayerDTO;
import com.mas.statistics.tennis.mapper.PlayerMapper;
import com.mas.statistics.tennis.model.Player;
import com.mas.statistics.tennis.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);
    private final DataClientService dataTennisClientService;
    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(DataClientService dataTennisClientService, PlayerMapper playerMapper) {
        this.dataTennisClientService = dataTennisClientService;
        this.playerMapper = playerMapper;
    }
    @Override
    public List<PlayerDTO> getListPlayersByRank() {
        List<Player> playerList = dataTennisClientService.getAllPlayers();

        playerList.sort(Comparator.comparing(o -> o.getData().getRank()));
        LOGGER.info("getListPlayersByRank -  Rank sort = {}",
                playerList.stream().map(e->e.getData().getRank()).collect(Collectors.toList()));
        return playerMapper.toPlayerListDto(playerList);
    }
    @Override
    public PlayerDTO getPlayerById(Long playerId) {
        List<Player> playerList = dataTennisClientService.getAllPlayers();
        LOGGER.info("getPlayerById - id = {}", playerId);
        return playerMapper.toPlayerDto(playerList.stream().filter(e->e.getId().equals(String.valueOf(playerId)))
                .findFirst().orElseThrow(() -> new NoSuchElementException("player not found with id  = " + playerId)));
    }
}