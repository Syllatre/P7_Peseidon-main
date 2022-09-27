package com.poseidon.webapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Data
@NoArgsConstructor
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
@NotBlank
    String name;
    @NotBlank
    String description;
    @NotBlank
    String json;
    @NotBlank
    String template;
    @NotBlank
@Column(name = "sql_str")
    String sqlStr;
    @NotBlank
    @Column(name = "sql_part")
    String sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
