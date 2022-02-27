package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Withdraw;
import com.svn.app.wlt.entity.dto.WithdrawDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {

    @Query("select new com.svn.app.wlt.entity.dto.WithdrawDto(w) from Withdraw w  " +
            " where w.coin = :asset and w.balanceLeg is null order by w.applyTime")
    List<WithdrawDto> findAllByBaseAssetAndLegIsNullAsDto(@Param("asset") String asset);
}
