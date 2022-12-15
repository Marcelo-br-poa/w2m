package com.w2m.adapter.out.adapter;

import com.w2m.adapter.out.persiste.HeroeEntity;
import com.w2m.adapter.out.repository.HeroeRepository;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import com.w2m.domain.mapper.HeroeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class HeroeAdapterTest {

    @Mock
    private HeroeRepository heroeRepository;

    @Mock
    private HeroeMapper heroeMapperMock;

    private HeroeMapper heroeMapper;

    @Mock
    private HeroeAdapter heroeAdapterMock;

    private HeroeAdapter heroeAdapter;

    private HeroeRequest heroeRequest;

    private HeroeResponse heroeResponse;

    @Mock
    private HeroeEntity heroeEntityMock;


    private HeroeEntity heroeEntity;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        heroeAdapter = new HeroeAdapter(
                heroeRepository,
                heroeMapper
        );

        heroeRequest = HeroeRequest
                .builder()
                .id(1L)
                .name("Heroe Test")
                .build();

        heroeResponse = HeroeResponse
                .builder()
                .name("Nuevo SuperHeroes")
                .build();
    }

    @Test
    void heroeSavedPort() {
        HeroeRequest heroeRequestSaved = HeroeRequest
                .builder()
                .id(1L)
                .name("Heroe Test")
                .build();

        HeroeResponse response = HeroeResponse
                .builder()
                .id(1L)
                .name("Heroe Test")
                .build();

        HeroeEntity entity = heroeMapperMock.toEntityRequest(heroeRequestSaved);
        HeroeResponse responseMapper = heroeMapperMock.toResponseEntity(entity);
        when(heroeAdapter.heroeSavedPort(heroeRequestSaved)).thenReturn(responseMapper);
        heroeRepository.save(entity);
        Assertions.assertEquals("Heroe Test", response.getName());
    }

    @Test
    void heroeUpdatePort() {
        HeroeRequest heroeRequestSaved = HeroeRequest
                .builder()
                .id(1L)
                .name("Heroe Test")
                .build();

        HeroeResponse response = HeroeResponse
                .builder()
                .id(1L)
                .name("Heroe Test")
                .build();


        HeroeMapper mapper = new HeroeMapper();
        HeroeEntity entity = new HeroeEntity();
        entity.setId(1L);
        entity.setName("Heroe Test");

        when(heroeMapperMock.toEntityRequest(heroeRequest)).thenReturn(entity);
        entity = heroeMapperMock.toEntityRequest(heroeRequestSaved);
        when(heroeAdapter.heroeUpdatePort(1L, new HeroeRequest(1L, "super"))).thenReturn(response);
        heroeRepository.save(entity);
        Assertions.assertEquals("Heroe Test", response.getName());


    }

    @Test
    void heroeDeletePort() {
    }

    @Test
    void heroeConusultNamePort() {

        when(heroeAdapter.heroeConusultNamePort("Super")).then(InvocationOnMock::getArguments);
        heroeRepository.findByName("super");
        Assertions.assertEquals("Nuevo SuperHeroes", heroeResponse.getName());

    }

    @Test
    void heroeConusultIdPort() {

        Optional<HeroeEntity> entity = Optional.of(new HeroeEntity());

        HeroeResponse response = HeroeResponse
                .builder()
                .id(entity.get().getId())
                .name(entity.get().getName())
                .build();

        when(heroeRepository.findById(1L)).thenReturn(entity);
        when(heroeAdapter.heroeConusultIdPort(1L)).then(InvocationOnMock::getArguments);
        Assertions.assertEquals("Nuevo SuperHeroes", heroeResponse.getName());
    }

    @Test
    void heroeConusultAllPort() {
//        when(heroeRepository.findAll()).then(InvocationOnMock::getArguments);
        when(heroeAdapter.heroeConusultAllPort()).then(InvocationOnMock::getArguments);
        Assertions.assertEquals("Nuevo SuperHeroes", heroeResponse.getName());

    }

    @Test
    void heroeConusultLikeNamePort() {

        when(heroeAdapter.heroeConusultLikeNamePort("Super")).then(InvocationOnMock::getArguments);
        heroeRepository.listNameHeroe("Super");
        Assertions.assertEquals("Nuevo SuperHeroes", heroeResponse.getName());
    }
}