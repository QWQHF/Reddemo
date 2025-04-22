package com.example.demo.mapper;


import com.example.demo.entity.StudyBase;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BaseMapper {
    // 获取基地列表（带分页和条件）
    List<StudyBase> selectBases(
            @Param("keyword") String keyword,
            @Param("themes") List<String> themes,
            @Param("region") String region,
            @Param("sort") String sort,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    // 获取基地总数（用于分页）
    int countBases(
            @Param("keyword") String keyword,
            @Param("themes") List<String> themes,
            @Param("region") String region
    );

    // 获取单个基地详情
    @Select("""
    SELECT
        id,
        name,
        image,
        rating,
        location,
        description,
        max_capacity AS maxCapacity,  -- 明确指定别名
        available,
        open_time AS openTime,
        current_bookings AS currentBookings,
        contact,
        notice,
        latitude,
        longitude,
        region,
        created_at AS createdAt,
        updated_at AS updatedAt
    FROM study_base
    WHERE id = #{id}
""")
    StudyBase selectBaseById(Long id);

    // 获取基地标签
    @Select("SELECT tag_name FROM base_tag WHERE base_id = #{baseId}")
    List<String> selectTagsByBaseId(Long baseId);

    // 获取基地设施
    @Select("SELECT facility_name FROM base_facility WHERE base_id = #{baseId}")
    List<String> selectFacilitiesByBaseId(Long baseId);

    // 获取基地图片
    @Select("SELECT image FROM study_base WHERE id = #{baseId}")
    List<String> selectImagesByBaseId(Long baseId);
}