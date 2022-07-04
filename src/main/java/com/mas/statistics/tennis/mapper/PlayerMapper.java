package com.mas.statistics.tennis.mapper;

import com.mas.statistics.tennis.dto.PlayerDTO;
import com.mas.statistics.tennis.model.Player;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDTO toPlayerDto (Player player);
    List<PlayerDTO> toPlayerListDto(List<Player> playerList);

}
