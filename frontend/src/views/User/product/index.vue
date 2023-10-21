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
          <el-col :span="2.5">
            <el-button type="primary" @click="addDialogVisible = true"
              >添加物品</el-button
            >
          </el-col>
          <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteProduct"
              >批量删除</el-button
            >
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
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column prop="pid" label="物品编号" v-if="false"></el-table-column>
          <el-table-column prop="pname" label="物品名"></el-table-column>
          <el-table-column prop="type" label="物品类型"></el-table-column>
          <el-table-column prop="des" label="物品描述"></el-table-column>
          <el-table-column prop="pcon" label="物品状态"></el-table-column>
          <el-table-column prop="price" label="目标价格"></el-table-column>
          <el-table-column prop="item" label="目标物品"></el-table-column>
          <el-table-column prop="rcon" label="审核状态"></el-table-column>
          <el-table-column label="操作">
            <!-- 作用域插槽 -->
            <template slot-scope="scope">
              <!--修改按钮-->
              <el-button
                type="primary"
                size="mini"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row)"
              ></el-button>
              <!--删除按钮-->
              <el-button
                type="danger"
                size="mini"
                icon="el-icon-delete"
                @click="removeProductById(scope.row.pid)"
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
    <!--添加物品的对话框-->
    <el-dialog
      title="添加物品"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogClosed"
    >
      <!--内容主体区域-->
      <el-form :model="productForm" label-width="70px" >
        <el-form-item label="物品名" prop="pname">
          <el-input v-model="productForm.pname"></el-input>
        </el-form-item>
        <el-form-item label="物品类型" prop="type">
          <el-input v-model="productForm.type"></el-input>
        </el-form-item>
        <el-form-item label="物品描述" prop="des">
          <el-input type="textarea" v-model="productForm.des"></el-input>
        </el-form-item>
        <el-form-item label="目标价格" prop="price">
          <el-input v-model="productForm.price"></el-input>
        </el-form-item>
        <el-form-item label="目标物品" prop="item">
          <el-input v-model="productForm.item"></el-input>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addProduct">确 定</el-button>
      </span>
    </el-dialog>
    <!--修改物品的对话框-->
    <el-dialog title="修改物品信息" :visible.sync="editDialogVisible" width="30%">
      <!--内容主体区域-->
      <el-form :model="editForm" label-width="70px">
        <el-form-item label="物品名" prop="pname">
          <el-input v-model="editForm.pname"></el-input>
        </el-form-item>
        <el-form-item label="物品类型" prop="type">
          <el-input v-model="editForm.type"></el-input>
        </el-form-item>
        <el-form-item label="物品状态" prop="pcon">
          <el-radio v-model="editForm.pcon" label=0>上架</el-radio>
          <el-radio v-model="editForm.pcon" label=1>下架</el-radio>
        </el-form-item>
        <el-form-item label="物品描述" prop="des">
          <el-input type="textarea" v-model="editForm.des"></el-input>
        </el-form-item>
        <el-form-item label="目标价格" prop="price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
        <el-form-item label="目标物品" prop="item">
          <el-input v-model="editForm.item"></el-input>
        </el-form-item>

        <!-- 图片上传 -->
        <el-upload
          class="upload-demo"
          action="http://localhost:8088/api/photo/uploadPhoto"
          :data="uploadData" 
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editProduct">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {productList, productAdd, productUpdate, productDelete, productBatchDelete} from "@/api/product";
import {photoList,deletePhoto} from "@/api/photo";
export default {
  data() {
    return {

      fileList: [],
      uploadData: {ppid:""},

      productList: [], // 物品列表
      total: 0, // 物品总数
      // 获取物品列表的参数对象
      queryInfo: {
        keyword: "", // 查询参数
        pageNo: 1, // 当前页码
        pageSize: 5, // 每页显示条数
      },
      addDialogVisible: false, // 控制添加对话框是否显示
      productForm: {
        //商品
        pname: "",
        type: "",
        des: "",
        price: "",
        item: "",
      },
      editDialogVisible: false, // 控制修改对话框是否显示
      editForm: {
        pname: "",
        type: "",
        pcon: "",
        des: "",
        price: "",
        item: "",
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
    //获取图片列表
    loadFileList(id){
      photoList({ppid:id}).then(res=>{
        this.fileList = res.data.data
      })
    },
    //删除图片
    handleRemove(file, fileList) {
        console.log(file.url);
        deletePhoto({url:file.url}).then(res=>{})
      },
    handlePreview(file) {
        console.log(file.url);
    },
    //获取物品列表
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
    //添加物品
    addProduct() {
      const copy = Object.assign(this.productForm, { pcid: this.$store.state.user.cid });
      productAdd(copy)
        .then((res) => {
          if (res.data.code === 200) {
            this.addDialogVisible = false;
            this.getProductList();
            this.$message({
              message: "添加物品成功",
              type: "success",
            });
          } else {
            this.$message.error("添加物品失败");
          }
        })
        .catch((err) => {
          this.$message.error("添加物品异常");
          console.log(err);
        });
    },

    // 监听 添加物品对话框的关闭事件
    addDialogClosed() {
      // 表单内容重置为空
      this.productForm.pname = '';
      this.productForm.type = '';
      this.productForm.des = '';
      this.productForm.price = '';
      this.productForm.item = '';
    },

    // 监听 修改物品状态
    showEditDialog(productinfo) {
      
      this.editForm = productinfo;
      this.loadFileList(productinfo.pid);
      this.uploadData.ppid = productinfo.pid;
      this.editDialogVisible = true;
    },

    //修改物品信息
    editProduct() {
      const copy = Object.assign(this.editForm, { pcid: this.$store.state.user.cid});
      productUpdate(copy)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible = false;
            this.getProductList();
            this.$message({
              message: "修改成功",
              type: "success",
            });
          } else {
            this.$message.error("修改失败");
          }
        })
        .catch((err) => {
          this.$message.error("修改异常");
          console.loge(err);
        });
    },
    // 根据ID删除对应的用户信息
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
        productDelete(id)
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
        productBatchDelete(this.ids)
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
