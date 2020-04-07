package com.hqj.utils;

import com.hqj.es.enums.AnalyzerEnum;
import com.hqj.es.enums.DataType;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * html工具类
 */
public class HtmlUtils {
    /**
     * 生曾呢映射
     *
     * @param datas
     * @return
     */
    public static String generalMapping(List<List<String>> datas) {
        StringBuilder sb = new StringBuilder();
        if(!CollectionUtils.isEmpty(datas)) {
            for(List<String> data : datas) {
                Integer anayzed = DataType.getDataTypeAnayzed(data.get(1));
                boolean anayzedFlag = anayzed == 1 ? true : false;
                boolean indexedFlag = Objects.equals(data.get(2), "1") ? true : false;
                sb.append("<div class=\"property_item gap_top_2\">\n");
                sb.append("<div class=\"group_item\">\n");
                sb.append("<label class=\"input_label\">字段名称<span>*</span></label><input type=\"text\" class=\"small-input\" name=\"propertyName\" value=\"" + data.get(0) + "\">\n");
                sb.append("</div>\n");
                sb.append("<div class=\"group_item gap_left_4 propertyType\">\n");
                sb.append("<label class=\"input_label\">字段类型<span>*</span></label><select class=\"small-input select\" name=\"propertyType\" indexed=\"" + data.get(2) + "\" onchange=\"javascript:$(this).parent().siblings('.indexed').find('input[name=indexed]').attr('anayzed', $(this).find('option:selected').attr('anayzed'));($(this).find('option:selected').attr('anayzed')==1 && $(this).attr('indexed')==1)?($(this).parent().siblings('.analyzer').removeClass('hide')):($(this).parent().siblings('.analyzer').addClass('hide'));\">\n");
                for(DataType dt : DataType.values()) {
                    sb.append("<option value=\"" + dt.getValue() + "\" anayzed=" + dt.getAnayzed() + " " + (Objects.equals(dt.getValue(), data.get(1)) ? "selected='selected'" : "") + ">" + dt.getValue() + "</option>\n");
                }
                sb.append("</select>\n");
                sb.append("</div>\n");
                sb.append("<div class=\"group_item indexed\">\n");
                sb.append("<div class=\"group_item\">\n");
                sb.append("<input type=\"checkbox\" class=\"normal-checkbox\" name=\"indexed\" anayzed=\"" + anayzed + "\" " + (indexedFlag ? "checked" : "") + " onclick=\"javascript:$(this).parents('.indexed').siblings('.propertyType').find('select').attr('indexed', $(this).prop('checked')?1:0);($(this).prop('checked') && $(this).attr('anayzed')==1)?($(this).parents('.indexed').siblings('.analyzer').removeClass('hide')):($(this).parents('.indexed').siblings('.analyzer').addClass('hide'));\"><label class=\"checkout_label\">是否索引</label>\n");
                sb.append("</div>\n");
                sb.append("</div>\n");
                sb.append("<div class=\"group_item\">\n");
                sb.append("<span class=\"del_property_item gap_left_2\" onclick=\"javascript: $(this).parents('.property_item').remove();\">\n");
                sb.append("<img src=\"/static/image/del.png\">\n");
                sb.append("</span>\n");
                sb.append("</div>\n");
                sb.append("<div class=\"gap_top_2 analyzer " + (!(anayzedFlag && indexedFlag) ? "hide" : "") + "\">\n");
                sb.append("<div class=\"group_item\">\n");
                sb.append("<label class=\"input_label\">索引分词<span>*</span></label><select class=\"small-input select\" name=\"analyzer\">\n");
                for(AnalyzerEnum a : AnalyzerEnum.values()) {
                    sb.append("<option value=\"" + a.getValue() + "\" " + (Objects.equals(a.getValue(), data.get(3)) ? "selected='selected'" : "") + ">" + a.getValue() + "</option>\n");
                }
                sb.append("</select>\n");
                sb.append("</div>\n");
                sb.append("<div class=\"group_item gap_left_4\">\n");
                sb.append("<label class=\"input_label\">搜索分词<span>*</span></label><select class=\"small-input select\" name=\"searchAnalyzer\">\n");
                for(AnalyzerEnum a : AnalyzerEnum.values()) {
                    sb.append("<option value=\"" + a.getValue() + "\" " + (Objects.equals(a.getValue(), data.get(4)) ? "selected='selected'" : "")  + ">" + a.getValue() + "</option>\n");
                }
                sb.append("</select>\n");
                sb.append("</div>\n");
                sb.append("</div>\n");
                sb.append("</div>\n");
            }
        }
        return sb.toString();
    }
}
