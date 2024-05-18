package io.ssafy.soupapi.domain.noti.api;

import io.ssafy.soupapi.domain.noti.application.EmitterNotiService;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dto.response.GetNotiRes;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notis")
@Tag(name = "알림", description = "알림")
public class NotiController {

    private final EmitterNotiService emitterNotiService;
    private final NotiService notiService;

    // 유저가 /sub으로 구독하면, 백엔드에서 /sub/{memberId}로 구독된 것으로 처리
    // Last-Event-ID : 전에 못 받은 이벤트가 존재할 경우(SSE 연결에 대한 시간 만료 혹은 종료) 마지막 이벤트 ID를 넘겨 그 이후의 데이터부터 받을 수 있게 하기 위해 필요
    @GetMapping(value = "/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
        @AuthenticationPrincipal UserSecurityDTO userSecurityDTO,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId // SSE 연결이 시간 만료 등의 이유로 끊어졌는데 알림이 발생하면? 이를 방지하기 위해, 클라이언트가 마지막으로 수신한 데이터의 ID값을 받는다. 이를 이용해 유실된 데이터를 다시 보내줄 수 있다.
    ) {
        String memberId = String.valueOf(userSecurityDTO.getId());
        return notiService.subscribe(memberId, lastEventId);
    }

    @Operation(summary = "수신한 알림 조회", description = "유저가 수신한 모든 알림을 조회한다.\n\n" +
        "Query Parameter인 read의 값을 true 또는 false로 줌에 따라 필터링이 가능하다. (<- 아직 API 미완성)\n\n" +
        "read 없이 요청 시, 읽은 거나 안 읽은 거나, 모든 알림을 조회한다.")
    @GetMapping(value="")
    public ResponseEntity<BaseResponse<GetNotiRes>> getNotis(
            @RequestParam(value = "read", required = false) Boolean read,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                notiService.getNotis(String.valueOf(userSecurityDTO.getId()), read)
        );
    }

    @Operation(summary = "알림 읽음 처리")
    @PostMapping(value="")
    public ResponseEntity<BaseResponse<Boolean>> readNoti(
            @RequestParam(value = "notiId") ObjectId notiId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                notiService.readNoti(userSecurityDTO.getId().toString(), notiId)
        );
    }

}
