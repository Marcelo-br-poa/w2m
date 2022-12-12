package com.w2m.adapter.in;


import com.w2m.application.in.HeroeConsultAllCase;
import com.w2m.application.in.HeroeConsultIdCase;
import com.w2m.application.in.HeroeSavedCase;
import com.w2m.common.MessageResponse;
import com.w2m.domain.HeroeRequest;
import com.w2m.domain.HeroeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public HeroeController(
            HeroeSavedCase heroeSavedCase,
            HeroeConsultAllCase heroeConsultAllCase,
            HeroeConsultIdCase heroeConsultIdCase
    ) {
        this.heroeSavedCase = heroeSavedCase;
        this.heroeConsultAllCase = heroeConsultAllCase;
        this.heroeConsultIdCase = heroeConsultIdCase;
    }

    @PostMapping
    public ResponseEntity savedHeroe(@RequestBody HeroeRequest heroeRequest){
        heroeSavedCase.heroeSavedCase(heroeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<HeroeResponse>> getHeroeAll(){
        var listHeroe =  heroeConsultAllCase.heroeConsultAllCase();
        return ResponseEntity.ok().body(listHeroe);
    }

    @GetMapping("/heroe/{heroeId}")
    public ResponseEntity<HeroeResponse> getHeroe(@PathVariable("heroeId") Long heroeId){
        var conultHeroe = heroeConsultIdCase.heroeConsultIdCase(heroeId);
        return ResponseEntity.ok().body(conultHeroe);
    }



}
