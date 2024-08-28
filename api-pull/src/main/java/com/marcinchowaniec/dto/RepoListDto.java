package com.marcinchowaniec.dto;

import java.util.List;

import com.marcinchowaniec.entity.Repo;

public record RepoListDto(String msg, List<Repo> repos) {

}
