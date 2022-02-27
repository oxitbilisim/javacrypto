package com.svn.app.wlt.api.binance.domain.account.request;

import com.svn.app.wlt.api.binance.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request object for canceling an order.
 */
public class CancelOrderRequest extends OrderRequest {

  private Long orderId;

  private String origClientOrderId;

  public CancelOrderRequest(String symbol, Long orderId) {
    super(symbol);
    this.orderId = orderId;
  }

  public CancelOrderRequest(String symbol, String origClientOrderId) {
    super(symbol);
    this.origClientOrderId = origClientOrderId;
  }

  /**
   * Used to uniquely identify this cancel. Automatically generated by default.
   */
  private String newClientOrderId;

  public Long getOrderId() {
    return orderId;
  }

  public CancelOrderRequest orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  public String getOrigClientOrderId() {
    return origClientOrderId;
  }

  public CancelOrderRequest origClientOrderId(String origClientOrderId) {
    this.origClientOrderId = origClientOrderId;
    return this;
  }

  public String getNewClientOrderId() {
    return newClientOrderId;
  }

  public CancelOrderRequest newClientOrderId(String newClientOrderId) {
    this.newClientOrderId = newClientOrderId;
    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
        .append("orderId", orderId)
        .append("origClientOrderId", origClientOrderId)
        .append("newClientOrderId", newClientOrderId)
        .toString();
  }
}
