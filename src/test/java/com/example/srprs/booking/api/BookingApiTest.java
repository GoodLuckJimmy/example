package com.example.srprs.booking.api;

import com.example.srprs.booking.BookingDataMaker;
import com.example.srprs.booking.dto.BookingRequest;
import com.example.srprs.booking.dto.BookingSearchRequest;
import com.example.srprs.booking.service.BookingService;
import com.example.srprs.common.MockMvcSetting;
import com.example.srprs.member.domain.WithMockMember;
import com.example.srprs.member.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BookingApi.class)
class BookingApiTest extends MockMvcSetting {
    @MockBean
    private BookingService bookingService;

    @MockBean
    private MemberDto memberDTO;


    @Test
    @WithMockMember
    void book() throws Exception {
        // given
        BookingRequest request = BookingDataMaker.bookingRequest();
        when(memberDTO.getNo()).thenReturn(1234L);

        // then
        this.mvc.perform(post("/booking")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockMember
    void search() throws Exception {
        // given
        when(bookingService.find(any(BookingSearchRequest.class))).thenReturn(BookingDataMaker.bookingSearchResponse());

        // then
        this.mvc.perform(get("/booking/histories").with(user("bpUser").roles("CUSTOMER")))
                .andExpect(status().isOk());
    }

    @Test
    void 예약_검색시_전화번호_없을시_오류() throws Exception {
        // given
        BookingSearchRequest request = BookingDataMaker.bookingSearchRequest(123L);

        // then
        this.mvc.perform(post("/booking/histories")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}