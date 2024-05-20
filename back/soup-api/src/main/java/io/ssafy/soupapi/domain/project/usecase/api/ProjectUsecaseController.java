package io.ssafy.soupapi.domain.project.usecase.api;

import io.ssafy.soupapi.domain.project.usecase.application.ProjectUsecase;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import io.ssafy.soupapi.domain.project.usecase.dto.request.UpdateProjectImage;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트", description = "Project Domain Usecase Controller")
public class ProjectUsecaseController {
    private final ProjectUsecase projectUsecase;

    /**
     * 프로젝트 생성 Post API
     *
     * @param userSecurityDTO member who create project
     * @return mongodb project objectId
     */
    @Operation(summary = "프로젝트 생성 요청")
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> createProject(
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                projectUsecase.createProject(userSecurityDTO)
        );
    }

    @Operation(summary = "AI 기획서 생성 요청")
    @PostMapping("/{projectId}/plan/ai")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<CreateAiProposal>> createAiProposal(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO,
            @RequestBody CreateAiProposal createAiProposal
    ) {
//        CreateAiProposal response = projectUsecase.createAiProposal(createAiProposal);
//
//        return BaseResponse.success(
//                SuccessCode.SELECT_SUCCESS,
//                response
//        );

        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                CreateAiProposal.builder()
                        .background(List.of("반복되는 프로젝트 초기 설계 작업을 자동화하여 개발 생산성을 높일 수 있습니다."))
                        .intro(List.of("웹 기반 플랫폼에서 실시간으로 팀원들과 협업하며 프로젝트 코드를 동시에 편집할 수 있습니다."))
                        .target(List.of("동시 편집, 음성 채팅, ERD, API 설계등 개발에 필요한 다양한 기능을 하나의 플랫폼에서 제공합니다."))
                        .result(List.of("Spring 프레임워크를 처음 접하는 개발자들도 쉽게 프로젝트를 시작할 수 있도록 도와줍니다."))
                        .build()
        );
    }


    @Operation(summary = "프로젝트 사진 업데이트")
    @PatchMapping("/{projectId}/image")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> changeProjectImage(
            @PathVariable String projectId,
            @RequestBody UpdateProjectImage updateProjectImage,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                projectUsecase.changeProjectImage(projectId, updateProjectImage)
        );
    }
}
