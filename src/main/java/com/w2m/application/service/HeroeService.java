package com.w2m.application.service;

import com.w2m.application.in.*;
import com.w2m.application.out.*;
import com.w2m.common.GeneralMessages;
import com.w2m.common.exceptions.ValidatioinConsult;
import com.w2m.common.exceptions.ValidationRequest;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HeroeService implements
        HeroeSavedCase,
        HeroeConsultAllCase,
        HeroeConsultIdCase,
        HeroeConsultLikeNameCase,
        HeroeUpdateCase,
        HeroeDeleteCase{

    private HeroeSavedPort heroeSavedPort;
    private HeroeConusultNamePort heroeConusultNamePort;
    private HeroeConusultIdPort heroeConusultIdPort;

    private HeroeConusultAllPort heroeConusultAllPort;
    private HeroeConusultLikeNamePort heroeConusultLikeNamePort;
    private HeroeUpdatePort heroeUpdatePort;
    private HeroeDeletePort heroeDeletePort;
    private Validator validator;

    @Autowired
    public HeroeService(
            HeroeSavedPort heroeSavedPort,
            HeroeConusultNamePort heroeConusultNamePort,
            HeroeConusultIdPort heroeConusultIdPort,
            HeroeConusultAllPort heroeConusultAllPort,
            HeroeConusultLikeNamePort heroeConusultLikeNamePort,
            HeroeUpdatePort heroeUpdatePort,
            HeroeDeletePort heroeDeletePort,
            Validator validator
    ) {

        this.heroeSavedPort = heroeSavedPort;
        this.heroeConusultNamePort = heroeConusultNamePort;
        this.heroeConusultIdPort = heroeConusultIdPort;
        this.heroeConusultAllPort = heroeConusultAllPort;
        this.heroeConusultLikeNamePort = heroeConusultLikeNamePort;
        this.heroeUpdatePort = heroeUpdatePort;
        this.heroeDeletePort = heroeDeletePort;

        this.validator = validator;
    }

    @Override
    public HeroeResponse heroeSavedCase(HeroeRequest heroeRequest) {

        Set<ConstraintViolation<HeroeRequest>> constraintViolations = validator.validate(heroeRequest);
        if (!constraintViolations.isEmpty()) {
            String violationMessage = constraintViolations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));

            log.error(GeneralMessages.LOG_ERROR_REQUEST, violationMessage);

            throw new ValidationRequest(HttpStatus.BAD_REQUEST, String.format(GeneralMessages.MESSAGE_ERROR_REQUEST, violationMessage));
        }

        var consultName = heroeConusultNamePort.heroeConusultNamePort(heroeRequest.getName());

        if (consultName.isEmpty()) {
            heroeSavedPort.heroeSavedPort(heroeRequest);
        } else {
            throw new ValidationRequest(HttpStatus.NOT_FOUND, String.format(GeneralMessages.MESSAGE_EXISTE_HEROE, heroeRequest.getName()));
        }

        return null;
    }

    @Override
    public HeroeResponse heroeUpdateCase(Long id, HeroeRequest request){

        HeroeResponse response = new HeroeResponse();

        var consultHeroe = heroeConusultIdPort.heroeConusultIdPort(id);

        if(consultHeroe == null){
            log.info(GeneralMessages.LOG_NO_EXISTE_REGISTRO, id);
            throw new ValidatioinConsult(HttpStatus.INTERNAL_SERVER_ERROR, String.format(GeneralMessages.MESSAGE_NO_EXISTE_REGISTRO, id));
        }

        var consultName = heroeConusultNamePort.heroeConusultNamePort(request.getName());
        if(consultName.isEmpty()){
            response = heroeUpdatePort.heroeUpdatePort(id, request);
        }else{
            consultName.forEach(listEntity ->{
                if(listEntity.getName().equals(request.getName()) && listEntity.getId() != request.getId()){
                    log.info(GeneralMessages.LOG_UPDATE_EXISTE_HEROE, id);
                    throw new ValidatioinConsult(HttpStatus.INTERNAL_SERVER_ERROR, String.format(GeneralMessages.MESSAGE_UPDATE_EXISTE_HEROE, request.getName()));
                }
            });
        }

        return response;
    }

    @Override
    public void heroeDeleteCase(Long id) {

        var consultHeroe = heroeConusultIdPort.heroeConusultIdPort(id);
        if(consultHeroe == null){
            log.info(GeneralMessages.LOG_NO_EXISTE_REGISTRO, id);
            throw new ValidatioinConsult(HttpStatus.INTERNAL_SERVER_ERROR, String.format(GeneralMessages.MESSAGE_NO_EXISTE_REGISTRO, id));
        }

        heroeDeletePort.heroeDeletePort(id);
    }

    @Override
    public List<HeroeResponse> heroeConsultAllCase() {
        return heroeConusultAllPort.heroeConusultAllPort();
    }

    @Override
    public HeroeResponse heroeConsultIdCase(Long id) {

        return heroeConusultIdPort.heroeConusultIdPort(id);
    }

    @Override
    public List<HeroeResponse> heroeConsultLikeNameCase(String name) {

        var consultNameLikeHeroe = heroeConusultLikeNamePort.heroeConusultLikeNamePort(name);
        if (consultNameLikeHeroe.isEmpty()){
            throw new ValidatioinConsult(HttpStatus.INTERNAL_SERVER_ERROR, String.format(GeneralMessages.MESSAGE_NO_EXISTE_HEROE_LIKE, name));
        }

        return consultNameLikeHeroe;

    }

}
