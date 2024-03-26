package DroneMed.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

/**
 * Utility class to build standardized API responses.
 * This class ensures that responses from the API have a consistent format.
 */
public class ResponseHandler {

    // Prevent instantiation
    private ResponseHandler() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Builds a ResponseEntity with a consistent response format.
     *
     * @param <T> The type of the response object.
     * @param message A descriptive message related to the response.
     * @param httpStatus The HTTP status for the response.
     * @param responseObject The data/content of the response.
     * @return ResponseEntity containing the structured response.
     */
    public static <T> ResponseEntity<Map<String, Object>> responseBuilder(
            String message, HttpStatus httpStatus, T responseObject) {

        Map<String, Object> response = Map.of(
                "message", message,
                "httpStatus", httpStatus,
                "data", responseObject
        );

        return new ResponseEntity<>(response, httpStatus);
    }

    public static <T> ResponseEntity<Map<String, Object>> responseBuilder(
            String message, HttpStatus httpStatus) {

        Map<String, Object> response = Map.of(
                "message", message,
                "httpStatus", httpStatus
        );

        return new ResponseEntity<>(response, httpStatus);
    }

}
