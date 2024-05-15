package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.HttpMethodType;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.util.StringParserUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
public record APIListDetail(
        String id,
        @NotBlank(message = "domain은 필수입니다.")
        String domain,
        String name,
        @NotBlank(message = "method_name은 필수입니다.")
        @JsonProperty("method_name")
        String methodName,
        @NotNull(message = "method_name은 필수입니다.")
        @JsonProperty("http_method")
        String httpMethod,
        @NotBlank(message = "uri는 필수입니다.")
        String uri,
        String desc,
        @JsonProperty("path_variable")
        List<PathVariable> pathVariable,
        @JsonProperty("query_param")
        List<QueryParam> queryParam,
        @JsonProperty("request_body")
        Body requestBody,
        @JsonProperty("response_body")
        Body responseBody
) {
    public static ApiDoc toApiDoc(APIListDetail apiListDetail) {
        if (isValid(apiListDetail)) {
            return ApiDoc.builder()
                    .id(UUID.fromString(apiListDetail.id()))
                    .domain(apiListDetail.domain())
                    .name(apiListDetail.name())
                    .methodName(apiListDetail.methodName())
                    .description(apiListDetail.desc())
                    .httpMethodType(apiListDetail.httpMethod() != null ?
                            HttpMethodType.valueOf(apiListDetail.httpMethod()) :
                            null)
                    .apiUriPath(apiListDetail.uri())
                    .requestBody(apiListDetail.requestBody() != null
                            ? apiListDetail.requestBody().data()
                            : null)
                    .responseBody(apiListDetail.responseBody() != null
                            ? apiListDetail.responseBody().data()
                            : null)
                    .pathVariables(apiListDetail.pathVariable() != null
                            ? apiListDetail.pathVariable().stream().map(PathVariable::toApiVariable).toList()
                            : List.of())
                    .queryParameters(apiListDetail.queryParam() != null
                            ? apiListDetail.queryParam().stream().map(QueryParam::toApiVariable).toList()
                            : List.of())
                    .build();
        }

        return null;
    }


    private static boolean isValid(APIListDetail apiListDetail) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<APIListDetail>> violations = validator.validate(apiListDetail);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<APIListDetail> violation : violations) {
                sb.append(violation.getMessage()).append(", ");
            }
            throw new BaseExceptionHandler(ErrorCode.UNABLE_TO_USE_THIS_API_DOC, "유효성 검사 실패: " + sb);
        }

        List<String> needKeys = StringParserUtil.extractBracketsContent(apiListDetail.uri());
        Set<String> pathKey = apiListDetail.pathVariable().stream().map(p -> p.name().toUpperCase()).collect(Collectors.toSet());
        for (String needKey : needKeys) {
            if (!pathKey.contains(needKey.toUpperCase())) {
                throw new BaseExceptionHandler(ErrorCode.UNABLE_TO_USE_THIS_API_DOC, "유효성 검사 실패: PathVariable 정보 부족 " + needKey);
            }
        }

        return true;
    }

}
