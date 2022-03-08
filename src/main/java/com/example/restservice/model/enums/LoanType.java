package com.example.restservice.model.enums;

import static java.util.Arrays.stream;

public enum LoanType {
    STUDENT("student"),
    CONSUMER("consumer");

    private String type;

    LoanType(String type){
        this.type = type;
    }

    public String value() {
        return this.type;
    }

    public static LoanType fromString(String type) {
        return stream(LoanType.values()).
            filter(loanType -> loanType.type.equalsIgnoreCase(type))
            .findFirst().get();
    }
}