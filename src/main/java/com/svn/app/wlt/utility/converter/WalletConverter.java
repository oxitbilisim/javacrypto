package com.svn.app.wlt.utility.converter;

import org.springframework.core.convert.converter.Converter;
import com.svn.app.wlt.entity.Wallet;
import com.svn.app.wlt.entity.dto.WalletDto;


public class WalletConverter implements Converter<Wallet, WalletDto> {

    @Override
    public WalletDto convert(Wallet source) {
        return null;
    }


}
