package com.marcinchowaniec.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcinchowaniec.entity.Branch;

public class BranchMapper {

    public static Branch branchToEnityMapper(JsonNode node, String repo, String username) {
        Branch branch = new Branch();
        branch.name = node.get("name").asText();
        branch.sha = node.get("commit").get("sha").asText();
        branch.url = node.get("commit").get("url").asText();
        branch.repoName = repo;
        branch.username = username;
        return branch;
    }

}
