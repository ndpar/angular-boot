package com.ndpar.petstore.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class Pet {
    Long id
    String name

    Map<String, ?> toMap() {
        [id: id, name: name]
    }
}
