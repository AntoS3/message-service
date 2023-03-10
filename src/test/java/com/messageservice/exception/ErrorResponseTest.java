package com.messageservice.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseTest {

    @Test
    public void testBuildingErrorResponse() {

        var actualResponse = new ErrorResponse();

        actualResponse.setError("");
        actualResponse.setStatus(1);
        actualResponse.setMessage("");
        actualResponse.setTime(LocalDateTime.parse("2023-03-08T17:40:31.939954800"));

        var response = new ErrorResponse("",
                1,
                "",
                LocalDateTime.parse("2023-03-08T17:40:31.939954800"));

        assertEquals(response.getError(), actualResponse.getError());
        assertEquals(response.getStatus(), actualResponse.getStatus());
        assertEquals(response.getMessage(), actualResponse.getMessage());
        assertEquals(response.getTime(), actualResponse.getTime());

    }

}