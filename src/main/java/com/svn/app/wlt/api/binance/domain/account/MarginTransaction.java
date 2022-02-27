package com.svn.app.wlt.api.binance.domain.account;

import com.svn.app.wlt.api.binance.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * MarginTransaction information.
 */
@JsonIgnoreProperties
public class MarginTransaction {

  private String tranId;

  public String getTranId() {
    return tranId;
  }

  public void setTranId(String tranId) {
    this.tranId = tranId;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
        .append("transactionId", tranId)
        .toString();
  }
}
