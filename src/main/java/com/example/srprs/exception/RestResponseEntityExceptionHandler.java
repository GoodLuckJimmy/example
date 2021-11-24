package com.example.srprs.exception;

import com.example.srprs.exception.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<HttpStatus> commonException(Exception exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequestRejectedException.class)
    public final ResponseEntity<HttpStatus> requestRejectedException(RequestRejectedException exception) {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiExceptionResponse> missingParameter(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getParameterName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiExceptionResponse> missMatchParameter(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        ApiExceptionResponse response = new ApiExceptionResponse(exception.getName() + "type mismatch: " + exception.getName());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionResponse> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        ApiExceptionResponse response = new ApiExceptionResponse("not readable params");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiExceptionResponse> validationError(BindException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse("error field: " + exception.getFieldError().getField()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ApiExceptionResponse> typeError(UnexpectedTypeException exception) {
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse("check request parameters"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotPaidException.class)
    public ResponseEntity<ApiExceptionResponse> typeError(NotPaidException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> constraintViolationException(ConstraintViolationException exception) {
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedAccommodationException.class)
    public ResponseEntity<ApiExceptionResponse> unsupportedAccommodationException(UnsupportedAccommodationException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpStatus> badCredentialsException() {

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AlgorithmException.class)
    public ResponseEntity<HttpStatus> algorithmException(AlgorithmException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ApiExceptionResponse> noContentException(NoContentException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NoBookingFoundException.class)
    public ResponseEntity<ApiExceptionResponse> noBookingFoundException(NoBookingFoundException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<HttpStatus> loginException(LoginException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiExceptionResponse> duplicateUserException(DuplicateUserException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse("중복된 유저는 등록 불가"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCancelRequest.class)
    public ResponseEntity<ApiExceptionResponse> wrongCancelRequest(WrongCancelRequest exception) {
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse("잘못된 취소 수수료"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InicisException.class)
    public ResponseEntity<ApiExceptionResponse> inicisSignatureException(InicisException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(new ApiExceptionResponse("결제 오류"), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(InicisCertificationFail.class)
    public ResponseEntity<ApiExceptionResponse> inicisCertificationFail(InicisCertificationFail exception) {
        log.error(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NoPaymentInfoException.class)
    public ResponseEntity<ApiExceptionResponse> noPaymentInfoException(NoPaymentInfoException exception) {
        log.warn(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BookingCancelException.class)
    public ResponseEntity<ApiExceptionResponse> bookingCancelException(BookingCancelException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotCorrectCustomerOrderException.class)
    public ResponseEntity<ApiExceptionResponse> notCorrectCustomerOrderException(NotCorrectCustomerOrderException exception) {
        log.debug(exception.getMessage());

        return new ResponseEntity<>(new ApiExceptionResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
    }


}
