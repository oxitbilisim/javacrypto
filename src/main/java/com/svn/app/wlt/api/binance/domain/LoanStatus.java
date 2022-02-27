package com.svn.app.wlt.api.binance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Status of a submitted order.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum LoanStatus {
  PENDING, CONFIRMED, FAILED
}
