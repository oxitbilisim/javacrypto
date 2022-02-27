package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Trade;
import com.svn.app.wlt.entity.dto.TradeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade,Long> {

    @Query("select new com.svn.app.wlt.entity.dto.TradeDto(t,ei) from Trade t left join ExchangeInfo ei on t.symbol=ei.symbol where t.id in (:ids)")
    List<TradeDto> findAllByIdAsDTO(@Param("ids") List<Long> ids);

    @Query("select new com.svn.app.wlt.entity.dto.TradeDto(t,ei) from Trade t left join ExchangeInfo ei on t.symbol=ei.symbol where ei.baseAsset = :asset ")
    List<TradeDto> findAllByBaseAssetAsDTO(@Param("asset") String asset);

    @Query("select new com.svn.app.wlt.entity.dto.TradeDto(t,ei) from Trade t " +
            " left join ExchangeInfo ei on t.symbol=ei.symbol " +
            " where ei.baseAsset = :asset and t.balanceLeg is null order by t.time")
    List<TradeDto> findAllByBaseAssetAndLegIsNullAsDto(@Param("asset") String asset);

}
