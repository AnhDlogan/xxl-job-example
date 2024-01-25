package executor.controller;

public class ApiOutput {
    private Integer status;
    private String message;
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Integer status;
        private String message;
        private Object data;

        public Builder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withData(Object data) {
            this.data = data;
            return this;
        }

        public ApiOutput build() {
            ApiOutput apiOutput = new ApiOutput();
            apiOutput.setStatus(this.status);
            apiOutput.setMessage(this.message);
            apiOutput.setData(this.data);
            return apiOutput;
        }
    }
}

