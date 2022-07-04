package com.mas.statistics.tennis.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mas.statistics.tennis.dto.CountryDTO;
import com.mas.statistics.tennis.dto.DataDTO;
import com.mas.statistics.tennis.dto.PlayerDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerApiDelegateImpl playerApiDelegate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final PlayerDTO playerDTO = getPlayerDTO();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup(){
       mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private PlayerDTO getPlayerDTO(){

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(52L);
        playerDTO.setFirstname("Novak");
        playerDTO.setLastname("Djokovic");
        playerDTO.setShortname("N.DJO");
        playerDTO.setSex("M");
        playerDTO.setPicture("https://data.latelier.co/training/tennis_stats/resources/Djokovic.png");
        playerDTO.setCountry(getCountryDTO());
        playerDTO.setData(getDataDTO());

        return playerDTO;
    }

    private CountryDTO getCountryDTO(){
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode("SRB");
        countryDTO.setPicture("https://data.latelier.co/training/tennis_stats/resources/Serbie.png");
        return countryDTO;
    }

    private DataDTO getDataDTO(){
        DataDTO dataDTO = new DataDTO();
        List<Integer> last = new ArrayList<>();
        last.add(1);
        last.add(1);
        last.add(1);
        last.add(1);
        last.add(1);
        dataDTO.setAge(31);
        dataDTO.setHeight(188);
        dataDTO.setWeight(80000);
        dataDTO.setPoints(2542);
        dataDTO.setRank(2);
        dataDTO.setLast(last);

        return dataDTO;
    }

    @Test
    public void CODE_200_GetPlayerById_test() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/tennis/player/52")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        PlayerDTO actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertEquals(playerDTO,actual);
    }

    @Test
    public void CODE_200_GetPlayersByRank_test() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/tennis/player/players")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        List<PlayerDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        List<PlayerDTO> expectActualASC = new ArrayList<>(actual);
        expectActualASC.sort(Comparator.comparing(e -> e.getData().getRank()));
        assertEquals(actual, expectActualASC);
    }

}
