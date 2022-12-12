package com.w2m.application.service;

import com.w2m.application.in.HeroeConsultAllCase;
import com.w2m.application.in.HeroeConsultIdCase;
import com.w2m.application.in.HeroeSavedCase;
import com.w2m.application.out.HeroeConusultAllPort;
import com.w2m.application.out.HeroeConusultIdPort;
import com.w2m.application.out.HeroeConusultNamePort;
import com.w2m.application.out.HeroeSavedPort;
import com.w2m.common.GeneralMessages;
import com.w2m.common.MessageResponse;
import com.w2m.common.exceptions.ValidationRequest;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        HeroeConsultIdCase {

    private HeroeSavedPort heroeSavedPort;
    private HeroeConusultNamePort heroeConusultNamePort;
    private HeroeConusultIdPort heroeConusultIdPort;

    private HeroeConusultAllPort heroeConusultAllPort;
    private Validator validator;

    @Autowired
    public HeroeService(
            HeroeSavedPort heroeSavedPort,
            HeroeConusultNamePort heroeConusultNamePort,
            HeroeConusultIdPort heroeConusultIdPort,
            HeroeConusultAllPort heroeConusultAllPort,
            Validator validator
    ) {

        this.heroeSavedPort = heroeSavedPort;
        this.heroeConusultNamePort = heroeConusultNamePort;
        this.heroeConusultIdPort = heroeConusultIdPort;
        this.heroeConusultAllPort = heroeConusultAllPort;

        this.validator = validator;
    }

    @Override
    public void heroeSavedCase(HeroeRequest heroeRequest) {

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
    }

    @Override
    public List<HeroeResponse> heroeConsultAllCase() {
        return heroeConusultAllPort.heroeConusultAllPort();
    }

    @Override
    public HeroeResponse heroeConsultIdCase(Long id) {
        return heroeConusultIdPort.heroeConusultIdPort(id);
    }
}
