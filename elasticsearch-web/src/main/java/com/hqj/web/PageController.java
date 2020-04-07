package com.hqj.web;

import com.hqj.es.enums.*;
import com.hqj.es.service.EsService;
import com.hqj.utils.TokenUitls;
import com.hqj.common.constant.Constant;
import com.hqj.es.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面
 */
@RequestMapping("/")
@Controller
public class PageController {
    private static final String HOME = "/page/home";
    private static final String LOG_LIST = "/page/log/list";
    private static final String USER_LIST = "/page/user/list";
    private static final String ES_LIST = "/page/es/list";
    private static final String ES_ADD = "/page/es/add";
    private static final String ES_EDIT = "/page/es/edit";
    private static final String ES_COPY = "/page/es/copy";
    private static final String READ_ME = "/page/readme/index";
    private static final String LOGIN = "/page/login";

    @Autowired
    private EsService esService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/")
    public String main() {
        return HOME;
    }

    @RequestMapping("/page/home.html")
    public String home() {
        return HOME;
    }

    @RequestMapping("/page/log.html")
    public String listLog() {
        return LOG_LIST;
    }

    @RequestMapping("/page/user.html")
    public String listUser() {
        return USER_LIST;
    }

    @RequestMapping("/page/list.html")
    public String list() {
        return ES_LIST;
    }
    
    @RequestMapping("/page/add.html")
    public String add(Model model) {
        model.addAttribute("_dataTypes", DataType.values());
        model.addAttribute("_analyzers", AnalyzerEnum.values());
        model.addAttribute("_syncTypes", SyncType.values());
        model.addAttribute("_syncAllRules", SyncAllRule.values());
        model.addAttribute("_syncIncrRules", SyncIncrRule.values());
        return ES_ADD;
    }

    @RequestMapping("/page/edit.html")
    public String edit(Model model, @RequestParam("id") Long id) {
        model.addAttribute("_dataTypes", DataType.values());
        model.addAttribute("_analyzers", AnalyzerEnum.values());
        model.addAttribute("_syncTypes", SyncType.values());
        model.addAttribute("_syncAllRules", SyncAllRule.values());
        model.addAttribute("_syncIncrRules", SyncIncrRule.values());
        model.addAttribute("_index", esService.getIndex(id));
        return ES_EDIT;
    }

    /**
     * 复制
     *
     * @param model
     * @param from
     * @return
     */
    @RequestMapping("/page/copy.html")
    public String copy(Model model, @RequestParam("from") Long from) {
        model.addAttribute("_dataTypes", DataType.values());
        model.addAttribute("_analyzers", AnalyzerEnum.values());
        model.addAttribute("_syncTypes", SyncType.values());
        model.addAttribute("_syncAllRules", SyncAllRule.values());
        model.addAttribute("_syncIncrRules", SyncIncrRule.values());
        model.addAttribute("_index", esService.getIndex(from));
        return ES_COPY;
    }

    @RequestMapping("/page/readme.html")
    public String readme() {
        return READ_ME;
    }

    @RequestMapping("/page/login.html")
    public String login() {
        return LOGIN;
    }

    /**
     * 登陆退出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page/loginout.html")
    public String loginout(HttpServletRequest request) {
        String token = TokenUitls.getToken(request);
        if(!StringUtils.isEmpty(token)) {
            redisTemplate.delete((Constant.USER_PREFIX + token));
        }
        return LOGIN;
    }
}
