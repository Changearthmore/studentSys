package com.example.agile0509.controller;

import com.example.agile0509.common.CommonResult;
import com.example.agile0509.vo.GPAUpdateReqVo;
import com.example.agile0509.vo.StuGPAReqVo;
import com.example.agile0509.vo.StuVolunteerReqVo;
import com.example.agile0509.mapper.*;
import com.example.agile0509.pojo.Score;
import com.example.agile0509.pojo.StudentInfo;
import com.example.agile0509.pojo.User;
import com.example.agile0509.pojo.VolunteerService;
import com.example.agile0509.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teacher/")
public class AssessController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getGPA")
    public CommonResult<?> getStudentGPAList (@RequestHeader("Authorization") String authHeader)  {

        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User foundUser = userMapper.findByUsername(username);

        if(!foundUser.getRole().equals("评委")){
            return CommonResult.error(403, "没有该权限，禁止访问");
        }

        List<Score> scores = scoreMapper.getScores();
        List<StuGPAReqVo> studentList = new ArrayList<>();

        for (Score score : scores) {
            Integer studentId = score.studentId;
            StudentInfo studentInfo = studentInfoMapper.getStudentInfoByStudentId(studentId);

            //通过school_id在school表中找到对应学苑
            Integer school_id=studentInfo.getSchoolID();
            String department= schoolMapper.findSchoolBySchoolId(school_id);

            StuGPAReqVo stugpaReqVO = new StuGPAReqVo();
            stugpaReqVO.setName(studentInfo.getName());
            stugpaReqVO.setId(studentId);
            stugpaReqVO.setDepartment(department);
            stugpaReqVO.setGpa(score.score);

            studentList.add(stugpaReqVO);
        }

        return CommonResult.success(studentList);
    }

    @PostMapping("/changeGPA")
    public CommonResult<?> updateGPAInfo(@RequestHeader("Authorization") String authHeader, @RequestBody GPAUpdateReqVo request) {
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User foundUser = userMapper.findByUsername(username);
        if(!foundUser.getRole().equals("评委")){
            return CommonResult.error(403, "没有该权限，禁止访问");
        }

        // 从数据库中获取成绩信息
        String pastgpa = scoreMapper.getGpaByStudentId(request.getId());

        CommonResult<String> result;
        if(pastgpa!= request.getNewgpa()&foundUser.getRole().equals("评委")) {
            // 更新用户信息
            scoreMapper.updateScore(request.getId(), request.getGpa(), request.getNewgpa());
            result = CommonResult.success("成绩信息修改成功！");
        }else{
            result = CommonResult.success("成绩信息修改失败");
        }
        return result;
    }

    @GetMapping("/getVolunteer")
    public CommonResult<?> getStudentVolunList (@RequestHeader("Authorization") String authHeader)  {

        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User foundUser = userMapper.findByUsername(username);

        if(!foundUser.getRole().equals("评委")){
            return CommonResult.error(403, "没有该权限，禁止访问");
        }

        List<VolunteerService> volunteers = volunteerMapper.getVolunteers();
        List<StuVolunteerReqVo> studentList = new ArrayList<>();

        for (VolunteerService volunteer : volunteers) {

            Integer studentId = volunteer.studentId;
            StudentInfo studentInfo = studentInfoMapper.getStudentInfoByStudentId(studentId);

            //通过school_id在school表中找到对应学苑
            Integer school_id=studentInfo.getSchoolID();
            String department= schoolMapper.findSchoolBySchoolId(school_id);

            StuVolunteerReqVo stuvolunteerReqVO = new StuVolunteerReqVo();
            stuvolunteerReqVO .setName(studentInfo.getName());
            stuvolunteerReqVO .setId(studentId);
            stuvolunteerReqVO .setDepartment(department);
            stuvolunteerReqVO .setHours(volunteer.duration);

            studentList.add(stuvolunteerReqVO);
        }

        return CommonResult.success(studentList);
    }
}
