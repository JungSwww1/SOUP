package io.ssafy.soupapi.domain.project.mongodb.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "projects")
public class Project {
    @Id
    @Builder.Default
    @Field("project_id")
    private ObjectId id = null;
    @Field("project_info")
    private Info info;
    @Field("project_tools")
    private List<Tool> tools;
    @Builder.Default
    @Field("project_team_members")
    private List<TeamMember> teamMembers = new ArrayList<>();
    private Proposal proposal;
}
