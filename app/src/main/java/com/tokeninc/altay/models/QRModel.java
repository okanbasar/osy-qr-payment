package com.tokeninc.altay.models;

/**
 * Created by Okan Engin Başar on 9.05.2019.
 */
public class QRModel {

    private String payloadFormatIndicator;  //Tag: 00
    private int transactionCurrency;        //Tag: 53
    private int transactionAmount;          //Tag: 54
    private String arcelikQRVersion;        //Tag: 80
    private String transactionType;         //Tag: 81
    private String receiptDatetime;         //Tag: 82
    private String receiptID;               //Tag: 83
    private String sessionID;               //Tag: 84
    private String vatStr;                  //Tag: 86
    private String posID;                   //Tag: 87
    private String secureQRSignature;       //Tag: 88
    private int batchNumber;                //Tag: 89

    private String completeQRcontent;       //RAW QR CONTENT

    public String getPayloadFormatIndicator() {
        return payloadFormatIndicator;
    }

    public void setPayloadFormatIndicator(String payloadFormatIndicator) {
        this.payloadFormatIndicator = payloadFormatIndicator;
    }

    public int getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(int transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getArcelikQRVersion() {
        return arcelikQRVersion;
    }

    public void setArcelikQRVersion(String arcelikQRVersion) {
        this.arcelikQRVersion = arcelikQRVersion;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReceiptDatetime() {
        return receiptDatetime;
    }

    public void setReceiptDatetime(String receiptDatetime) {
        this.receiptDatetime = receiptDatetime;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getVatStr() {
        return vatStr;
    }

    public void setVatStr(String vatStr) {
        this.vatStr = vatStr;
    }

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }

    public String getSecureQRSignature() {
        return secureQRSignature;
    }

    public void setSecureQRSignature(String secureQRSignature) {
        this.secureQRSignature = secureQRSignature;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getCompleteQRcontent() {
        return completeQRcontent;
    }

    public void setCompleteQRcontent(String completeQRcontent) {
        this.completeQRcontent = completeQRcontent;
    }
}
