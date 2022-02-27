package com.svn.app.wlt.entity.dto;

import com.svn.app.wlt.utility.Types;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletListDto {
    Long exchangeId;
    String exchangeCode;
    Long walletId;
    String exchangeName;
    String walletName;
    String description;
    Types.WalletStatus status;
    String publicKey;
    String privateKey;
}
