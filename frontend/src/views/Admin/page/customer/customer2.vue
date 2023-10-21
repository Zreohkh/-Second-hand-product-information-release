<template>
    <div>
      <!--Layout布局-->
      <el-row>
        <el-col :span="24">
          <el-row :gutter="20">
            <el-col :span="6">
              <!--搜索区域-->
              <el-input
                placeholder="请输入内容"
                v-model="queryInfo.keyword"
                clearable
                @clear="getCustomerList"
              >
                <el-button
                  slot="append"
                  icon="el-icon-search"
                  @click="getCustomerList"
                ></el-button>
              </el-input>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <!--表格-->
          <el-table
            :data="customerList"
            border
            stripe
            @selection-change="handleSelectionChange"
          >
            <el-table-column prop="cid" label="用户编号" v-if="false"></el-table-column>
            <el-table-column prop="cname" label="用户名"></el-table-column>
            <el-table-column prop="cpass" label="密码"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="qq" label="QQ"></el-table-column>
            <el-table-column prop="wechat" label="微信"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="des" label="个人描述"></el-table-column>
            <el-table-column prop="rcon" label="审核状态"></el-table-column>
            <el-table-column label="操作">
              <!-- 作用域插槽 -->
              <template slot-scope="scope">
                <!--修改按钮-->
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-edit"
                  @click="approve(scope.row.cid)"
                >通过</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <el-row>
        <!--分页区域-->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNo"
          :page-sizes="[1, 2, 5, 10]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </el-row>
     
    </div>
  </template>
  
  <script>
  import {customerList,review} from "@/api/admin";

  export default {
    data() {
      return {
  
        customerList: [], // 物品列表
        total: 0, // 物品总数
        // 获取物品列表的参数对象
        queryInfo: {
          keyword: "", // 查询参数
          pageNo: 1, // 当前页码
          pageSize: 5, // 每页显示条数
        },
      }
    },
    created() {
      // 生命周期函数
      this.getCustomerList();
    },
    methods: {
      //获取物品列表
      getCustomerList() {
        const copy = Object.assign(this.queryInfo, { type: 2 });
        customerList(copy)
          .then((res) => {
            if (res.data.code === 200) {
              //物品列表
              this.customerList = res.data.data.records;
              this.total = res.data.data.total;
            } else {
              this.$message.error(res.data.message);
            }
          })
          .catch((err) => {
            console.log(err);
          });
      },
      // 监听 pageSize 改变的事件
      handleSizeChange(newSize) {
        // console.log(newSize)
        this.queryInfo.pageSize = newSize;
        // 重新发起请求列表
        this.getCustomerList();
      },
      // 监听 当前页码值 改变的事件
      handleCurrentChange(newPage) {
        // console.log(newPage)
        this.queryInfo.pageNo = newPage;
        // 重新发起请求列表
        this.getCustomerList();
      },

      //同意修改
      approve(id) {
        review({id:id,rcon:1,tableName:'customer'}).then(res=>{
            console.log(res.data.data);
            this.getCustomerList();
        })
       
      }
    },
  };
  </script>
  
  <style>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  .el-card {
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1) !important;
    height: 60pt;
  }
  </style>
  