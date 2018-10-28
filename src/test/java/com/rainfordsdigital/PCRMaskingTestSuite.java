package com.rainfordsdigital;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PCRMaskingTestSuite {

  @Test
  public void shouldMaskSingleParticularsField() {
    PCRData data = new PCRData ("4999122233334444", "code", "reference");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("499912xxxxxx4444", data.getParticulars());
    assertEquals("code", data.getCode());
    assertEquals("reference", data.getReference());

  }

  @Test
  public void shouldMaskSingleCodeField() {
    PCRData data = new PCRData ("particulars", "4999122233334444", "reference");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("particulars", data.getParticulars());
    assertEquals("499912xxxxxx4444", data.getCode());
    assertEquals("reference", data.getReference());

  }

  @Test
  public void shouldMaskSingleReferenceField() {
    PCRData data = new PCRData ("particulars", "code", "4999122233334444");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("particulars", data.getParticulars());
    assertEquals("code", data.getCode());
    assertEquals("499912xxxxxx4444", data.getReference());

  }

  @Test
  public void shouldMaskPCFields() {
    PCRData data = new PCRData ("49991222", "33334444", "reference");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("499912xx", data.getParticulars());
    assertEquals("xxxx4444", data.getCode());
    assertEquals("reference", data.getReference());
  }

  @Test
  public void shouldMaskCRFields() {
    PCRData data = new PCRData ("particulars", "49991222", "33334444");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("particulars", data.getParticulars());
    assertEquals("499912xx", data.getCode());
    assertEquals("xxxx4444", data.getReference());
  }

  @Test
  public void shouldMaskPRFields() {
    PCRData data = new PCRData ("49991222", "code", "33334444");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("499912xx", data.getParticulars());
    assertEquals("code", data.getCode());
    assertEquals("xxxx4444", data.getReference());
  }

  @Test
  public void shouldMaskPRFieldsReversed() {
    PCRData data = new PCRData ("33334444", "code", "49991222");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("xxxx4444", data.getParticulars());
    assertEquals("code", data.getCode());
    assertEquals("499912xx", data.getReference());
  }

  @Test
  public void shouldMaskPCRFields() {
    PCRData data = new PCRData ("33334444", "4999", "1222");
    PCRCreditCardMasker masker = new PCRCreditCardMasker(data);

    assertEquals("xxxx4444", data.getParticulars());
    assertEquals("4999", data.getCode());
    assertEquals("12xx", data.getReference());
  }

}
