package ru.practicum.ewm.compilations.model;

import lombok.*;
import ru.practicum.ewm.events.model.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events = new ArrayList<>();
    @Column(name = "pinned")
    private Boolean pinned;
    @Column(name = "title")
    private String title;
}
