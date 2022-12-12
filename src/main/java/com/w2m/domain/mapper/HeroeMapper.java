package com.w2m.domain.mapper;


import com.w2m.adapter.out.persiste.HeroeEntity;
import com.w2m.adapter.out.repository.HeroeRepository;
import com.w2m.common.exceptions.ValidatioinConsult;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HeroeMapper {

    private HeroeRepository heroeRepository;

    @Autowired
    public HeroeMapper(
            HeroeRepository heroeRepository
    ) {

        this.heroeRepository = heroeRepository;
    }

    public HeroeEntity toEntityRequest(HeroeRequest heroeRequest){
        return HeroeEntity
                .builder()
                .id(heroeRequest.getId())
                .name(heroeRequest.getName())
                .build();
    }

    public HeroeResponse toRequestEntity(HeroeEntity heroeEntity){
        return HeroeResponse
                .builder()
                .id(heroeEntity.getId())
                .name(heroeEntity.getName())
                .build();
    }

    public HeroeResponse toRequestEntityId(Long id){
        var consultEntity = heroeRepository.findById(id);
        if(consultEntity.isEmpty()){
            log.info("");
            throw new ValidatioinConsult(HttpStatus.INTERNAL_SERVER_ERROR, String.format("No hay registro con el ID informado: %d", id));
        }
        return HeroeResponse
                .builder()
                .id(consultEntity.get().getId())
                .name(consultEntity.get().getName())
                .build();
    }

    public List<HeroeResponse> listToResponseEntity(String name){

        List<HeroeResponse> listResponse = new ArrayList<>();

        var listEntity = heroeRepository.findByName(name);

        if (!listEntity.isEmpty()){
            listEntity.forEach(heroeEntity -> {
                HeroeResponse response = HeroeResponse
                        .builder()
                        .id(heroeEntity.getId())
                        .name(heroeEntity.getName())
                        .build();
                listResponse.add(response);
            });
        }

        return listResponse;
    }

    public List<HeroeResponse> listToResponseEntityAll(){

        List<HeroeResponse> listResponse = new ArrayList<>();

        var listEntity = heroeRepository.findAll();

        if (!listEntity.isEmpty()){
            listEntity.forEach(heroeEntity -> {
                HeroeResponse response = HeroeResponse
                        .builder()
                        .id(heroeEntity.getId())
                        .name(heroeEntity.getName())
                        .build();
                listResponse.add(response);
            });
        }

        return listResponse;
    }



}
