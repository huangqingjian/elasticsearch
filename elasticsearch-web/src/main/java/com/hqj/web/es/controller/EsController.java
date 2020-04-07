package com.hqj.web.es.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hqj.common.controller.BaseController;
import com.hqj.common.dto.ResponseDTO;
import com.hqj.es.client.EsClient;
import com.hqj.es.dto.*;
import com.hqj.es.service.EsService;
import com.hqj.job.JobService;
import com.hqj.user.dto.UserDTO;
import com.hqj.utils.CsvUtils;
import com.hqj.utils.HtmlUtils;
import com.hqj.utils.SqlUtils;
import com.hqj.common.constant.Constant;
import com.hqj.common.dto.PageDTO;
import com.hqj.common.enums.YesNo;
import com.hqj.es.dto.*;
import com.hqj.web.es.form.IndexDataForm;
import com.hqj.web.es.form.InterfaceForm;
import com.hqj.web.es.form.SqlForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "Es管理器", tags = "Es.Manager")
@RestController
@RequestMapping(value = "/es")
public class EsController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(EsController.class);

    private static final String UPLOAD_FILE_NOT_NULL = "上传文件不能为空";
    private static final String UPLOAD_FILE_NOT_CSV = "上传文件必须是csv文件";

    @Autowired
    private EsClient client;
    @Autowired
    private EsService esService;
    @Autowired
    private JobService jobService;

    @ApiOperation("查询索引")
    @RequestMapping(value = "/listIndex", method = RequestMethod.GET)
    public ResponseDTO<PageDTO> listIndex(IndexQueryDTO reqDTO) {
        if(reqDTO.getPageNum() == null) {
            reqDTO.setPageNum(Constant.PAGE_NUM);
        }
        if(reqDTO.getPageSize() == null) {
            reqDTO.setPageSize(Constant.PAGE_SIZE);
        }
        UserDTO user = getUser();
        if(user.getManaged() != null && YesNo.YES.getValue() != user.getManaged()) {
            reqDTO.setUserId(user.getId());
        }
        PageDTO page = esService.listIndex(reqDTO);
        return ResponseDTO.success(page);
    }

    @ApiOperation("查询索引任务日志")
    @RequestMapping(value = "/listIndexJobLog", method = RequestMethod.GET)
    public ResponseDTO<PageDTO> listIndexJobLog(IndexQueryDTO reqDTO) {
        if(reqDTO.getPageNum() == null) {
            reqDTO.setPageNum(Constant.PAGE_NUM);
        }
        if(reqDTO.getPageSize() == null) {
            reqDTO.setPageSize(Constant.PAGE_SIZE);
        }
        PageDTO page = esService.listIndexJobLog(reqDTO);
        return ResponseDTO.success(page);
    }

    @ApiOperation("创建索引")
    @RequestMapping(value = "/createIndex", method = RequestMethod.POST)
    public ResponseDTO<Long> createIndex(@RequestBody IndexSaveOrUpdateReqDTO reqDTO) {
        Long userId = getUserId();
        reqDTO.setUserId(userId);
        Long id = esService.createIndex(reqDTO);
        return ResponseDTO.success(id);
    }

    @ApiOperation("更新索引")
    @RequestMapping(value = "/updateIndex", method = RequestMethod.POST)
    public ResponseDTO<Long> updateIndex(@RequestBody IndexSaveOrUpdateReqDTO reqDTO) {
        Long userId = getUserId();
        reqDTO.setUserId(userId);
        Long id = esService.updateIndex(reqDTO);
        return ResponseDTO.success(id);
    }

    @ApiOperation("手动全量")
    @RequestMapping(value = "/manual/all", method = RequestMethod.POST)
    public ResponseDTO<Long> manualAll(@RequestParam("id") Long id) {
        jobService.manual(id, true);
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("手动增量")
    @RequestMapping(value = "/manual/incr", method = RequestMethod.POST)
    public ResponseDTO<Long> manualIncr(@RequestParam("id") Long id) {
        jobService.manual(id, false);
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("删除索引")
    @RequestMapping(value = "/deleteIndex", method = RequestMethod.DELETE)
    public ResponseDTO<Boolean> deleteIndex(@RequestParam("id") Long id) {
        esService.deleteIndex(id);
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("索引开关")
    @RequestMapping(value = "/switchs", method = RequestMethod.POST)
    public ResponseDTO<Boolean> switchs(@RequestBody IndexJobDTO jobDTO) {
        esService.switchIndexJob(jobDTO.getId(), jobDTO.getSwitchs());
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("删除索引任务日志")
    @RequestMapping(value = "/deleteIndexJobLog", method = RequestMethod.DELETE)
    public ResponseDTO<Boolean> deleteIndexJobLog(@RequestParam("id") Long id) {
        esService.deleteIndexJobLog(id);
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("数据添加")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseDTO<String> save(@RequestBody IndexDataForm form) {
        client.saveOrUpdate(form.getIndexName(), form.getId(), form.getObjectMap());
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("通过ID更新数据")
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public ResponseDTO<Boolean> updateById(@RequestBody IndexDataForm form) {
        client.saveOrUpdate(form.getIndexName(), form.getId(), form.getObjectMap());
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("通过ID删除数据")
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public ResponseDTO<Boolean> deleteById(@RequestBody IndexDataForm form) {
        client.deleteById(form.getIndexName(), form.getId());
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("sql解析")
    @RequestMapping(value = "/parseSelectSql", method = RequestMethod.POST)
    public ResponseDTO<List<String>> parseSelectSql(@RequestBody SqlForm form) {
        List<String> list = SqlUtils.parseSelectSQL(form.getSql());
        return ResponseDTO.success(list);
    }

    @ApiOperation("json映射解析")
    @RequestMapping(value = "/parseMappingJson", method = RequestMethod.POST)
    public ResponseDTO<List<String>> parseMappingJson(@RequestBody String json) {
        List<String> properties = Lists.newArrayList();
        JSONObject jObject = JSONObject.parseObject(json);
        if(!jObject.isEmpty()) {
            for(Map.Entry entry : jObject.entrySet()) {
                properties.add(String.valueOf(entry.getKey()));
            }
        }
        return ResponseDTO.success(properties);
    }

    @ApiOperation("请求指定接口")
    @RequestMapping(value = "/requestUrl", method = RequestMethod.POST)
    public ResponseDTO<List<IndexMappingDTO>> requestUrl(@RequestBody InterfaceForm form) {
        List<IndexMappingDTO> mappings = esService.getIndexMapping(form.getUrl());
        return ResponseDTO.success(mappings);
    }

    @ApiOperation("导入映射csv")
    @RequestMapping(value = "/importCsv", method = RequestMethod.POST)
    public ResponseDTO importCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(UPLOAD_FILE_NOT_NULL);
        }
        String filename = file.getOriginalFilename();
        if(!filename.endsWith(".csv")) {
            return ResponseDTO.fail(UPLOAD_FILE_NOT_CSV);
        }
        try {
            return ResponseDTO.success(HtmlUtils.generalMapping(CsvUtils.importCsv(file.getInputStream())));
        } catch (Exception e) {
            return ResponseDTO.fail(e.getMessage());
        }
    }

    @ApiOperation("导出映射csv")
    @RequestMapping(value = "/exportCsv/{indexId}", method = RequestMethod.GET)
    public void exportCsv(HttpServletResponse response, @PathVariable("indexId") Long indexId) {
        try {
            IndexDTO index = esService.getSimpleIndex(indexId);
            String fileName = "";
            if (index == null) {
                fileName = System.currentTimeMillis() + ".csv";
            } else {
                fileName = index.getName() + ".csv";
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            CsvUtils.exportCsv(response.getOutputStream(), esService.getIndexPropertyHeader(), esService.getIndexProperty(indexId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
