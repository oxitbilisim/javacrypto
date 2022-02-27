package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.Deposit;
import com.svn.app.wlt.entity.Withdraw;
import com.svn.app.wlt.repository.DepositRepository;
import com.svn.app.wlt.repository.WithdrawRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawService {
    private WithdrawRepository withdrawRepository;

    public WithdrawService(WithdrawRepository withdrawRepository) {
        this.withdrawRepository = withdrawRepository;
    }

    public Withdraw findById(Long id){
        return withdrawRepository.findById(id).get();
    }

    public Withdraw create(Withdraw withdraw){
        withdraw.setId(null);
        return withdrawRepository.save(withdraw);
    }

    public Withdraw update(Withdraw withdraw){
        return withdrawRepository.save(withdraw);
    }

    public void deleteById(Long id){
        withdrawRepository.deleteById(id);
    }

    public List<Withdraw> saveAll(List<Withdraw> withdrawList){
        return withdrawRepository.saveAll(withdrawList);
    }
}
