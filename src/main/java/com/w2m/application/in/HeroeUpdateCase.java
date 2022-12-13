package com.w2m.application.in;

import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;

public interface HeroeUpdateCase {

    HeroeResponse heroeUpdateCase(Long id, HeroeRequest request);
}
