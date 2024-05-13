package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Schema(description = "프로젝트 정보 업데이트")
public record UpdateProjectInfo(
        @Schema(description = "프로젝트 이름")
        String name,
        @Schema(description = "프로젝트 설명")
        String description,
        @Pattern(regexp = "^\\d{8}$", message = "날짜 형식이 잘못되었습니다. (yyyyMMdd)")
        @Schema(description = "프로젝트 시작일")
        String startDate,
        @Pattern(regexp = "^\\d{8}$", message = "날짜 형식이 잘못되었습니다. (yyyyMMdd)")
        @Schema(description = "프로젝트 종료일")
        String endDate,
        @Schema(description = "프로젝트 사용툴")
        List<UpdateProjectTool> tools
) {
    public UpdateProjectInfo {
        if (Objects.isNull(tools)) {
            tools = List.of();
        }
    }

    public LocalDate getStartDate() {
        if (Objects.isNull(startDate)) {
            return LocalDate.now();
        }
        return LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE);
    }

    public LocalDate getEndDate() {
        if (Objects.isNull(endDate)) {
            return LocalDate.of(2049, 12, 31);
        }
        return LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
