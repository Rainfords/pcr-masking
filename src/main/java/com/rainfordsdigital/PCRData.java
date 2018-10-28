package com.rainfordsdigital;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PCRData {

  private String particulars;
  private String code;
  private String reference;
  private boolean masked;

  public PCRData(String particulars, String code, String reference) {
    this.particulars = particulars;
    this.code = code;
    this.reference = reference;
  }

  @Override
  public String toString() {
    return "PCRData{" +
        "particulars='" + particulars + '\'' +
        ", code='" + code + '\'' +
        ", reference='" + reference + '\'' +
        ", masked=" + masked +
        '}';
  }
}
