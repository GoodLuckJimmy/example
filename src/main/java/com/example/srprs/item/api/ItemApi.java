package com.example.srprs.item.api;

import com.example.srprs.item.dto.CalendarItemPrice;
import com.example.srprs.item.dto.ItemCalendarOneDaySearchRequest;
import com.example.srprs.item.dto.ItemCalendarSearchRequest;
import com.example.srprs.item.dto.StaticItemSearchResponse;
import com.example.srprs.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="여행 상품", description = "여행 상품 api")
public class ItemApi {
    private final ItemService itemService;

    @Operation(summary = "달력용 여행상품 가져오기", description = "달력에 보여주기 위한 상품 정보 리스트를 가져온다.")
    @GetMapping("/items/calendars")
    public StaticItemSearchResponse items(@Valid @ModelAttribute ItemCalendarSearchRequest request) {

        List<CalendarItemPrice> itemPrices = itemService.getItemPriceByMonth(request);

        return new StaticItemSearchResponse(itemPrices);
    }

    @Operation(summary = "특정날짜 여행상품 가져오기", description = "특정 날짜에 해당하는 상품 가격을 가져온다.")
    @GetMapping("/items/calendars/days")
    public StaticItemSearchResponse oneDayItems(@Valid @ModelAttribute ItemCalendarOneDaySearchRequest request) {

        List<CalendarItemPrice> itemPrices = itemService.getItemPriceByDay(request);

        return new StaticItemSearchResponse(itemPrices);
    }

}
