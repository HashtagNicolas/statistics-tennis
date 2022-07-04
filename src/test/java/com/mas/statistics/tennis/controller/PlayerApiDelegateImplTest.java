package com.mas.statistics.tennis.controller;

import com.mas.statistics.tennis.dto.CountryDTO;
import com.mas.statistics.tennis.dto.DataDTO;
import com.mas.statistics.tennis.dto.PlayerDTO;
import com.mas.statistics.tennis.service.PlayerService;
import org.junit.Before;
import  org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PlayerApiDelegateImplTest {

    @InjectMocks
    private PlayerApiDelegateImpl playerApiDelegate;
    @Mock
    private PlayerService playerService;
    private final List<PlayerDTO> playerDTOList = getPlayerDTOList();
    private final PlayerDTO playerDTO = getPlayerDTO();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        MockMvcBuilders.standaloneSetup(playerApiDelegate).build();
    }

    private List<PlayerDTO> getPlayerDTOList(){
        List<PlayerDTO> playerDTOS = new ArrayList<>();
        playerDTOS.add(getPlayerDTO());

        return playerDTOS;
    }
    private PlayerDTO getPlayerDTO(){

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(10L);
        playerDTO.setFirstname("firstname");
        playerDTO.setLastname("lastname");
        playerDTO.setShortname("FL");
        playerDTO.setSex("M");
        playerDTO.setCountry(getCountryDTO());
        playerDTO.setData(getDataDTO());

        return playerDTO;
    }

    private CountryDTO getCountryDTO(){
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode("FRA");
        countryDTO.setPicture("image.png");
        return countryDTO;
    }

    private DataDTO getDataDTO(){
        DataDTO dataDTO = new DataDTO();
        List<Integer> last = new ArrayList<>();
        last.add(1);
        last.add(0);
        last.add(1);
        last.add(1);
        last.add(0);
        dataDTO.setAge(22);
        dataDTO.setHeight(197);
        dataDTO.setWeight(78000);
        dataDTO.setPoints(1123);
        dataDTO.setRank(3);
        dataDTO.setLast(last);

        return dataDTO;
    }
    @Test
    void CODE_200_GetPlayerById_Test() {
        ResponseEntity<PlayerDTO> expected = ResponseEntity.ok(playerDTO);

        Mockito.when(playerService.getPlayerById(Mockito.any())).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> actual = playerApiDelegate.getPlayerById(10L);

        Assertions.assertEquals(HttpStatus.OK,actual.getStatusCode());
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void CODE_500_GetPlayerByStringId_NumberFormatException_Test() {

        NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
            playerApiDelegate.getPlayerById(Long.parseLong("Ten"));
        }, "NumberFormatException was expected");

        Assertions.assertEquals("For input string: \"Ten\"", thrown.getMessage());

    }

    @Test
    void CODE_200_getListPlayersByRank_Test(){
        ResponseEntity<List<PlayerDTO>> expected = ResponseEntity.ok(playerDTOList);

        Mockito.when(playerService.getListPlayersByRank()).thenReturn(playerDTOList);

        ResponseEntity<List<PlayerDTO>> actual = playerApiDelegate.getListPlayersByRank();

        Assertions.assertEquals(HttpStatus.OK,actual.getStatusCode());
        Assertions.assertEquals(expected, actual);
    }

}
