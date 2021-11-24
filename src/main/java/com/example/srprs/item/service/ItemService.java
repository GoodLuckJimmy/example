package com.example.srprs.item.service;

import com.example.srprs.item.domain.*;
import com.example.srprs.item.dto.CalendarItemPrice;
import com.example.srprs.item.dto.ItemCalendarOneDaySearchRequest;
import com.example.srprs.item.dto.ItemCalendarSearchRequest;
import com.example.srprs.item.dto.ItemSearchParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    public ItemPrice theDayItemPrice(ItemSearchParam param) {
        ItemPrice price = new AccommodationPrice();
        price = new AccommodationTypePriceRate(price, param.getAccommodationType());
        price = new NumOfPeopleRate(price, param.getNumOfPeople());
        price = new TransportationPriceRate(price, param.getNumOfPeople(), param.getTransportationType());
        price = new BusyDayRate(price, param.getDay());
        price = new RandomRate(price, param.getDay());

        return price;

    }

    public List<CalendarItemPrice> getItemPriceByMonth(ItemCalendarSearchRequest request) {
        List<CalendarItemPrice> itemPrices = new ArrayList<>();

        LocalDate startDate = getStartDate(request);
        LocalDate endDate =getEndDate(request);

        for (LocalDate day = startDate; day.isBefore(endDate); day = day.plusDays(1) ) {
            ItemCalendarOneDaySearchRequest oneDaySearchRequest = ItemCalendarOneDaySearchRequest
                    .builder()
                    .day(day)
                    .numOfPeople(request.getNumOfPeople())
                    .numOfNight(request.getNumOfNight())
                    .accommodationType(request.getAccommodationType())
                    .transportationType(request.getTransportationType())
                    .build();

            itemPrices.add(new CalendarItemPrice(day, calculatePrice(oneDaySearchRequest)));
        }

        return itemPrices;
    }

    private LocalDate getEndDate(ItemCalendarSearchRequest request) {
        int year = LocalDate.now().getYear();
        int month = request.getMonth() + 2;

        if (month > 12) {
            year++;
            month = request.getMonth() % 10;
        }

        return LocalDate.of(year, month, 1);
    }

    private LocalDate getStartDate(ItemCalendarSearchRequest request) {
        int year = LocalDate.now().getYear();
        int month = request.getMonth() - 1;

        if (month <=0) {
            year--;
            month = 12;
        }

        return LocalDate.of(year, month, 1);
    }

    public List<CalendarItemPrice> getItemPriceByDay(ItemCalendarOneDaySearchRequest request) {

        return List.of(new CalendarItemPrice(request.getDay(), calculatePrice(request)));
    }



    private BigDecimal calculatePrice(ItemCalendarOneDaySearchRequest request) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(int i = 0; i < request.getNumOfNight(); i++) {
            LocalDate date = request.getDay().plusDays(i);
            ItemSearchParam param = new ItemSearchParam(
                    request.getAccommodationType(),
                    request.getNumOfPeople(),
                    request.getTransportationType(),
                    date);

            totalPrice = totalPrice.add(theDayItemPrice(param).cost());
        }

        return totalPrice;
    }

}
