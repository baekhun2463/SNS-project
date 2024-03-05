package snsproject.snsproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultsCode;
    private T result;

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }
}
