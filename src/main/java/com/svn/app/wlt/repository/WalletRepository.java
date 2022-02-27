package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Wallet;
import com.svn.app.wlt.entity.dto.WalletDto;
import com.svn.app.wlt.entity.dto.WalletListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Query("SELECT new com.svn.app.wlt.entity.dto.WalletListDto(e.id,e.code,w.id,e.name,w.name,w.description,w.status,w.apiKey,w.secretKey)  " +
            "FROM Wallet w LEFT JOIN w.exchange e")
    List<WalletListDto> getWalletList();

    @Query("SELECT new com.svn.app.wlt.entity.dto.WalletDto(w.id,w.name,w.description,w.apiKey,w.secretKey,w.walletUpdateDate,w.syncDate,w.exchange.id,w.status) FROM Wallet w WHERE w.id = :id")
    WalletDto findByIdDto(@Param("id") Long id);

}
