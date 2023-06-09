package com.example.agile0509.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.agile0509.pojo.Score;
import com.example.agile0509.vo.SocialVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface SocialMapper extends BaseMapper<Score> {

    List<SocialVo> getSocialByStuId(Integer stuId);

    void insertSocial(Integer stuId, String time, String content, String material);

    void modifySocial(Integer SocialId, String time, String content, String material);

    void deleteSocial(Integer SocialId);
}
