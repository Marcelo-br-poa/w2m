package com.w2m.adapter.out.adapter;

import com.w2m.adapter.out.persiste.HeroeEntity;
import com.w2m.adapter.out.repository.HeroeRepository;
import com.w2m.application.out.*;
import com.w2m.common.GeneralMessages;
import com.w2m.common.exceptions.ValidatioinConsult;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import com.w2m.domain.mapper.HeroeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class HeroeAdapter implements
        HeroeSavedPort,
        HeroeConusultNamePort,
        HeroeConusultIdPort,
        HeroeConusultAllPort,
        HeroeConusultLikeNamePort,
        HeroeUpdatePort,
        HeroeDeletePort{

    private HeroeRepository heroeRepository;
    private HeroeMapper heroeMapper;

    @Autowired
    public HeroeAdapter(
            HeroeRepository heroeRepository,
            HeroeMapper heroeMapper
    ) {
        this.heroeRepository = heroeRepository;
        this.heroeMapper = heroeMapper;
    }

    @Override
    @Transactional
    public HeroeResponse heroeSavedPort(HeroeRequest heroeRequest) {
        var savedEntity =  HeroeEntity
                .builder()
                .id(heroeRequest.getId())
                .name(heroeRequest.getName())
                .build();

        return HeroeResponse
                .builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .build();
    }

    @Override
    @Transactional
    public HeroeResponse heroeUpdatePort(Long id, HeroeRequest request) {

        request.setId(id);

        HeroeEntity entity = HeroeEntity
                .builder()
                .id(request.getId())
                .name(request.getName())
                .build();

                heroeRepository.save(entity);

        return  HeroeResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    @Transactional
    public void heroeDeletePort(Long id) {
        heroeRepository.deleteById(id);
    }

    @Override
    public List<HeroeResponse> heroeConusultNamePort(String name) {

        var consultNameEntity = heroeRepository.findByName(name);

        if (!consultNameEntity.isEmpty()){
            return heroeMapper.listToResponseEntity(consultNameEntity);
        }

        return Collections.emptyList();
    }

    @Override
    public HeroeResponse heroeConusultIdPort(Long id) {

        var consultEntity = heroeRepository.findById(id);
        if(consultEntity.isEmpty()){
            log.info(GeneralMessages.LOG_NO_EXISTE_REGISTRO, id);
            throw new ValidatioinConsult(HttpStatus.INTERNAL_SERVER_ERROR, String.format(GeneralMessages.MESSAGE_NO_EXISTE_REGISTRO, id));
        }

        return HeroeResponse
                .builder()
                .id(consultEntity.get().getId())
                .name(consultEntity.get().getName())
                .build();
    }

    @Override
    public List<HeroeResponse> heroeConusultAllPort() {
        log.info(GeneralMessages.LOG_PROCESO,"Inicio", "consulta", "heroeConusultAllPort");
        var listEntity = heroeRepository.findAll();
        List<HeroeResponse> listResponse = new ArrayList<>();
        if (!listEntity.isEmpty()){
            listResponse = heroeMapper.listToResponseEntity(listEntity);
        }
        log.info(GeneralMessages.LOG_PROCESO,"Fin", "consulta", "heroeConusultAllPort");
        return listResponse;
    }

    @Override
    public List<HeroeResponse> heroeConusultLikeNamePort(String name) {

        var listEntity = heroeRepository.listNameHeroe(name.toLowerCase());

        List<HeroeResponse> listResponse = new ArrayList<>();

        listEntity.forEach(entity -> {
            listResponse.add(new HeroeResponse(entity.getId(), entity.getName()));
                }

        );

        return listResponse;

    }

}
