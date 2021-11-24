package com.example.srprs.payment.api;

import com.example.srprs.booking.service.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="결제", description = "결제 관련 api")
public class PaymentApi {
    private final BookingService bookingService;

//    @Operation(summary = "결제 성공시", description = "결제 성공시 결제 고유번호를 저장한다. Authorization 헤더 필요")
//    @PostMapping("/payments")
//    @ResponseStatus(HttpStatus.OK)
//    @Deprecated
//    public void paid(@Valid @RequestBody BookingPaymentRequest request,
//                     @Parameter(hidden = true) @AuthenticationPrincipal MemberDTO memberDTO) {
//        bookingService.paid(request);
//    }

}
