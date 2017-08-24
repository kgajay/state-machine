package com.kgajay.demo.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * @author ajay.kg created on 24/08/17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transition {

    private Long id;

    private String description;

    private String path;

    @NotNull
    private Long sourceNodeId;

    @NotNull
    private Long destinationNodeId;

    @JsonIgnore
    private Boolean deleted = Boolean.FALSE;

    private DateTime createdAt;
    private DateTime updatedAt;
}