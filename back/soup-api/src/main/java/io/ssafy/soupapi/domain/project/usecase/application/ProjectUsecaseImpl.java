package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import io.ssafy.soupapi.external.claude.ClaudeFeignClient;
import io.ssafy.soupapi.external.claude.dto.CreateClaudeMessageReq;
import io.ssafy.soupapi.external.claude.dto.CreateClaudeMessageRes;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectUsecaseImpl implements ProjectUsecase {
    private final MProjectService mProjectService;
    private final PProjectService pProjectService;

    private final ClaudeFeignClient claudeFeignClient;

    /**
     * 프로젝트 생성
     * 1. MongoDB 프로젝트 생성
     * 2. PostgreSQL 프로젝트 등록 및 접속 권한 부여
     *
     * @param userSecurityDTO project maker
     * @return mongodb project objectId
     */
    @Transactional
    @Override
    public String createProject(UserSecurityDTO userSecurityDTO) {
        var projectId = mProjectService.createProject(userSecurityDTO);
        pProjectService.registProject(projectId.toHexString(), userSecurityDTO);
        return projectId.toHexString();
    }

    @Override
    public CreateAiProposal createAiProposal(CreateAiProposal createAiProposal) {
        CreateClaudeMessageReq createClaudeMessageReq = new CreateClaudeMessageReq(createAiProposal);
        log.info(createClaudeMessageReq);
        CreateClaudeMessageRes claudeRes = claudeFeignClient.getClaudeMessage(createClaudeMessageReq);
        log.info(claudeRes);

        return claudeRes.toResponse();
    }

}
