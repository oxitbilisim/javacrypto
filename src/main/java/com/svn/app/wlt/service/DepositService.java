package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.Deposit;
import com.svn.app.wlt.entity.Order;
import com.svn.app.wlt.repository.DepositRepository;
import com.svn.app.wlt.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositService {
    private DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public Deposit findById(Long id){
        return depositRepository.findById(id).get();
    }

    public Deposit create(Deposit deposit){
        deposit.setId(null);
        return depositRepository.save(deposit);
    }

    public Deposit update(Deposit deposit){
        return depositRepository.save(deposit);
    }

    public void deleteById(Long id){
        depositRepository.deleteById(id);
    }

    public List<Deposit> saveAll(List<Deposit> depositList){
        return depositRepository.saveAll(depositList);
    }
}
