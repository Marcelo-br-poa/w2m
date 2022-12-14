package com.w2m.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.application.in.*;
import com.w2m.application.service.HeroeService;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.mapper.HeroeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class HeroeControllerTest {

    @Autowired
    private HeroeSavedCase heroeSavedCase;

    @Autowired
    private HeroeConsultAllCase heroeConsultAllCase;

    @Autowired
    private HeroeConsultIdCase heroeConsultIdCase;

    @Autowired
    private HeroeConsultLikeNameCase heroeConsultLikeNameCase;

    @Autowired
    private HeroeUpdateCase heroeUpdateCase;

    @Autowired
    private HeroeDeleteCase heroeDeleteCase;
    HeroeController heroeController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HeroeService heroeServiceMock;

    @Mock
    private HeroeMapper heroeMapper;

    @BeforeEach
    void setUp() {
        heroeController = new HeroeController(
                heroeSavedCase,
                heroeConsultAllCase,
                heroeConsultIdCase,
                heroeConsultLikeNameCase,
                heroeUpdateCase,
                heroeDeleteCase
        );
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savedHeroe() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/heroe")
                        .content(jsonRequest(new HeroeRequest(null, "SuperMan")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateHeroe() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/heroe/1")
                        .content(jsonRequest(new HeroeRequest(null, "SuperMan")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteHeroe() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/heroe/1")
                        .content(jsonRequest(new HeroeRequest(null, "SuperMan")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @Test
    void getHeroeAll() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/heroe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getHeroeName() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/heroe/list/man")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getHeroe() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/heroe/heroe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String jsonRequest(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}