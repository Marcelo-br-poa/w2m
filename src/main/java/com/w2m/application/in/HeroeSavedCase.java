package com.w2m.application.in;

import com.w2m.common.MessageResponse;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;

public interface HeroeSavedCase {

    HeroeResponse heroeSavedCase(HeroeRequest heroeRequest);

}
