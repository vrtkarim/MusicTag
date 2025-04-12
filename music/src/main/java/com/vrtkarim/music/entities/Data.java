package com.vrtkarim.music.entities;

import lombok.*;


@Builder
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter

public class Data {
    String composer ;
    String title ;
    String artist ;
    String album ;
    String genre ;
    String year ;
    String comment ;

}
