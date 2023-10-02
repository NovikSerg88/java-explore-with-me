package ru.practicum.ewm.events.model;

import lombok.*;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @Embedded
    private Location location;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private EventState state;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compilation_id")
    private Compilation compilation;


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Event)) return false;
        Event other = (Event) obj;
        if (other.id == null || this.id == null) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Event.class.hashCode();
    }
}
