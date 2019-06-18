package edu.nju.wsql.service.results;

import edu.nju.wsql.model.enums.UserType;

public class ValidationResult {
    private Result result;
    private String id;
    private UserType type;

    public ValidationResult(Result result) {
        this.result = result;
    }

    public ValidationResult(Result success, String id, UserType type) {
        this(success);
        this.id = id;
        this.type = type;
    }

    public Result getResult() {
        return result;
    }

    public String getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }

    public enum Result {
        SUCCESS, EXPIRED, ILLEGAL
    }
}
