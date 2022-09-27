package com.poseidon.webapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Data
@NoArgsConstructor
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer id;
    @NotNull
    @Column(name = "curve_id")
    Integer curveId;

    @Column(name = "as_of_date")
    Timestamp asOfDate;
    @NotNull
    Double term;
    @NotNull
    Double value;
    @Column(name = "creation_date")
    Timestamp creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
