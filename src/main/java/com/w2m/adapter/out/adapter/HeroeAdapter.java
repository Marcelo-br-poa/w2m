package com.w2m.adapter.out.adapter;

import com.w2m.adapter.out.repository.HeroeRepository;
import com.w2m.application.out.HeroeConusultAllPort;
import com.w2m.application.out.HeroeConusultIdPort;
import com.w2m.application.out.HeroeConusultNamePort;
import com.w2m.application.out.HeroeSavedPort;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import com.w2m.domain.mapper.HeroeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Slf4j
public class HeroeAdapter implements
        HeroeSavedPort,
        HeroeConusultNamePort,
        HeroeConusultIdPort,
        HeroeConusultAllPort {

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
        var savedEntity = heroeRepository.save(heroeMapper.toEntityRequest(heroeRequest));
        return heroeMapper.toRequestEntity(savedEntity);
    }

    @Override
    public List<HeroeResponse> heroeConusultNamePort(String name) {
        return heroeMapper.listToResponseEntity(name);
    }

    @Override
    public HeroeResponse heroeConusultIdPort(Long id) {
        return heroeMapper.toRequestEntityId(id);
    }

    @Override
    public List<HeroeResponse> heroeConusultAllPort() {
        return heroeMapper.listToResponseEntityAll();
    }
}
