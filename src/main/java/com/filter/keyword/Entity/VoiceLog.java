package com.filter.keyword.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceLog {
    private Integer status;
    private String description;
    private String text_voice;
}
