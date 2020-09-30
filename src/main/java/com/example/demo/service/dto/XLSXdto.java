package com.example.demo.service.dto;

import com.opencsv.bean.CsvBindByPosition;

public class XLSXdto {

    @CsvBindByPosition(position = 0)
    private String iuNo;
    @CsvBindByPosition(position = 1)
    private String type;
    @CsvBindByPosition(position = 2)
    private String holder;
    @CsvBindByPosition(position = 3)
    private String entryTime;
    @CsvBindByPosition(position = 4)
    private String exitTime;
    @CsvBindByPosition(position = 5)
    private String parkedTime;
    @CsvBindByPosition(position = 6)
    private String paidAmt;
    @CsvBindByPosition(position = 7)
    private String cardType;
    @CsvBindByPosition(position = 8)
    private String cardNo;
    @CsvBindByPosition(position = 9)
    private String vehicleNo;

    public String getIuNo() {
        return iuNo;
    }

    public void setIuNo(String iuNo) {
        this.iuNo = iuNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(String parkedTime) {
        this.parkedTime = parkedTime;
    }

    public String getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(String paidAmt) {
        this.paidAmt = paidAmt;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    @Override
    public String toString() {
        return "XLSdto{" +
                "iuNo='" + iuNo + '\'' +
                ", type='" + type + '\'' +
                ", holder='" + holder + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", exitTime='" + exitTime + '\'' +
                ", parkedTime='" + parkedTime + '\'' +
                ", paidAmt='" + paidAmt + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                '}';
    }
}
