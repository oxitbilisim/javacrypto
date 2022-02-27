package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.dto.ExchangeInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExchangeInfoRepository extends JpaRepository<ExchangeInfo,String> {

    @Query("SELECT e FROM ExchangeInfo e WHERE e.baseAsset IN (:baseAssetList)")
    List<ExchangeInfo> getByBaseAsset(@Param("baseAssetList") List<String> baseAssetList);

    @Query("SELECT new com.svn.app.wlt.entity.dto.ExchangeInfoDto(e.symbol,e.baseAsset,e.quoteAsset,db.dominance,dq.dominance) FROM ExchangeInfo e " +
            " left join Dominance db on db.symbol = e.baseAsset" +
            " left join Dominance dq on dq.symbol = e.quoteAsset")
    List<ExchangeInfoDto> findAllAsDto();
}
