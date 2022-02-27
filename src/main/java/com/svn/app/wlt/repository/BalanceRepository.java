package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance,Long> {

    Balance findByAsset(String asset);
}
