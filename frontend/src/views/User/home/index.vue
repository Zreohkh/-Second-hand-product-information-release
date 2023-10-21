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
          <el-table-column prop="itemInfo.pid" label="物品编号" v-if="false"></el-table-column>
          <el-table-column prop="itemInfo.pname" label="物品名"></el-table-column>
          <el-table-column prop="itemInfo.type" label="物品类型"></el-table-column>
          <el-table-column prop="itemInfo.des" label="物品描述"></el-table-column>
          <el-table-column prop="itemInfo.price" label="期待价格(或)"></el-table-column>
          <el-table-column prop="itemInfo.item" label="想换物品(或)"></el-table-column>
          <el-table-column prop="itemInfo.rcon" label="审核状态" v-if="false">
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
          <el-table-column label="发送留言">
            <!-- 作用域插槽 -->
            <template slot-scope="scope">
              <!--申请按钮-->
              <el-button
                type="primary"
                size="mini"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row)"
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


    <el-dialog title="留言信息" :visible.sync="editDialogVisible" width="30%">
      <!--内容主体区域-->
      <el-form :model="mesform" label-width="70px">
        <el-form-item label="留言" prop="pname">
          <el-input v-model="mesform.mes" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeMesform">取 消</el-button>
        <el-button type="primary" @click="editProduct">确 定</el-button>
      </span>
    </el-dialog>

    </div>
</template>

<script>
import { productList} from "@/api/search";
import { applyFor } from "@/api/trade";
export default {
  data() {
    return {
      mesform:{mes:""},
      uploadData:{
            tfcid:this.$store.state.user.cid,
            tpid:"",
            message:"",
      },
      editDialogVisible: false, // 控制留言框是否显示


      productList: [], // 物品列表
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
    this.getProductList();
  },
  methods: {
    formatContact(rcon) {
          if(rcon===2) return '审核不通过'
          else if(rcon===1) return '审核通过'
          else return '审核中';
      },
    editProduct() {
      this.uploadData.message = this.mesform.mes;
      console.log(this.uploadData);
      applyFor(this.uploadData)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible = false;
            this.$message(res.data.message);
            this.getProductList();
            this.uploadData.tpid = "";
            this.uploadData.message="";
          } else {
            this.$message.error("申请失败");
          }
        })
        .catch((err) => {
          this.$message.error("申请异常");
          console.loge(err);
        });
    },
    // 监听申请状态
    showEditDialog(productinfo) {
      this.uploadData.tpid = productinfo.itemInfo.pid;
      this.editDialogVisible = true;
    },
    closeMesform()
    {
      this.editDialogVisible = false;
      this.uploadData.tpid = "";
      this.uploadData.message="";
    },


    getProductList() {
      const copy = Object.assign(this.queryInfo, { pcid: this.$store.state.user.cid });
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
