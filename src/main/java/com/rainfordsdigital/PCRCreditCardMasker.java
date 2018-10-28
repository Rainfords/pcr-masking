package com.rainfordsdigital;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class PCRCreditCardMasker {

  private static final String VISA_AND_MASTERCARD_MASK = "xxxxxx";

  private static final String AMEX_MASK = "xxxxxxx";

  private static final int MASK_START = 6;

  @Getter
  private PCRData dataRequiresMasking;

  public PCRCreditCardMasker(PCRData dataRequiresMasking) {
    this.dataRequiresMasking = dataRequiresMasking;
    locateAndMaskPCRData();
  }

  public void locateAndMaskPCRData() {
    // First remove all non-numerics
    String p = this.dataRequiresMasking.getParticulars().replaceAll("[^\\d]", StringUtils.EMPTY );
    String c = this.dataRequiresMasking.getCode().replaceAll("[^\\d]", StringUtils.EMPTY );
    String r = this.dataRequiresMasking.getReference().replaceAll("[^\\d]", StringUtils.EMPTY );
    //Case where complete CC in one of the fields

    if (findCCInSingleField(p, c, r)) {
      return;
    }

    if (findCCInTwoFields(p, c, r)) {
      return;
    }

    if (findCCInAllFields(p, c, r)) {
      return;
    }



  }

  private boolean findCCInSingleField(String p, String c, String r) {
    if (StringUtils.isNotBlank(p) && isCreditCardNumber(p)) {
      this.dataRequiresMasking.setParticulars(applyVisaMask(p, p));
      this.dataRequiresMasking.setMasked(true);
      return true;
    }

    if (StringUtils.isNotBlank(c) && isCreditCardNumber(c)) {
      this.dataRequiresMasking.setCode(applyVisaMask(c, c));
      this.dataRequiresMasking.setMasked(true);
      return true;
    }

    if (StringUtils.isNotBlank(r) && isCreditCardNumber(r)) {
      this.dataRequiresMasking.setReference(applyVisaMask(r, r));
      this.dataRequiresMasking.setMasked(true);
      return true;
    }
    return false;
  }

  private boolean findCCInTwoFields(String p, String c, String r) {
    List<List<String>> outerBase = Arrays.asList(Arrays.asList(p, c), Arrays.asList(c, r), Arrays.asList(p, r));
    for (List<String> base : outerBase) {
      PermutationIterable<String> permutationIterable =
          new PermutationIterable<>(base, new RecursiveCounter<>(base.size()));
      for (List<String> permutation : permutationIterable) {
        String candidate = joinParts(permutation);
        if (isCreditCardNumber(candidate)) {
          maskData(p, c, r, candidate);
          return true;
        }
      }
    }

    return false;
  }

  private boolean findCCInAllFields(String p, String c, String r) {
    List<String> base = Arrays.asList(p, c, r);
      PermutationIterable<String> permutationIterable =
          new PermutationIterable<>(base, new RecursiveCounter<>(base.size()));
      for (List<String> permutation : permutationIterable) {
        String candidate = joinParts(permutation);
        if (isCreditCardNumber(candidate)) {
          maskData(p, c, r, candidate);
          return true;
        }
    }

    return false;
  }

  private void maskData(String p, String c, String r, String candidate) {
    String pMasked = applyVisaMask(candidate, p);
    String cMasked = applyVisaMask(candidate, c);
    String rMasked = applyVisaMask(candidate, r);
    if (StringUtils.isNotBlank(pMasked)) {
      this.dataRequiresMasking.setParticulars(pMasked);
    }
    if (StringUtils.isNotBlank(cMasked)) {
      this.dataRequiresMasking.setCode(cMasked);
    }
    if (StringUtils.isNotBlank(rMasked)) {
      this.dataRequiresMasking.setReference(rMasked);
    }
    this.dataRequiresMasking.setMasked(true);
  }

  private boolean isCreditCardNumber(String candidateCreditCardNumber) {
    //mock visa or mastercard
    if ("4999122233334444".equals(candidateCreditCardNumber)) {
      return true;
      // Mock amex
    } else if ("375987654321001".equals(candidateCreditCardNumber)) {
      return true;
    }

    return false;
  }

  private String joinParts(List<String> parts) {
    StringBuilder sb = new StringBuilder();
    for (String part : parts) {
      sb.append(part);
    }
    return sb.toString();
  }

  private String applyVisaMask(String ccNumber, String partToMask) {
    String masked = StringUtils.overlay(ccNumber,
        VISA_AND_MASTERCARD_MASK, MASK_START, MASK_START + VISA_AND_MASTERCARD_MASK.length());
    return masked.substring(ccNumber.indexOf(partToMask), ccNumber.indexOf(partToMask) + partToMask.length());
  }

}
