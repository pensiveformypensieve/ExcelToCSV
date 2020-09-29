package com.example.demo.domain;

import com.sun.istack.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "parking_transaction")
public class ParkingTransaction {

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

    @Column(name = "last_modified_date", nullable = false)
    private ZonedDateTime lastModifiedDate;

    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

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

    @Override
    public String toString() {
        return "ParkingTransaction [id=" + id + ", carPlateNo=" + carPlateNo + ", carParkId=" + carParkId
                + ", vehicleType=" + vehicleType
                + ", entryDateTime=" + entryDateTime + ", exitDateTime=" + exitDateTime + ", allowOpen=" + allowOpen
                + ", entry=" + entry + ", entrySessionId=" + entrySessionId + ", exitSessionId=" + exitSessionId
                + ", paymentAmount=" + paymentAmount + ", tranAmount=" + tranAmount + ", duration=" + duration
                + ", driverCode=" + driverCode + ", lastModifiedDate=" + lastModifiedDate + ", createdDate=" + createdDate
                + ", transaction_id=" + transactionId + ", parking_type_id=" + parkingTypeId
                + ", notificationToken=" + notificationToken + ", status=" + status + ", remarks=" + remarks + ", modifiedDetails=" + modifiedDetails
                + "]";
    }


}
