<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="listQuery.name" placeholder="图片名称"></el-input>
        </el-form-item>
        <el-form-item label="图片类型">
          <el-select v-model="listQuery.category" placeholder="类型" clearable>
            <el-option label="壁纸" value="壁纸"></el-option>
            <el-option label="表情包" value="表情包"></el-option>
            <el-option label="个人图片" value="个人图片"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryHandler">查询</el-button>
          <el-button type="success" icon="plus" @click="showAddDialog">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80" type="index">

      </el-table-column>
      <el-table-column align="center" prop="name" label="名称" width="80"></el-table-column>
      <el-table-column align="center" prop="category" label="图片类型" width="100"></el-table-column>
      <el-table-column align="center" prop="type" label="标签" width="100"></el-table-column>
      <el-table-column align="center" prop="common" :formatter="formatCommon" label="是否共享"
                       width="100"></el-table-column>
      <el-table-column align="center" prop="hot" label="热门等级" width="100">

      </el-table-column>
      <!--
            <el-table-column align="center" prop="album" label="相册" width="100"></el-table-column>
      -->
      <el-table-column align="center" prop="img" label="预览" width="100">
        <template slot-scope="scope">
          <el-button type="success" v-on:click="preView(scope.row.img)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="des" label="描述" width="220"></el-table-column>
      <el-table-column align="center" prop="status" label="状态" width="100"></el-table-column>

      <el-table-column align="center" label="管理" width="300">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showEdit(scope.$index)">編輯</el-button>
          <el-button type="success" icon="audit" @click="showAudit(scope.$index)">审核</el-button>
          <el-button type="danger" @click="showDel(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="listQuery.pageSize"
      :page-count="listQuery.pages"
      :current-page="listQuery.pageNum"
      @current-change="currentPageChange"
      :total="listQuery.total">
    </el-pagination>


    <!--图片审核对话框-->
    <el-dialog title="图片审核" :visible.sync="dialogFormAuditVisible" width="30%">
      <el-radio-group v-model="ImgInfo.status">
        <el-radio label="未上线">未上线</el-radio>
        <el-radio label="申请上线">申请上线</el-radio>
        <el-radio label="上线中">上线中</el-radio>
      </el-radio-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAudit()">确定</el-button>
      </div>
    </el-dialog>

    <!-- Form -->
    <el-dialog title="编辑图片" :visible.sync="dialogFormEditVisible">
      <el-form ref="form" :model="ImgInfo" label-width="80px" size="mini">
        <el-form-item label="图片名称">
          <el-input v-model="ImgInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="图片描述">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入图片描述"
            v-model="ImgInfo.des">
          </el-input>
        </el-form-item>
        <el-form-item label="图片标签">
          <el-input v-model="ImgInfo.type"></el-input>
        </el-form-item>
        <el-form-item label="热门等级">
          <el-rate
            v-model="ImgInfo.hot"
            :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
          </el-rate>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEdit">确 定</el-button>
      </div>
    </el-dialog>

    <!--图片上传-->
    <el-dialog title="上传图片" :visible.sync="dialogFormAddVisible">
      <el-upload
        ref="upload"
        class="upload-demo"
        action="http://localhost:8080/img/upload"
        name="file"
        :data="ImgInfo"
        :accept="'image/*'"
        :on-success="handleUploadSuccess"
        :before-upload="beforeUpload"
        :file-list="fileList"
        :auto-upload="false"
        :with-credentials="needCookie"
        :on-preview="handlePreview"
        :limit="1"
        list-type="picture">
        <el-button size="small" type="primary">选取图片</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
      <el-form ref="ImgInfo" :model="ImgInfo" label-width="70px" size="mini">
        <el-form-item label="图片名称">
          <el-input v-model="ImgInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="图片类型">
          <el-select v-model="ImgInfo.category" placeholder="请选择图片类型">
            <el-option label="壁纸" value="壁纸"></el-option>
            <el-option label="表情包" value="表情包"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="图片标签">
          <el-input v-model="ImgInfo.type"></el-input>
        </el-form-item>
        <el-form-item label="图片描述">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入图片描述"
            v-model="ImgInfo.des">
          </el-input>
        </el-form-item>
        <el-form-item label="热门等级">
          <el-rate
            v-model="ImgInfo.hot"
            :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
          </el-rate>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormAddVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUpload">确定上传</el-button>
      </div>
    </el-dialog>
    <el-dialog title="预览" :visible.sync="preViewShow" class="imgDialog">
      <img :src="currentImg" style="width: 300px;height:300px"/>
    </el-dialog>

  </div>
