package com.hqj.web.es.controller;

import com.hqj.common.dto.ResponseDTO;
import com.hqj.es.dto.PageDTO;
import com.hqj.es.dto.SearchDTO;
import com.hqj.es.service.EsService;
import com.hqj.web.es.form.SearchIdForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "搜索管理器", tags = "Search.Manager")
@RestController
@RequestMapping(value = "/")
public class SearchController {
    @Autowired
    private EsService esService;

    @ApiOperation("搜索")
    @RequestMapping(value = "/remote/search")
    public ResponseDTO<PageDTO> search(@RequestBody SearchDTO search) {
        PageDTO page = esService.searchByPage(search);
        return ResponseDTO.success(page);
    }

    @ApiOperation("通过Id查找")
    @RequestMapping(value = "/remote/getById")
    public ResponseDTO<Map<String, Object>> getById(@RequestBody SearchIdForm form) {
        Map<String, Object> map = esService.getById(form.getIndexName(), form.getId());
        return ResponseDTO.success(map);
    }

    @ApiOperation("通过Id批量查找")
    @RequestMapping(value = "/remote/getByIds")
    public ResponseDTO<List<Map<String, Object>>> getByIds(@RequestBody SearchIdForm form) {
        List<Map<String, Object>> mapList = esService.getByIds(form.getIndexName(), form.getIds());
        return ResponseDTO.success(mapList);
    }
}
