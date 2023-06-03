package com.example.agile0509.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.agile0509.pojo.Score;
import com.example.agile0509.vo.ServePositionVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ServePositionMapper extends BaseMapper<Score>{

    List<ServePositionVo> getServePositionByStuId(String stuId);

    void insertServePosition(String stuId, String time, String content);

    void modifyServePosition(Integer ServePositionId, String time, String content);

    void deleteServePosition(Integer ServePositionId);
}