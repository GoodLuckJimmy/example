package com.example.srprs.item.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.srprs.booking.api.BookingApi;
import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import com.example.srprs.common.MockMvcSetting;
import com.example.srprs.item.dto.CalendarItemPrice;
import com.example.srprs.item.dto.ItemCalendarOneDaySearchRequest;
import com.example.srprs.item.dto.ItemCalendarSearchRequest;
import com.example.srprs.item.service.ItemService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebMvcTest(value = ItemApi.class)
class ItemApiTest extends MockMvcSetting {

    @MockBean
    private ItemService itemService;


    @Test
    void items() throws Exception {
        // given
        when(itemService.getItemPriceByMonth(any(ItemCalendarSearchRequest.class)))
                .thenReturn(List.of(new CalendarItemPrice(LocalDate.of(2021, 9, 29), BigDecimal.TEN)));

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("month", "1");
        request.add("numOfPeople", "1");
        request.add("numOfNight", "1");
        request.add("accommodationType", AccommodationType.GUEST_HOUSE.name());
        request.add("transportationType", TransportationType.MY_CAR.name());

        // then
        this.mvc.perform(get("/items/calendars").params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemPrices[0].date").value("2021-09-29"));
    }

    @Test
    void oneDayItems() throws Exception {
        // given
        when(itemService.getItemPriceByDay(any(ItemCalendarOneDaySearchRequest.class)))
                .thenReturn(List.of(new CalendarItemPrice(LocalDate.of(2021, 9, 29), BigDecimal.TEN)));

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("day", "2021-09-23");
        request.add("numOfPeople", "1");
        request.add("numOfNight", "1");
        request.add("accommodationType", AccommodationType.GUEST_HOUSE.name());
        request.add("transportationType", TransportationType.MY_CAR.name());

        // then
        this.mvc.perform(get("/items/calendars/days").params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemPrices[0].date").value("2021-09-29"));
    }
}