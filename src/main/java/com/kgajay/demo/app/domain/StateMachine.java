package com.kgajay.demo.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * @author ajay.kg created on 24/08/17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateMachine {

    private Long id;

    @NotEmpty
    private String name;

    private String description;

    @JsonIgnore
    private Boolean deleted = Boolean.FALSE;

    private Long startNodeId;

    private DateTime createdAt;
    private DateTime updatedAt;

}
