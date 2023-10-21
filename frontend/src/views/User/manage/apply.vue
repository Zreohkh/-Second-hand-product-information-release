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
              @clear="getTradeList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getTradeList"
              ></el-button>
            </el-input>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24">
        <!--表格-->
        <el-table
          :data="tradeList"
          border
          stripe
          @selection-change="handleSelectionChange">
          <el-table-column prop="tid" label="订单编号" v-if="false"> </el-table-column>
          <el-table-column prop="cname" label="用户名"> </el-table-column>
          <el-table-column prop="pname" label="物品名"></el-table-column>
          <el-table-column prop="type" label="物品类型"></el-table-column>
          <el-table-column prop="message" label="留言"></el-table-column>
          <el-table-column prop="tcon" label="状态">
            <template slot-scope="scope">
              {{ displayContent(scope.row.tcon) }}
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
import {applyList} from "@/api/trade";
export default {
  data() {
    return {
      uploadData: {ppid:""},
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
    console.log(this.tradeList)
  },
  methods: {
    displayContent(value){
      if(value===0) return '申请中';
      else if(value===1) return '被拒绝'
    },
      getTradeList() {
      const copy = Object.assign(this.queryInfo, { pcid: this.$store.state.user.cid });
      applyList(copy)
        .then((res) => {
          if (res.data.code === 200) {
            this.tradeList = res.data.data.records;
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
      this.getTradeList();
    },
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      this.queryInfo.pageNo = newPage;
      // 重新发起请求列表
      this.getTradeList();
    },
    
    

    // 根据ID删除对应的用户信息
    async removetradeById(id) {
      // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "此操作将删除请求, 是否继续?",
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
        console.log(id);
        applyDelete(id)
          .then((res) => {
            if (res.data.code === 200) {
              this.getTradeList();
              this.$message({
                message: "删除成功",
                type: "success",
              });
            } else {
              this.$message.error("删除失败");
            }
          })
          .catch((err) => {
            this.$message.error("删除异常");
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
        this.ids.push(item.tid);
        console.log(this.ids);
      });
    },
    //批量删除用户
    async batchDeletetrade(){
     // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "此操作将删除请求, 是否继续?",
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
        console.log(this.ids)
        applyBatchDelete(this.ids)
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
  },

}
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
