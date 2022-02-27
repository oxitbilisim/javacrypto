package com.svn.app.wlt.controller;

import com.svn.app.wlt.api.ExchangeService;
import com.svn.app.wlt.entity.Exchange;
import com.svn.app.core.entity.Language;
import com.svn.app.wlt.repository.ExchangeRepository;
import com.svn.app.core.repository.LanguageRepository;
import com.svn.app.wlt.utility.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/lang-img/{lang}")
    public void saveImageFile(@PathVariable String lang,@RequestParam("file") MultipartFile file) throws IOException {
        Optional<Language> lng = languageRepository.findById(lang);
        byte[] bytes = file.getBytes();
        lng.get().setFlagBase64(bytes);
        languageRepository.save(lng.get());
    }


    @RequestMapping("/hello")
    public String home(){
        ExchangeService s = new ExchangeService(applicationContext, Types.Exchange.BINANCE);
        s.instance().getKlines("BNBUSDT","",1619207160000L,1619207180000L,1);
        return "hello";
    }

    @GetMapping("/user-1")
    @Transactional
    public List<Exchange> getUser1(){
        return exchangeRepository.findAll();

    }
    @GetMapping("/user-2")
    public List<Exchange> getUser2(){
        return exchangeRepository.findAll();

    }
}
