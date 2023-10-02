package ru.practicum.ewm.requests.model;

import lombok.*;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.requests.dto.Status;
import ru.practicum.ewm.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "requests")
@Entity
@Builder
@AllArgsConstructor
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
