package com.w2m.adapter.out.repository;

import com.w2m.adapter.out.persiste.HeroeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class HeroeRepositoryTest {

    @Autowired
    private HeroeRepository heroeRepository;

    @Mock
    private HeroeRepository heroeRepositoryMock;


    @Test
    void findByName() {

        HeroeEntity entity = HeroeEntity
                .builder()
                .id(1L)
                .name("SuperMan")
                .build();

        List<HeroeEntity> listEntity = heroeRepository.findByName("SuperMan");
        when(heroeRepositoryMock.findByName("SuperMan")).thenReturn(listEntity);
        assertFalse(listEntity.isEmpty());
        Assertions.assertEquals("SuperMan", entity.getName());
    }

    @Test
    void listNameHeroe() {
        HeroeEntity entity = HeroeEntity
                .builder()
                .id(1L)
                .name("SuperMan")
                .build();

        List<HeroeEntity> listEntity = heroeRepository.listNameHeroe("man");
        when(heroeRepositoryMock.listNameHeroe("Man")).thenReturn(listEntity);
        assertFalse(listEntity.isEmpty());
        Assertions.assertEquals("SuperMan", entity.getName());


    }
}