package com.w2m.application.out;

import com.w2m.adapter.out.persiste.HeroeEntity;
import com.w2m.common.MessageResponse;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;

public interface HeroeSavedPort {

    HeroeResponse heroeSavedPort(HeroeRequest heroeRequest);
}
