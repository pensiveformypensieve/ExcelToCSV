package com.example.demo.service.dto;

import com.opencsv.bean.CsvBindByPosition;

public class XLSdto {
    @CsvBindByPosition(position = 0)
    private String iuNo;
    @CsvBindByPosition(position = 1)
    private String cashCardNo;
    @CsvBindByPosition(position = 2)
    private String ES;
    @CsvBindByPosition(position = 3)
    private String entryTime;
    @CsvBindByPosition(position = 4)
    private String XS;
    @CsvBindByPosition(position = 5)
    private String exitTime;
    @CsvBindByPosition(position = 6)
    private String parkedTime;
    @CsvBindByPosition(position = 7)
    private String paid;
    @CsvBindByPosition(position = 8)
    private String parkingType;
    @CsvBindByPosition(position = 9)
    private String cardType;
    @CsvBindByPosition(position = 10)
    private String VehicleNo;
    @CsvBindByPosition(position = 11)
    private String zoneName;

    public String getIuNo() {
        return iuNo;
    }

    public void setIuNo(String iuNo) {
        this.iuNo = iuNo;
    }

    public String getCashCardNo() {
        return cashCardNo;
    }

    public void setCashCardNo(String cashCardNo) {
        this.cashCardNo = cashCardNo;
    }

    public String getES() {
        return ES;
    }

    public void setES(String ES) {
        this.ES = ES;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getXS() {
        return XS;
    }

    public void setXS(String XS) {
        this.XS = XS;
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

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    @Override
    public String toString() {
        return "XLSdto{" +
                "iuNo='" + iuNo + '\''
                + ", cashCardNo='" + cashCardNo + '\''
                + ", ES='" + ES + '\''
                + ", entryTime='" + entryTime + '\''
                + ", XS='" + XS + '\''
                + ", exitTime='" + exitTime + '\''
                + ", parkedTime='" + parkedTime + '\''
                + ", paid='" + paid + '\''
                + ", parkingType='" + parkingType + '\''
                + ", cardType='" + cardType + '\''
                + ", VehicleNo='" + VehicleNo + '\''
                + ", zoneName='" + zoneName + '\'' +
                '}';
    }
}
