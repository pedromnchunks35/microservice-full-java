package rest.dto;

public class GeneralResponse<T> {
    String message;
    T response;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return this.response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    private GeneralResponse(GeneralResponseBuilder<T> builder) {
        this.message = builder.message;
        this.response = builder.response;
    }

    public static class GeneralResponseBuilder<T> {
        String message;
        T response;

        public GeneralResponseBuilder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public GeneralResponseBuilder<T> setResponse(T response) {
            this.response = response;
            return this;
        }

        public GeneralResponse<T> build() {
            return new GeneralResponse<>(this);
        }
    }
}
