package com.marcinchowaniec.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcinchowaniec.entity.Branch;
import com.marcinchowaniec.entity.Repo;

public class BranchMapper {

    public static Branch branchToEntityMapper(JsonNode node, Repo repo) {
        Branch branch = new Branch();
        branch.name = node.get("name").asText();
        branch.sha = node.get("commit").get("sha").asText();
        branch.branch_protected = node.get("protected").asBoolean();
        branch.repo = repo;
        return branch;
    }

}
