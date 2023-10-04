package ru.practicum.ewm.events.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.model.Event;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public interface EventsRepository extends JpaRepository<Event, Long> {

    boolean existsByCategoryId(Long categoryId);

    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    Long countAllByIdIn(List<Long> ids);

    @Query("select e from Event e " +
            "where e.state = 'PUBLISHED' " +
            "and (coalesce(:text, null) is null or (lower(e.annotation) like lower(concat('%', :text, '%')) or lower(e.description) like lower(concat('%', :text, '%')))) " +
            "and (coalesce(:categories, null) is null or e.category.id in :categories) " +
            "and (coalesce(:paid, null) is null or e.paid = :paid) " +
            "and (coalesce(:rangeStart, null) is null or e.eventDate >= :rangeStart) " +
            "and (coalesce(:rangeEnd, null) is null or e.eventDate <= :rangeEnd) " +
            "and (:onlyAvailable = false or e.id in " +
            "(select r.event.id " +
            "from ParticipationRequest r " +
            "where r.status = 'CONFIRMED' " +
            "group by r.event.id " +
            "having e.participantLimit - count(id) > 0 " +
            "order by count(r.id))) ")
    List<Event> findEventsForPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable page);

    @Query("SELECT e from Event e " +
            "WHERE (:users is null or e.initiator.id in :users) " +
            "AND (:states is null or e.state in :states) " +
            "AND (:categories is null or e.category.id in :categories) " +
            "AND (coalesce(:rangeStart, null) is null or e.eventDate >= :rangeStart) " +
            "AND (coalesce(:rangeEnd, null) is null or e.eventDate <= :rangeEnd)")
    List<Event> findEventsForAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable page);

    @Query("select e from Event e " +
            "JOIN FETCH e.initiator " +
            "JOIN FETCH e.category " +
            "JOIN fetch e.location " +
            "WHERE e.id in :ids")
    List<Event> findAllByIdIn(List<Long> ids);

}
