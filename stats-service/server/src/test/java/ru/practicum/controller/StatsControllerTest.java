package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatsService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.util.Constants.FORMATTER;

@WebMvcTest(controllers = StatsController.class)
public class StatsControllerTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    StatsService statsService;

    @Autowired
    private MockMvc mvc;

    private final EndpointHitDto endpointHitDto = EndpointHitDto.builder()
            .app("ewm-main-service")
            .uri("/events/1")
            .ip("ip")
            .timestamp(LocalDateTime.parse("2020-05-05 00:00:00", FORMATTER))
            .build();

    private final ViewStatsDto viewStatsDto = ViewStatsDto.builder()
            .app("ewm-main-service")
            .uri("/events/1")
            .hits(1L)
            .build();

    @Test
    void saveHitTest() throws Exception {
        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getStatsTest() throws Exception {
        when(statsService.getStats("2020-05-05 00:00:00", "2035-05-05 00:00:00", null, false)).thenReturn(List.of(viewStatsDto));

        mvc.perform(get("/stats")
                        .param("start", "2020-05-05 00:00:00")
                        .param("end", "2035-05-05 00:00:00")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(viewStatsDto.getApp()), String.class))
                .andExpect(jsonPath("$[0].uri", is(viewStatsDto.getUri()), String.class))
                .andExpect(jsonPath("$[0].hits", is(viewStatsDto.getHits()), Long.class));
    }
}
