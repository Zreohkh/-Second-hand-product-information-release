<template>
    <div>
      <!--Layout布局-->
      <el-row>
        <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteProduct"
              >批量删除</el-button
            >
          </el-col>
        <el-col :span="24">
          <!--表格-->
          <el-table
            :data="tradeList"
            border
            stripe
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="tid" label="编号" ></el-table-column>
            <el-table-column prop="tfcid" label="用户编号" ></el-table-column>
            <el-table-column prop="tpid" label="物品编号"></el-table-column>
            <el-table-column prop="message" label="留言"></el-table-column>
            <el-table-column prop="tdate" label="时间">
                <template slot-scope="scope">
                    {{ formatDate(scope.row.tdate) }}
                </template>
            </el-table-column>
            <el-table-column prop="tcon" label="状态"></el-table-column>

            <el-table-column label="操作">
              <!-- 作用域插槽 -->
              <template slot-scope="scope">
              <!--删除按钮-->
              <el-button
                type="danger"
                size="mini"
                icon="el-icon-delete"
                @click="removeTradeById(scope.row.tid)"
              ></el-button>

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
  import { tradeList,Delete,deleteTradeBatch} from "@/api/admin";
  export default {
    data() {
      return {
        mesform:{mes:""},
        uploadData:{
              tfcid:this.$store.state.user.cid,
              tpid:"",
              message:"",
        },
        
        value1: '',
        tradeList: [], // 物品列表
        total: 0, // 物品总数
        // 获取物品列表的参数对象
        queryInfo: {
          keyword: "", // 查询参数
          pageNo: 1, // 当前页码
          pageSize: 5, // 每页显示条数
        },
        multipleSelection: [],
        ids: [],
      }
    },
    created() {
      // 生命周期函数
      this.getTradeList();
    },
    methods: {
    formatDate(dateTimeString) {
      const originalDate = new Date(dateTimeString);
      const datePart = originalDate.toISOString().split('T')[0];
      const timePart = originalDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
      return `${datePart} ${timePart}`;
        },
  
      getTradeList() {
        tradeList(this.queryInfo)
          .then((res) => {
            if (res.data.code === 200) {
              //物品列表
              this.tradeList = res.data.data.records;
              this.total = res.data.data.total;
              console.log(this.tradeList);
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
        this.getTradeList();
      },
      // 监听 当前页码值 改变的事件
      handleCurrentChange(newPage) {
        // console.log(newPage)
        this.queryInfo.pageNo = newPage;
        // 重新发起请求列表
        this.getTradeList();
      },
    },


   
     async removeTradeById(id) {
      // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "此操作将删除物品, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      // 如果确认删除，则返回值为字符串 confirm
      // 如果取消删除，则返回值为字符串 cancel
      // console.log(confirmResult)
      if (confirmResult == "confirm") {
        //删除物品
        Delete({id:id,tableName:'trade'})
          .then((res) => {
            if (res.data.code === 200) {
              this.getTradeList();
              this.$message({
                message: "删除物品成功",
                type: "success",
              });
            } else {
              this.$message.error("删除物品失败");
            }
          })
          .catch((err) => {
            this.$message.error("删除物品异常");
            console.log(err);
          });
      }
    },
    //批量选中事件处理
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
      //向被删除的ids赋值
      this.multipleSelection.forEach((item) => {
        this.ids.push(item.pid);
        console.log(this.ids);
      });
    },
    //批量删除用户
    async batchDeleteTrade(){
     // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "此操作将永久删除用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      // 如果确认删除，则返回值为字符串 confirm
      // 如果取消删除，则返回值为字符串 cancel
      if (confirmResult == "confirm") {
        //批量删除
        deleteTradeBatch(this.ids)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "批量删除成功",
                type: "success",
              });
              this.getTradeList();
            } else {
              this.$message.error("批量删除失败");
            }
          })
          .catch((err) => {
            this.$message.error("批量删除异常");
            console.log(err);
          });
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
  