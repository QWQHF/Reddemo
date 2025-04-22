package com.example.demo.service.impl;


import com.example.demo.dto.request.GetBasesParams;
import com.example.demo.dto.response.BaseDetail;
import com.example.demo.dto.response.BaseItem;
import com.example.demo.dto.response.BaseListResponse;
import com.example.demo.dto.response.Coordinate;
import com.example.demo.mapper.BaseMapper;
import com.example.demo.entity.StudyBase;
import com.example.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public BaseListResponse getBases(GetBasesParams params) {
        // 计算偏移量
        int offset = (params.getPage() - 1) * params.getPageSize();

        // 查询列表
        List<StudyBase> bases = baseMapper.selectBases(
                params.getKeyword(),
                params.getThemes(),
                params.getRegion(),
                params.getSort(),
                offset,
                params.getPageSize()
        );

        // 查询总数
        int total = baseMapper.countBases(
                params.getKeyword(),
                params.getThemes(),
                params.getRegion()
        );

        // 转换为DTO
        List<BaseItem> list = bases.stream()
                .map(this::convertToBaseItem)
                .collect(Collectors.toList());

        // 构建响应
        BaseListResponse response = new BaseListResponse();
        response.setTotal((long) total);
        response.setList(list);
        return response;
    }

    @Override
    public BaseDetail getBaseDetail(Long id) {
        StudyBase base = (StudyBase) baseMapper.selectBaseById(id);
        if (base == null) {
            throw new RuntimeException("研学基地不存在");
        }

        // 加载关联数据
        base.setTags(baseMapper.selectTagsByBaseId(id));
        base.setFacilities(baseMapper.selectFacilitiesByBaseId(id));
        base.setImages(baseMapper.selectImagesByBaseId(id));

        return convertToBaseDetail(base);
    }

    private BaseItem convertToBaseItem(StudyBase base) {
        BaseItem item = new BaseItem();
        item.setId(base.getId());
        item.setName(base.getName());
        item.setImage(base.getImage());
        item.setRating(base.getRating());
        item.setLocation(base.getLocation());
        item.setTags(base.getTags());
        item.setDescription(base.getDescription());
        item.setMaxCapacity(base.getMaxCapacity());
        item.setAvailable(base.getAvailable());
        item.setOpenTime(base.getOpenTime());
        item.setCurrentBookings(base.getCurrentBookings());
        return item;
    }

    private BaseDetail convertToBaseDetail(StudyBase base) {
        BaseDetail detail = new BaseDetail();
        // 继承自BaseItem的属性
        detail.setId(base.getId());
        detail.setName(base.getName());
        detail.setImage(base.getImage());
        detail.setRating(base.getRating());
        detail.setLocation(base.getLocation());
        detail.setTags(base.getTags());
        detail.setDescription(base.getDescription());
        detail.setMaxCapacity(base.getMaxCapacity());
        detail.setAvailable(base.getAvailable());
        detail.setOpenTime(base.getOpenTime());
        detail.setCurrentBookings(base.getCurrentBookings());

        // BaseDetail特有属性
        detail.setContact(base.getContact());
        detail.setFacilities(base.getFacilities());
        detail.setNotice(base.getNotice());
        detail.setImages(base.getImages());

        Coordinate coordinate = new Coordinate();
        coordinate.setLat(base.getLatitude());
        coordinate.setLng(base.getLongitude());
        detail.setCoordinate(coordinate);

        return detail;
    }
}
