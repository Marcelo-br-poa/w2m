package com.w2m.adapter.out.repository;

import com.w2m.adapter.out.persiste.HeroeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroeRepository extends JpaRepository<HeroeEntity, Long> {

    List<HeroeEntity> findByName(String name);

    @Query("Select s from HeroeEntity s where lower(s.name) like CONCAT('%',:name,'%')")
    List<HeroeEntity> listNameHeroe(@Param("name") String name);
}
