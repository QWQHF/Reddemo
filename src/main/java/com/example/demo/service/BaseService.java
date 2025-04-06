package com.example.demo.service;
import com.example.demo.dto.request.GetBasesParams;
import com.example.demo.dto.response.BaseListResponse;
import com.example.demo.dto.response.BaseDetail;
import org.springframework.stereotype.Service;

@Service
public interface BaseService {
    BaseListResponse getBases(GetBasesParams params);
    BaseDetail getBaseDetail(Long id);
}