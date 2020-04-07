package com.hqj.web.es.controller;

import com.google.common.collect.Lists;
import com.hqj.common.dto.ResponseDTO;
import com.hqj.es.client.EsClient;
import com.hqj.es.dto.IndexMappingDTO;
import com.hqj.es.dto.PageDTO;
import com.hqj.es.dto.SearchDTO;
import com.hqj.es.dto.TestDTO;
import com.hqj.es.service.EsService;
import com.hqj.job.JobService;
import com.hqj.web.es.form.TestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * es页面
 */
@RequestMapping("/")
@RestController
public class TestController {
    @Autowired
    private JobService jobService;
    @Autowired
    private EsService esService;
    @Autowired
    private EsClient esClient;

    @RequestMapping("/test")
    public void test() {
        jobService.fiveMinusJob();
    }

    @GetMapping("/test/getTestDatas")
    public ResponseDTO getTestDatas(TestForm form) {
        List<TestDTO> tests = new ArrayList<>();
        TestDTO test1 = new TestDTO();
        test1.setId(1L);
        test1.setName("test1");
        tests.add(test1);
        TestDTO test2 = new TestDTO();
        test2.setId(2L);
        test2.setName("test2");
        tests.add(test2);
        TestDTO test3 = new TestDTO();
        test3.setId(3L);
        test3.setName("test3");
        tests.add(test3);
        return ResponseDTO.success(tests);
    }

    @RequestMapping(value = "/test/search")
    public ResponseDTO search(@RequestBody SearchDTO query) {
        PageDTO page = esService.searchByPage(query);
        return ResponseDTO.success(page);
    }

    @RequestMapping(value = "/test/mapping")
    public ResponseDTO testMapping() {
        List<IndexMappingDTO> mappings = Lists.newArrayList();
        IndexMappingDTO mapping = new IndexMappingDTO();
        mapping.setName("id");
        mappings.add(mapping);
        IndexMappingDTO mapping1 = new IndexMappingDTO();
        mapping1.setName("name");
        mappings.add(mapping1);
        return ResponseDTO.success(mappings);
    }

    @RequestMapping(value = "/test/indexExists")
    public ResponseDTO testMapping(@RequestParam("indexName") String indexName) {
        boolean flag = esClient.indexExist(indexName);
        return ResponseDTO.success(flag);
    }
}
