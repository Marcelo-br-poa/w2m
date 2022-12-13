package com.w2m.domain.mapper;


import com.w2m.adapter.out.persiste.HeroeEntity;
import com.w2m.adapter.out.repository.HeroeRepository;
import com.w2m.common.GeneralMessages;
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

    public HeroeEntity toEntityRequest(HeroeRequest heroeRequest){
        return HeroeEntity
                .builder()
                .id(heroeRequest.getId())
                .name(heroeRequest.getName())
                .build();
    }

    public HeroeResponse toResponseEntity(HeroeEntity heroeEntity){
        return HeroeResponse
                .builder()
                .id(heroeEntity.getId())
                .name(heroeEntity.getName())
                .build();
    }

//    public HeroeResponse toRequestEntityId(HeroeEntity entity){
//
//        return HeroeResponse
//                .builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .build();
//    }

    public List<HeroeResponse> listToResponseEntity(List<HeroeEntity> listEntity){

        List<HeroeResponse> listResponse = new ArrayList<>();
        listEntity.forEach(heroeEntity -> {
            HeroeResponse response = HeroeResponse
                    .builder()
                    .id(heroeEntity.getId())
                    .name(heroeEntity.getName())
                    .build();
            listResponse.add(response);
        });

        return listResponse;
    }

}
