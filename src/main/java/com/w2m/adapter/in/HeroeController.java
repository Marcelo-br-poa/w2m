package com.w2m.adapter.in;


import com.w2m.application.in.*;
import com.w2m.common.GeneralMessages;
import com.w2m.common.MessageResponse;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heroe")
@Slf4j
public class HeroeController {

    private HeroeSavedCase heroeSavedCase;
    private HeroeConsultAllCase heroeConsultAllCase;
    private HeroeConsultIdCase heroeConsultIdCase;
    private HeroeConsultLikeNameCase heroeConsultLikeNameCase;
    private HeroeUpdateCase heroeUpdateCase;
    private HeroeDeleteCase heroeDeleteCase;

    @Autowired
    public HeroeController(
            HeroeSavedCase heroeSavedCase,
            HeroeConsultAllCase heroeConsultAllCase,
            HeroeConsultIdCase heroeConsultIdCase,
            HeroeConsultLikeNameCase heroeConsultLikeNameCase,
            HeroeUpdateCase heroeUpdateCase,
            HeroeDeleteCase heroeDeleteCase
    ) {
        this.heroeSavedCase = heroeSavedCase;
        this.heroeConsultAllCase = heroeConsultAllCase;
        this.heroeConsultIdCase = heroeConsultIdCase;
        this.heroeConsultLikeNameCase = heroeConsultLikeNameCase;
        this.heroeUpdateCase = heroeUpdateCase;
        this.heroeDeleteCase = heroeDeleteCase;
    }

    @PostMapping
    @CacheEvict(value = "getHeroeAll", allEntries = true)
    public ResponseEntity savedHeroe(@RequestBody HeroeRequest heroeRequest){
        heroeSavedCase.heroeSavedCase(heroeRequest);
        log.info(GeneralMessages.LOG_LIMPIO_CACHE);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{heroeId}")
    @CacheEvict(value = "getHeroeAll", allEntries = true)
    public ResponseEntity<HeroeResponse> updateHeroe(@PathVariable("heroeId") Long heroeId, @RequestBody HeroeRequest request){
        var updateHeroe = heroeUpdateCase.heroeUpdateCase(heroeId, request);
        log.info(GeneralMessages.LOG_LIMPIO_CACHE);
        return ResponseEntity.ok(updateHeroe);
    }

    @DeleteMapping("/{heroeId}")
    @CacheEvict(value = "getHeroeAll", allEntries = true)
    public ResponseEntity deleteHeroe(@PathVariable("heroeId") Long heroeId){
        heroeDeleteCase.heroeDeleteCase(heroeId);
        log.info(GeneralMessages.LOG_LIMPIO_CACHE);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    @Cacheable(value = "getHeroeAll")
    public ResponseEntity<List<HeroeResponse>> getHeroeAll(){
        var listHeroe =  heroeConsultAllCase.heroeConsultAllCase();
        log.info(GeneralMessages.LOG_CACHE,"getHeroeAll");
        return ResponseEntity.ok().body(listHeroe);
    }

    @GetMapping("/list/{nameHeroe}")
    public ResponseEntity<List<HeroeResponse>> getHeroeName(@PathVariable("nameHeroe") String nameHeroe){
        var listHeroe =  heroeConsultLikeNameCase.heroeConsultLikeNameCase(nameHeroe);
        return ResponseEntity.ok().body(listHeroe);
    }

    @GetMapping("/heroe/{heroeId}")
    public ResponseEntity<HeroeResponse> getHeroe(@PathVariable("heroeId") Long heroeId){
        var conultHeroe = heroeConsultIdCase.heroeConsultIdCase(heroeId);
        return ResponseEntity.ok().body(conultHeroe);
    }



}
