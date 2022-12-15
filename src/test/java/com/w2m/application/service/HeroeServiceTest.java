package com.w2m.application.service;

import com.w2m.application.out.*;
import com.w2m.common.GeneralMessages;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class HeroeServiceTest {

    @Mock
    private HeroeSavedPort heroeSavedPort;

    @Autowired
    private HeroeConusultNamePort heroeConusultNamePort;

    @Autowired
    private HeroeConusultIdPort heroeConusultIdPort;

    @Autowired
    private HeroeConusultAllPort heroeConusultAllPort;

    @Mock
    private HeroeConusultLikeNamePort heroeConusultLikeNamePort;

    @Mock
    private HeroeUpdatePort heroeUpdatePort;

    @Mock
    private HeroeDeletePort heroeDeletePort;

//    @Autowired
    @Mock
    private Validator validator;

    private Set<ConstraintViolation<HeroeRequest>> constraintViolations = new HashSet<>();

    private HeroeService heroeService;

    @Mock
    private HeroeService heroeServiceMock;

    private HeroeRequest heroeRequest;

    private HeroeResponse heroeResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        heroeService = new HeroeService(
                heroeSavedPort,
                heroeConusultNamePort,
                heroeConusultIdPort,
                heroeConusultAllPort,
                heroeConusultLikeNamePort,
                heroeUpdatePort,
                heroeDeletePort,
                validator
        );

        heroeRequest = HeroeRequest
                .builder()
                .name("Heroe Test")
                .build();

        heroeResponse = HeroeResponse
                .builder()
                .name("Nuevo Heroes")
                .build();

        when(validator.validate(heroeRequest)).thenReturn(constraintViolations);
        constraintViolations = validator.validate(heroeRequest);
        String violationMessage = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        log.error(GeneralMessages.LOG_ERROR_REQUEST, violationMessage);
    }



    @Test
    void heroeSavedCase() {

        when(heroeService.heroeSavedCase(heroeRequest)).thenReturn(heroeResponse);

        heroeSavedPort.heroeSavedPort(heroeRequest);
        Assertions.assertEquals("Heroe Test", heroeRequest.getName());

    }

    @Test
    void heroeUpdateCase() {
        HeroeRequest request = HeroeRequest
                .builder()
                .name("Heroe update")
                .build();

        heroeResponse.setName("Heroe update");

        when(heroeService.heroeUpdateCase(1L,request)).thenReturn(heroeResponse);
        Set<ConstraintViolation<HeroeRequest>> constraintViolations = validator.validate(heroeRequest);
        heroeUpdatePort.heroeUpdatePort(1L, heroeRequest);
        Assertions.assertEquals("Heroe update", heroeResponse.getName());

    }

    @Test
    void heroeDeleteCase() {
        Long heroeId = 1L;
        doNothing().when(heroeServiceMock).heroeDeleteCase(heroeId);
        heroeDeletePort.heroeDeletePort(heroeId);
        Assertions.assertEquals(1L, heroeId);
    }

    @Test
    void heroeConsultAllCase() {

        when(heroeService.heroeConsultAllCase()).then(InvocationOnMock::getArguments);
        heroeConusultAllPort.heroeConusultAllPort();
    }

    @Test
    void heroeConsultIdCase() {
        when(heroeService.heroeConsultIdCase(1L)).then(InvocationOnMock::getArguments);
        heroeConusultIdPort.heroeConusultIdPort(1L);

        Assertions.assertEquals("Nuevo Heroes", heroeResponse.getName());


    }

    @Test
    void heroeConsultLikeNameCase() {

        when(heroeServiceMock.heroeConsultLikeNameCase("Man")).then(InvocationOnMock::getArguments);
        heroeConusultLikeNamePort.heroeConusultLikeNamePort("man");
        Assertions.assertEquals("Nuevo Heroes", heroeResponse.getName());

    }
}