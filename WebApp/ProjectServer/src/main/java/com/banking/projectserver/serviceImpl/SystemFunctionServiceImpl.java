package com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.SystemFunction;
import com.banking.projectserver.mapper.SystemFunctionMapper;
import com.banking.projectserver.service.SystemFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 17:00:09
 */
@Service
public class SystemFunctionServiceImpl implements SystemFunctionService {

    @Autowired
    private SystemFunctionMapper systemFunctionMapper;

    @Override
    public SystemFunction getSystemFunctionById(int functionID) {
        return systemFunctionMapper.getSystemFunctionById(functionID);
    }

    @Override
    public List<SystemFunction> getSystemFunction(){
        return systemFunctionMapper.getSystemFunction();
    }

    @Override
    public boolean addSystemFunction(int functionID, String functionName, String functionDescription){
        return systemFunctionMapper.addSystemFunction(functionID, functionName, functionDescription);
    }

    @Override
    public boolean deleteSystemFunctionById(int functionID){
        return systemFunctionMapper.deleteSystemFunctionById(functionID);
    }


}