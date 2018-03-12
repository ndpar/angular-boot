package com.ndpar.petstore.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.annotations.ApiModelProperty

@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class Pet {

    @ApiModelProperty(readOnly = true)
    @JsonProperty("id")
    Long id

    @ApiModelProperty
    @JsonProperty("name")
    String name

    Map<String, ?> toMap() {
        [id: id, name: name]
    }
}
