package com.example.demo.domain;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;
import static org.springframework.data.jpa.domain.AbstractAuditable_.lastModifiedDate;

@Entity
@Table(name = "parking_transaction")
public class ParkingTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "car_plate_no", length = 20, nullable = false)
    private String carPlateNo;

    @NotNull
    @Size(max = 20)
    @Column(name = "vehicle_type", length = 20, nullable = false)
    private String vehicleType;

    @NotNull
    @Column(name = "car_park_id", nullable = false)
    private Long carParkId;

    @Column(name = "entry_date_time", nullable = false)
    private ZonedDateTime entryDateTime;

    @LastModifiedDate
    @Column(name = "exit_date_time", nullable = true)
    private ZonedDateTime exitDateTime;

    @Column(name = "allow_open", nullable = true)
    private Integer allowOpen;

    @Column(name = "entry", nullable = true)
    private Integer entry;

    @Size(max = 50)
    @Column(name = "entry_session_id", length = 50, nullable = false)
    private String entrySessionId;

    @Size(max = 50)
    @Column(name = "exit_session_id", length = 50, nullable = true)
    private String exitSessionId;

    @Column(name = "payment_amount", nullable = true)
    private Long paymentAmount;

    @Column(name = "tran_amount", nullable = true)
    private Long tranAmount;

    @Column(name = "duration", nullable = true)
    private Integer duration;

    @Size(max = 50)
    @Column(name = "driver_code", length = 50, nullable = true)
    private String driverCode;

    @Column(name = "parking_type_id", nullable = true)
    private Integer parkingTypeId;

    @Column(name = "transaction_id", nullable = true)
    private Long transactionId;

    @Size(max = 256)
    @Column(name = "notification_token", length = 256, nullable = true)
    private String notificationToken;

    @Column(name = "status", nullable = true)
    private Integer status;

    @Column(name = "remarks", length = 256, nullable = true)
    private String remarks;

    @Column(name = "modified_details", nullable = true)
    private String modifiedDetails;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)

    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false)

    private ZonedDateTime createdDate = ZonedDateTime.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)

    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")

    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getCarParkId() {
        return carParkId;
    }

    public void setCarParkId(Long carParkId) {
        this.carParkId = carParkId;
    }

    public ZonedDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(ZonedDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public ZonedDateTime getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(ZonedDateTime exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public Integer getAllowOpen() {
        return allowOpen;
    }

    public void setAllowOpen(Integer allowOpen) {
        this.allowOpen = allowOpen;
    }

    public Integer getEntry() {
        return entry;
    }

    public void setEntry(Integer entry) {
        this.entry = entry;
    }

    public String getEntrySessionId() {
        return entrySessionId;
    }

    public void setEntrySessionId(String entrySessionId) {
        this.entrySessionId = entrySessionId;
    }

    public String getExitSessionId() {
        return exitSessionId;
    }

    public void setExitSessionId(String exitSessionId) {
        this.exitSessionId = exitSessionId;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(Long tranAmount) {
        this.tranAmount = tranAmount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public Integer getParkingTypeId() {
        return parkingTypeId;
    }

    public void setParkingTypeId(Integer parkingTypeId) {
        this.parkingTypeId = parkingTypeId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getModifiedDetails() {
        return modifiedDetails;
    }

    public void setModifiedDetails(String modifiedDetails) {
        this.modifiedDetails = modifiedDetails;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "ParkingTransaction{" +
                "id=" + id +
                ", carPlateNo='" + carPlateNo + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", carParkId=" + carParkId +
                ", entryDateTime=" + entryDateTime +
                ", exitDateTime=" + exitDateTime +
                ", allowOpen=" + allowOpen +
                ", entry=" + entry +
                ", entrySessionId='" + entrySessionId + '\'' +
                ", exitSessionId='" + exitSessionId + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", tranAmount=" + tranAmount +
                ", duration=" + duration +
                ", driverCode='" + driverCode + '\'' +
                ", parkingTypeId=" + parkingTypeId +
                ", transactionId=" + transactionId +
                ", notificationToken='" + notificationToken + '\'' +
                ", status=" + status +
                ", remarks='" + remarks + '\'' +
                ", modifiedDetails='" + modifiedDetails + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
