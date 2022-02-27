package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.BalanceLeg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceLegRepository extends JpaRepository<BalanceLeg,Long> {

    @Query("select l from BalanceLeg l where l.balance.asset = :asset and l.isOpen = true")
    BalanceLeg findByBalanceIdAndIsOpenTrue(@Param("asset") String asset);
}
