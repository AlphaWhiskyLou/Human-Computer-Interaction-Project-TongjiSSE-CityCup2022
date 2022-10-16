package com.banking.projectserver.service;

import com.banking.projectserver.entity.SystemFunction;

import java.util.List;

public interface SystemFunctionService {
    SystemFunction getSystemFunctionById(int functionID);
    List<SystemFunction> getSystemFunction();
    boolean addSystemFunction(int functionID, String functionName, String functionDescription);
    boolean deleteSystemFunctionById(int functionID);
}
