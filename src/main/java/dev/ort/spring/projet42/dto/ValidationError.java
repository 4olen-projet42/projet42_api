package dev.ort.spring.projet42.dto;

import java.util.Map;

public class ValidationError {

    private String errorMessage;

    private Map<String, String> fieldsErrors;


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getFieldsErrors() {
        return fieldsErrors;
    }

    public void setFieldsErrors(Map<String, String> fieldsErrors) {
        this.fieldsErrors = fieldsErrors;
    }
}

