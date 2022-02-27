package com.svn.app.wlt.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svn.app.wlt.entity.*;
import com.svn.app.wlt.entity.dto.WalletDto;
import com.svn.app.wlt.entity.dto.WalletListDto;
import com.svn.app.wlt.repository.ExchangeRepository;
import com.svn.app.wlt.repository.WalletRepository;
import com.svn.app.wlt.utility.Types;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private WalletRepository walletRepository;
    private BalanceService balanceService;
    private TradeService tradeService;
    private OrderService orderService;
    private DepositService depositService;
    private WithdrawService withdrawService;
    private ExchangeRepository exchangeRepository;

    public WalletService(WalletRepository walletRepository,
                         BalanceService balanceService,
                         TradeService tradeService,
                         ExchangeRepository exchangeRepository,
                         OrderService orderService,
                         DepositService depositService,
                         WithdrawService withdrawService
                         ) {
        this.walletRepository = walletRepository;
        this.balanceService = balanceService;
        this.tradeService = tradeService;
        this.exchangeRepository = exchangeRepository;
        this.orderService = orderService;
        this.depositService = depositService;
        this.withdrawService = withdrawService;
    }

    public List<WalletListDto> getAllListDto(){
        return walletRepository.getWalletList();
    }

    public WalletDto findById(Long id){
        return walletRepository.findByIdDto(id);
    }

    public WalletDto create(WalletDto walletDto){
        walletDto.setId(null);
        Wallet wallet = walletRepository.save(fromDtoToEntity(walletDto));
        return fromEntityToDto(wallet);
    }

    public WalletDto update(WalletDto walletDto){
        Wallet wallet = walletRepository.save(fromDtoToEntity(walletDto));
        return fromEntityToDto(wallet);
    }

    public void deleteById(Long id){
        walletRepository.deleteById(id);
    }

    public void saveData(Long walletId,JSONObject jsonObject) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Wallet wallet = walletRepository.findById(walletId).get();
        if(jsonObject.has("balance")){
            List<Balance> balanceList = objectMapper.readValue(jsonObject.getJSONArray("balance").toString(), new TypeReference<List<Balance>>(){});
            balanceList = balanceList.parallelStream().map(i -> {
                i.setWallet(wallet);
                return i;
            }).collect(Collectors.toList());
            balanceService.saveAll(balanceList);
        }

        if(jsonObject.has("trade")){
            List<Trade> tradeList = objectMapper.readValue(jsonObject.getJSONArray("trade").toString(), new TypeReference<List<Trade>>(){});
            tradeList = tradeList.parallelStream().map(i -> {
                i.setWallet(wallet);
                return i;
            }).collect(Collectors.toList());
            tradeService.saveAll(tradeList);
        }

        if(jsonObject.has("order")){
            List<Order> orderList = objectMapper.readValue(jsonObject.getJSONArray("order").toString(), new TypeReference<List<Order>>(){});
            orderList = orderList.parallelStream().map(i -> {
                i.setWallet(wallet);
                return i;
            }).collect(Collectors.toList());
            orderService.saveAll(orderList);
        }

        if(jsonObject.has("deposit")){
            List<Deposit> depositList = objectMapper.readValue(jsonObject.getJSONArray("deposit").toString(), new TypeReference<List<Deposit>>(){});
            depositList = depositList.parallelStream().map(i -> {
                i.setWallet(wallet);
                return i;
            }).collect(Collectors.toList());
            depositService.saveAll(depositList);
        }

        if(jsonObject.has("withdraw")){
            List<Withdraw> withdrawList = objectMapper.readValue(jsonObject.getJSONArray("withdraw").toString(), new TypeReference<List<Withdraw>>(){});
            withdrawList = withdrawList.parallelStream().map(i -> {
                i.setWallet(wallet);
                return i;
            }).collect(Collectors.toList());
            withdrawService.saveAll(withdrawList);
        }
    }

    private Wallet fromDtoToEntity(WalletDto walletDto){
        Exchange exchange=null;
        if(walletDto.getExchangeId()!=null){
            exchange = exchangeRepository.findById(walletDto.getExchangeId()).get();
        }
        Wallet w = new Wallet();
        w.setId(walletDto.getId());
        w.setName(walletDto.getName());
        w.setDescription(walletDto.getDescription());
        w.setApiKey(walletDto.getApiKey());
        w.setSecretKey(walletDto.getSecretKey());
        w.setWalletUpdateDate(walletDto.getWalletUpdateDate());
        w.setSyncDate(walletDto.getSyncDate());
        w.setExchange(exchange);
        w.setStatus(walletDto.getStatus());

        return w;
    }

    private WalletDto fromEntityToDto(Wallet wallet){
        WalletDto w = new WalletDto();
        w.setId(wallet.getId());
        w.setName(wallet.getName());
        w.setDescription(wallet.getDescription());
        w.setApiKey(wallet.getApiKey());
        w.setSecretKey(wallet.getSecretKey());
        w.setWalletUpdateDate(wallet.getWalletUpdateDate());
        w.setSyncDate(wallet.getSyncDate());
        w.setExchangeId(wallet.getExchange()!=null?wallet.getExchange().getId():null);
        w.setStatus(wallet.getStatus());

        return w;
    }


}
