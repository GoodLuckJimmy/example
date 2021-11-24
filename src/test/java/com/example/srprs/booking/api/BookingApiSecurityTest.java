package com.example.srprs.booking.api;

import com.example.srprs.booking.BookingDataMaker;
import com.example.srprs.booking.dto.BookingSearchRequest;
import com.example.srprs.booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingApi.class)
class BookingApiSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;


    @Test
    void 예약_검색시_jwt_토큰_없을_경우_오류() throws Exception {
        // given
        BookingSearchRequest request = BookingDataMaker.bookingSearchRequest(123L);

        // then
        this.mvc.perform(post("/booking/histories")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}