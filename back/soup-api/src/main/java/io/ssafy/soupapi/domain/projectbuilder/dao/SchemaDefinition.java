package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemaDefinition {
    @Builder.Default
    private Map<String, TableDefinition> tables = new HashMap<>();
    @Builder.Default
    private Map<String, TableRelationDefinition> relations = new HashMap<>();
}
