package com.mas.statistics.tennis.model;

import lombok.NoArgsConstructor;
@lombok.Data
@NoArgsConstructor
public class Player {

    private String id;
    private String firstname;
    private String lastname;
    private String shortname;
    private String sex;
    private String picture;
    private Country country;
    private Data data;

}
