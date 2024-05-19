package io.ssafy.soupapi.global.exception;

import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.OpenViduHttpException;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.response.ErrorResponse;
import io.ssafy.soupapi.global.security.exception.AccessTokenException;
import io.ssafy.soupapi.global.security.exception.RefreshTokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@Log4j2
@RestControllerAdvice
public class GlobalControllerAdvice {
    // Custom ErrorCode를 기반으로 에러 처리
    @ExceptionHandler(BaseExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleCustomBaseExceptionHandler(BaseExceptionHandler e) {
        var response = ErrorResponse.fail(e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(response.status()).body(response);
    }

    /**
     * accessToken 관련 에러 발생 시 처리
     */
    @ExceptionHandler(AccessTokenException.class)
    public ResponseEntity<ErrorResponse> handleAccessTokenExceptionHandler(AccessTokenException e) {
        var response = ErrorResponse.fail(e);
        return ResponseEntity.status(response.status()).body(response);
    }

    /**
     * refreshToken 관련 에러 발생 시 처리
     */
    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenExceptionHandler(RefreshTokenException e) {
        var response = ErrorResponse.fail(e);
        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedExceptionHandler(AccessDeniedException e) {
        log.info("[ControllerAdvice] Access Denied Exception 처리");
        var response = ErrorResponse.fail(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", e.getMessage());
        return ResponseEntity.status(response.status()).body(response);
    }

    /**
     * SSE 연결 타임 아웃 시
     */
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleAsyncTimeoutExceptions(AsyncRequestTimeoutException e) {
        log.info("[ControllerAdvice] SSE 연결 Async Timeout exception 발생. 재연결을 요청 받아야 합니다.");
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(null);
    }

    /**
     * OpenVidu 에러
     */
    @ExceptionHandler(OpenViduException.class)
    public ResponseEntity<ErrorResponse> handleOpenViduExceptions(OpenViduException e) {
        log.info("[ControllerAdvice] OpenVidu 관련 에러 발생: {}", e.getClass());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidation(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        var response = ErrorResponse.fail(ErrorCode.NOT_VALID_ERROR,
                Objects.requireNonNull(bindingResult).getFieldErrors(),
                bindingResult.getAllErrors().stream().findFirst().orElseThrow().getDefaultMessage()
        );

        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidation(HandlerMethodValidationException e) {
        var response = ErrorResponse.fail(ErrorCode.NOT_VALID_ERROR, e.getMessage());

        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        var response = ErrorResponse.fail(ErrorCode.NO_RESOUCE_FOUNDED, e.getMessage());
        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeExceptions(RuntimeException e) {
        log.info("[ControllerAdvice] Runtime Exception 처리");
        e.printStackTrace();
        var response = ErrorResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(response.status()).headers(headers).body(response);
    }

    /**
     * 예외 처리 되지 않은 모든 에러 처리
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.info("[ControllerAdvice] 모든 Exception 처리");
        e.printStackTrace();
        var response = ErrorResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(response.status()).headers(headers).body(response);
    }
}
