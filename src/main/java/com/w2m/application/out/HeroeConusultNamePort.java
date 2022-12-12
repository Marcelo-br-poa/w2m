package com.w2m.application.out;

import com.w2m.common.MessageResponse;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;

import java.util.List;

public interface HeroeConusultNamePort {

    List<HeroeResponse> heroeConusultNamePort(String name);
}
