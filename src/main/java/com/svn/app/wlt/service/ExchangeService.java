package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.Exchange;
import com.svn.app.wlt.entity.dto.ExchangeDto;
import com.svn.app.wlt.repository.ExchangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {
    private ExchangeRepository exchangeRepository;

    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public List<ExchangeDto> findAll(){
        return exchangeRepository.findAllDto();
    }
}
