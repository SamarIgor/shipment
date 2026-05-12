package org.app.shipment.api_response;
import java.time.LocalDateTime;

public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private String path;
    private String message;
    private T data;

    public ApiResponse(int status, String message, String path, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.path = path;
        this.data = data;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
