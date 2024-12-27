package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.model.LastLogin;

import java.util.List;

public interface LastLoginService {


    void updateLastLoginInfo(int userId);

    List<LastLogin> getAll();

    LastLogin findByUserId(int userId);
}
