package com.ciphertext.opencarebackend.respository.specification;

import java.util.List;

public record MultiJoinIn<T>(String joinColumn, String joinTable, String inColumn, List<T> joinValues) {

}