</template>


<script>

  import getToken from "../../utils/auth"

  export default {
    data() {
      return {
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageSize: 10,//页码
          pageNum: 1,//每页条数
          total: 10,
          pages: 1,
          name: '',
          category: ''
        },
        dialogFormAddVisible: false,
        dialogFormEditVisible: false,
        dialogFormAuditVisible: false,

        dialogImageUrl: '',
        dialogUploadVisible: false,
        ImgInfo: {
          id: "",
          name: "",
          category: "",
          type: "",
          common: "",
          hot: "",
          album: "",
          img: "",
          des: "",
          status: ""
        },
        needCookie: true,
        fileList: [],
        preViewShow: false,
        currentImg: '',
      }
    },
    created() {
      this.getList();
    },
    methods: {
      preView(img) {
        this.preViewShow = true;
        this.currentImg = img;
      },
      currentPageChange(currentPage) {
        console.log(currentPage);
        this.listQuery.pageNum = currentPage;
        this.getList();
      },
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/img/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          console.log(data);
          this.list = data.list;
          this.listQuery.pageSize = data.pageSize;
          this.listQuery.pageNum = data.pageNum;
          this.listQuery.total = data.total;
          this.listQuery.pages = data.pages;
        })
      },
      queryHandler() {
        //条件查询
        this.listLoading = true;
        this.api({
          url: "/img/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.listQuery.pageSize = data.pageSize;
          this.listQuery.pageNum = data.pageNum;
          this.listQuery.total = data.total;
          this.listQuery.pages = data.pages;
          if (this.list.length == 0) {
            this.$message({
              type: "info",
              message: "未查询到符合条件的图片！"
            })
          } else {
            this.$message({
              type: "success",
              message: `查询到${this.list.length}张图片！`
            })
          }
        })

      },

      formatCommon(row, col, cellVal) {
        return cellVal == "1" ? "共享" : "私有"
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },

      showAddDialog() {
        //显示上传图片对话框
        this.dialogFormAddVisible = true
        this.ImgInfo = {};
        this.$ref.upload.clearFiles();
      },

      showDel($index) {
        this.ImgInfo.id = this.list[$index].id;
        this.$confirm('此操作将永久删除该图片, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleDel();
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      showAudit($index) {
        //显示图片审核对话框
        console.log(this.list[$index].status);
        this.ImgInfo.id = this.list[$index].id;
        this.ImgInfo.status = this.list[$index].status;
        this.dialogFormAuditVisible = true

      },
      showEdit($index) {
        //显示修改对话框
        this.ImgInfo = {
          ...this.list[$index]
        }
        this.dialogFormEditVisible = true
      },

      handleAudit() {
        //图片审核
        this.api({
          url: "/img/check",
          method: "post",
          params: {id: this.ImgInfo.id, status: this.ImgInfo.status}
        }).then(() => {
          this.getList();
        }).catch((err) => {
          console.log("error", err);
        })
        this.dialogFormAuditVisible = false
      },

      handleEdit() {
        //编辑图片
        this.api({
          url: '/img/',
          method: "put",
          data: this.ImgInfo
        }).then(() => {
          this.getList();
          this.$message({
            type: 'success',
            message: '修改成功!'
          });
        }).catch((err) => {
          console.log("error", err)
        })
        this.dialogFormEditVisible = false
      },
      handleDel() {
        //删除图片
        console.log("del...")
        this.api({
          url: '/img/' + this.ImgInfo.id,
          method: 'delete',
        }).then(
          this.getList()
        )
      },

      handlePreview(file) {
        console.log("upload preview...")
        console.log(file);
      },

      submitUpload() {
        this.$refs.upload.submit();
      },

      handleUploadSuccess(res, file) {
        this.dialogFormAddVisible = false;
        this.$message({
          type: 'success',
          message: '图片上传成功!'
        });
        this.fileList = [];
        this.getList();
      },

      beforeUpload(file) {
        console.log(file)
      }

    }
  }
</script>

<style scoped>
  .imgDialog {
    width: 800px !important;
  }
</style>
