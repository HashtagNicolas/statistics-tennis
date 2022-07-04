package com.mas.statistics.tennis.model;

import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
public class Data {

    private int rank;
    private int points;
    private int weight;
    private int height;
    private int age;
    private List<Integer> last;
}
