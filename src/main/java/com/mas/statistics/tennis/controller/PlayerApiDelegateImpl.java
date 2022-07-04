package com.mas.statistics.tennis.controller;

import com.mas.statistics.tennis.dto.PlayerDTO;
import com.mas.statistics.tennis.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerApiDelegateImpl implements PlayerApiDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerApiDelegateImpl.class);
    private final PlayerService playerService;

    public PlayerApiDelegateImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public ResponseEntity<List<PlayerDTO>> getListPlayersByRank(){
        LOGGER.info("Start service : GetListPlayersByRank");
        return ResponseEntity.ok(playerService.getListPlayersByRank());
    }
    @Override
    public ResponseEntity<PlayerDTO> getPlayerById(Long playerId){
        LOGGER.info("Start service : getPlayerById - id = {} ",playerId);
        return ResponseEntity.ok(playerService.getPlayerById(playerId));
    }
}
