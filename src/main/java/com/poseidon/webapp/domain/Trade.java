package com.poseidon.webapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    Integer tradeId;
    String account;
    String type;
    @Column(name = "buy_quantity")
    Double buyQuantity;
    @Column(name = "sell_quantity")
    Double sellQuantity;
    @Column(name = "buy_price")
    Double buyPrice;
    @Column(name = "sell_price")
    Double sellPrice;
    String benchmark;
    @Column(name = "trade_date")
    Timestamp tradeDate;
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

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
