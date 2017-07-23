package com.kgajay.demo.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.utils.AsyncWorker;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author ajay.kg created on 23/07/17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankInfo {

    @JsonIgnore
    private Long id;

    @NotNull
    private String routingNumber;

    @NotEmpty
    private String name;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    @NotEmpty
    private String zipCode;

    private String address;

    @JsonIgnore
    private Boolean deleted = Boolean.FALSE;

    private DateTime createdAt;
    private DateTime updatedAt;

    public void merge(BankInfo bankInfoReq) {
        if (Objects.nonNull(bankInfoReq.getRoutingNumber())) {
            this.setRoutingNumber(bankInfoReq.getRoutingNumber());
        }
        if (Objects.nonNull(bankInfoReq.getName())) {
            this.setName(bankInfoReq.getName());
        }
        if (Objects.nonNull(bankInfoReq.getCity())) {
            this.setCity(bankInfoReq.getCity());
        }
        if (Objects.nonNull(bankInfoReq.getState())) {
            this.setState(bankInfoReq.getState());
        }
        if (Objects.nonNull(bankInfoReq.getZipCode())) {
            this.setZipCode(bankInfoReq.getZipCode());
        }
        if (Objects.nonNull(bankInfoReq.getAddress())) {
            this.setAddress(bankInfoReq.getAddress());
        }
    }

    public static void checkAndUpdate(DBDao dbDao, String routingNumber, String name, String city, String state, String zipCode, String address) {
        AsyncWorker.INSTANCE.execute(() -> {
            BankInfo bankInfo = dbDao.getBankInfoByRoutingNumber(routingNumber);
            if (Objects.isNull(bankInfo)) {
                dbDao.insertBankInfo(routingNumber, name, city, state, zipCode, address);
            } else {
                dbDao.updateBankInfo(bankInfo.getId(), routingNumber, name, city, state, zipCode, address);
            }
        });
    }
}
