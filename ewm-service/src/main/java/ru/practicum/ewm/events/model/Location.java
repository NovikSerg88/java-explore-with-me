package ru.practicum.ewm.events.model;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Location {
    private float lat;
    private float lon;
}
