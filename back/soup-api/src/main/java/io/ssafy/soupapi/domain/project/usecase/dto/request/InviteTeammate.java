package io.ssafy.soupapi.domain.project.usecase.dto.request;

import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "팀 멤버 초대 DTO")
public record InviteTeammate(
        @NotEmpty(message = "초대할 프로젝트 Id를 확인해 주세요")
        @Schema(description = "팀 멤버를 초대할 프로젝트 Id")
        String projectId,
        @Email(message = "이메일을 정확히 입력해 주세요.")
        @Schema(description = "초대할 팀원의 이메일")
        String email,
        @NotEmpty(message = "초대할 팀원의 권한을 확인해 주세요")
        @Schema(description = "팀 멤버의 권한")
        List<ProjectRole> roles
) {
}
