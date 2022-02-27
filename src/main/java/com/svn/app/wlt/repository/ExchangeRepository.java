package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Exchange;
import com.svn.app.wlt.entity.dto.ExchangeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange,Long> {

    @Query(value = "select new com.svn.app.wlt.entity.dto.ExchangeDto(e.id,e.code,e.name,e.description,e.rowNum,e.logo,e.active) from Exchange e")
    List<ExchangeDto> findAllDto();
}
