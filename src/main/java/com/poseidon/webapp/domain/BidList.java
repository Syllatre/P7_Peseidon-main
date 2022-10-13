package com.poseidon.webapp.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@Data
@NoArgsConstructor
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    Integer BidListId;
    @NotBlank(message = "Account is mandatory")
    String account;
    @NotBlank(message = "Account is mandatory")
    String type;
    @Column(name = "bid_quantity")
    @NotNull
    Double bidQuantity;
    @Column(name = "ask_quantity")
    Double askQuantity;
    Double bid;
    Double ask;
    String benchmark;
    @Column(name = "bid_list_date")
    Timestamp bidListDate;
    String commentary;
    String security;
    String status;
    String trader;
    String book;
    @Column(name = "creation_name")
    String creationName;
    @Column(name = "creation_date")
    Timestamp creationDate;
    @Column(name = "revision_name")
    String revisionName;
    @Column(name = "revision_date")
    Timestamp revisionDate;
    @Column(name = "deal_name")
    String dealName;
    @Column(name = "deal_type")
    String dealType;
    @Column(name = "source_list_id")
    String sourceListId;
    String side;

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
