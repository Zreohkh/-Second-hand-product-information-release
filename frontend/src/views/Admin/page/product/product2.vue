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
                @clear="getProductList"
              >
                <el-button
                  slot="append"
                  icon="el-icon-search"
                  @click="getProductList"
                ></el-button>
              </el-input>
            </el-col>

          </el-row>
        </el-col>
        <el-col :span="24">
          <!--表格-->
          <el-table
            :data="productList"
            border
            stripe
            @selection-change="handleSelectionChange"
          >
            <el-table-column prop="itemInfo.pid" label="物品编号" ></el-table-column>
            <el-table-column prop="itemInfo.pcid" label="用户编号" ></el-table-column>
            <el-table-column prop="itemInfo.pname" label="物品名"></el-table-column>
            <el-table-column prop="itemInfo.type" label="物品类型"></el-table-column>
            <el-table-column prop="itemInfo.des" label="物品描述"></el-table-column>
            <el-table-column prop="itemInfo.pcon" label="是否下架或被用户删除"></el-table-column>
            <el-table-column prop="itemInfo.price" label="期待价格(或)"></el-table-column>
            <el-table-column prop="itemInfo.item" label="想换物品(或)"></el-table-column>
            <el-table-column prop="itemInfo.rcon" label="审核状态">
              <template slot-scope="scope">
                  <span v-html="formatContact(scope.row.itemInfo.rcon)"></span>
                </template>
            </el-table-column>
            <el-table-column prop="image" label="图片">
              <template slot-scope="scope">
                <el-image
                  style="width: 100px; height: 100px"
                  :src="scope.row.image.url"
                  :preview-src-list="scope.row.image.srcList"
                >
                </el-image>
              </template>
            </el-table-column>
            <el-table-column label="通过">
              <!-- 作用域插槽 -->
              <template slot-scope="scope">
                <!--通过按钮-->
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-edit"
                  @click="approve(scope.row.itemInfo.pid)"
                >通过</el-button>

              </template>
            </el-table-column>

            <el-table-column label="删除">
              <!-- 作用域插槽 -->
              <template slot-scope="scope">


              <!--删除按钮-->
              <el-button
                type="danger"
                size="mini"
                icon="el-icon-delete"
                @click="removeProductById(scope.row.itemInfo.pid)"
              >删除</el-button>

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
  import { productList,Delete,review} from "@/api/admin";
  export default {
    data() {
      return {
  
        productList: [], // 物品列表
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
      this.getProductList();
    },
    methods: {
      formatContact(rcon) {
            if(rcon===2) return '审核不通过'
            else if(rcon===1) return '审核通过'
            else return '审核中';
        },
  
  
      getProductList() {
        const copy = Object.assign(this.queryInfo, { type: 2 });
        productList(copy)
          .then((res) => {
            if (res.data.code === 200) {
              //物品列表
              this.productList = res.data.data.records;
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
        this.getProductList();
      },
      // 监听 当前页码值 改变的事件
      handleCurrentChange(newPage) {
        // console.log(newPage)
        this.queryInfo.pageNo = newPage;
        // 重新发起请求列表
        this.getProductList();
      },


   
        async removeProductById(id) {
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
        Delete({id:id,tableName:"product"})
          .then((res) => {
            if (res.data.code === 200) {
              this.getProductList();
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
    async batchDeleteProduct(){
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
        deleteProductBatch(this.ids)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "批量删除成功",
                type: "success",
              });
              this.getProductList();
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

    approve(id) {
        review({id:id,rcon:1,tableName:'product'}).then(res=>{
            console.log(res.data.data);
            this.getProductList();
        })
       
      },
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
  