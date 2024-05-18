package io.ssafy.soupapi.domain.readme.api;


import io.ssafy.soupapi.domain.readme.application.ReadmeService;
import io.ssafy.soupapi.domain.readme.entity.BasicTemplate;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/readme")
@RequiredArgsConstructor
@Tag(name = "ReadMe", description = "ReadMe API")
public class ReadmeController {

    private final ReadmeService readmeService;

    @Operation(summary = "ReadMe 초기 템플릿 설정", description = "ReadMe 초기 템플릿을 제공합니다.")
    @GetMapping("/init")
    public ResponseEntity<BaseResponse<BasicTemplate>> getTemplate(
            @RequestParam(required = false, defaultValue = "Init Template V2") String templateName
    ) {
        return BaseResponse.success(
                SuccessCode.CHECK_SUCCESS,
                readmeService.getTemplate(templateName)
        );
    }

    @Operation(summary = "Readme 프로젝트 별 설정", description = "프로젝트 정보를 기반으로 리드미를 생성")
    @GetMapping("/init")
    public ResponseEntity<BaseResponse<BasicTemplate>> makeDefaultProjectReadme(
            @RequestParam(required = false, defaultValue = "Param Template V2") String templateName,
            @RequestParam String projectId
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                readmeService.makeDefaultProjectReadme(projectId, templateName)
        );
    }
}
