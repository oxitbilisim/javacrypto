package com.svn.app.wlt.controller;

import com.svn.app.wlt.service.BalanceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/calcutate-pnl/{asset}")
    public void calculatePNL(@PathVariable String asset){
        balanceService.calculatePNL(asset);
    }

    @PostMapping("/pnl/{asset}")
    public void getPNL(@PathVariable String asset){

    }

}
