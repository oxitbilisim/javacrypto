package com.svn.app.wlt.controller;

import com.svn.app.wlt.entity.Exchange;
import com.svn.app.wlt.entity.dto.ExchangeDto;
import com.svn.app.wlt.service.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping()
    public List<ExchangeDto> getAll(){
        return exchangeService.findAll();
    }
/*
    @GetMapping("/{id}")
    public Wallet getOne(@PathVariable Long id){
        return WalletRepository.findById(id).get();
    }

    @PostMapping()
    public Wallet create(@RequestBody Wallet Wallet){
        Wallet.setId(null);
        return WalletRepository.save(Wallet);
    }

    @PutMapping()
    public Wallet update(@RequestBody Wallet Wallet){
        Wallet.setId(null);
        return WalletRepository.save(Wallet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        WalletRepository.deleteById(id);
    }
    */
}
