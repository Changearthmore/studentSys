<template>
      <Transition>
      <div class="container">
  <div class="upload-tool">
    <span>
      <el-upload
        class="upload-demo"
        method="post"
        action="http://localhost:28080/studentInfo/upload"
        accept=".xlsx,.xls"
        :on-success="handleAvatarSuccess"
        :on-error="handleUploadError"
        :on-progress="uploadProgress"
        :headers="headerObj"
        :show-file-list="false"
        
        name="file"
      >
        <el-button type="success" size="medium" class="button" @click="reminder">导入excel</el-button>
      </el-upload>
    </span>   
  </div>
  <div class="space1"></div>
  <el-table :data="stuInfoList" stripe border style="width: 100%">
    <!--el-table-column prop="id" label="学生id" header-align="center"> </!--el-table-column-->
    <el-table-column prop="studentID" label="学生id" header-align="center"></el-table-column>
    <el-table-column prop="name" label="姓名" header-align="center"></el-table-column>
    <el-table-column prop="englishName" label="英文名" header-align="center"></el-table-column>
    <el-table-column prop="socialID" label="身份证号" header-align="center"></el-table-column>
    <el-table-column prop="stuNo" label="学生证号" header-align="center"></el-table-column>
    <el-table-column prop="gender" label="性别" header-align="center"></el-table-column>
    <el-table-column prop="phone" label="手机号" header-align="center"></el-table-column>
    <el-table-column prop="year" label="入学年份" header-align="center"></el-table-column>
    <el-table-column prop="schoolID" label="学苑号" header-align="center"></el-table-column>
    <el-table-column prop="place" label="生源地" header-align="center"></el-table-column>
    <el-table-column prop="nationality" label="国籍" header-align="center"></el-table-column>
    <el-table-column prop="birthday" label="生日" header-align="center"></el-table-column>
    <el-table-column prop="majorID" label="专业号" header-align="center"></el-table-column>
    <el-table-column prop="political" label="政治面貌" header-align="center"></el-table-column>
    <el-table-column prop="stutype" label="学生类型" header-align="center"></el-table-column>
  </el-table>
</div>
</Transition>
</template>
 
<script>
import * as XLSX from 'xlsx/xlsx.mjs'
import { getStuInfo } from "../api/login.js";
 
export default {
  computed: {
    headerObj(){
      const jwt= 'Bearer ' + localStorage.getItem('ACCESS_TOKEN')
      return{
        Authorization:jwt
      };
    },
  },
  components: {
   
  },
  data() {
    return {
      stuInfoList: [],
      // headerObj: {
      //   Authorization: localStorage.getItem("ACCESS_TOKEN"),
      // },
    };
  },
  mounted() {
    getStuInfo().then((res) => {
      console.log(res);
      this.stuInfoList = res.data;
    });
  },
  methods: {
    uploadProgress() {
      ElMessage.success('文件上传中')
    },
    reminder() {
      ElMessage.success('导入后请刷新页面！')
    },
    btnClick() {
      document.querySelector(".input-file").click();
    },
    refreshPage(){
      getStuInfo().then((res) => {
        console.log(res);
        this.stuInfoList = res.data;
      });
    },
    handleUploadError(err, file, fileList) { //上传失败钩子函数
	    console.log('err', err)
		  console.log('err', JSON.parse(err.message))
		  if (file.status == 'fail') {
			  ElMessage.error("上传失败")
		  }
    },
    handleAvatarSuccess() { //上传成功
	    ElMessage.success("上传成功！")
      getStuInfo().then((res) => {
        console.log(res);
        this.stuInfoList = res.data;
      });
    },
  }
};
</script>
 
<style>

.analysis-content{
  box-sizing: border-box;
  background: #efefef;
  padding:15px;
}

.analysis-content-head{
  box-sizing: border-box;
  background: #fff;
  padding:15px;
}

.analysis-content-body{
  box-sizing: border-box;
  margin-top: 15px;
  background: #fff;
  padding:15px;
  height:550px;
}

.input-file {
  display: none;
}

.column-content{
  box-sizing:border-box;padding:40px 15px 60px;
  

}
.el-checkbox-group{
    display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.el-checkbox.is-bordered{
  margin-bottom:15px;
}
.el-checkbox.is-bordered+.el-checkbox.is-bordered {
  margin-left: 0;
}
.drawer-footer{
   text-align: center;
}
/* .button {
  margin-top: 10px;
} */

</style>
 