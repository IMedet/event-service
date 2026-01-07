package kz.qonaqzhai.eventservice.handler;

import kz.qonaqzhai.eventservice.exception.EventNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void handleEventNotFoundException_returns404AndMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        EventNotFoundException ex = new EventNotFoundException("not found");

        ResponseEntity<String> response = handler.handleEventNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("not found", response.getBody());
    }
}
