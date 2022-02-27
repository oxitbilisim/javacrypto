package com.svn.app.wlt.controller;

import com.svn.app.wlt.entity.Wallet;
import com.svn.app.wlt.entity.dto.WalletDto;
import com.svn.app.wlt.entity.dto.WalletListDto;
import com.svn.app.wlt.service.WalletService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping()
    public List<WalletListDto> getAll(){
        return walletService.getAllListDto();
    }

    @GetMapping("/{id}")
    public WalletDto getOne(@PathVariable Long id){
        return walletService.findById(id);
    }

    @PostMapping()
    public WalletDto create(@RequestBody WalletDto WalletDto){
        return walletService.create(WalletDto);
    }

    @PostMapping("/data/{walletId}")
    public void saveData(@PathVariable Long walletId,@RequestBody String jsonData) throws Exception {
        walletService.saveData(walletId,new JSONObject(jsonData));
    }

    @PutMapping()
    public WalletDto update(@RequestBody WalletDto WalletDto){
        return walletService.update(WalletDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        walletService.deleteById(id);
    }
}
