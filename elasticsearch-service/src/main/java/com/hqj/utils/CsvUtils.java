package com.hqj.utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.collect.Lists;
import com.hqj.common.constant.Constant;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * csv工具
 */
public class CsvUtils {

    /**
     * 导出到csv
     *
     * @param out
     * @param headers
     * @param datas
     */
    public static void exportCsv(OutputStream out, List<String> headers, List<List<String>> datas) {
        exportCsv(out, null, null, headers, datas);
    }

    /**
     * 导出到csv
     *
     * @param out
     * @param split
     * @param charset
     * @param headers
     * @param datas
     */
    public static void exportCsv(OutputStream out, Character split, String charset, List<String> headers, List<List<String>> datas) {
        CsvWriter writer = null;
        try {
            writer = new CsvWriter(out, split == null ?  Constant.DOUHAO : split, Charset.forName(StringUtils.isEmpty(charset) ? Constant.DEFAULT_CHARSET : charset));
            writer.setForceQualifier(true);
            // 设置表头
            if (!CollectionUtils.isEmpty(headers)) {
                writer.writeRecord(headers.toArray(new String[headers.size()]));
            }
            // 遍历数据集合
            if (!CollectionUtils.isEmpty(datas)) {
                for(List<String> data : datas) {
                    String[] temp = new String[data.size()];
                    for(int i = 0; i < data.size(); i++) {
                        temp[i] = data.get(i);
                    }
                    writer.writeRecord(temp);
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 导入csv
     *
     * @param in
     */
    public static List<List<String>> importCsv(InputStream in) {
        return importCsv(in, null, null);
    }

    /**
     * 导入csv
     *
     * @param in
     * @param split
     * @param charset
     */
    public static List<List<String>> importCsv(InputStream in, Character split, String charset) {
        CsvReader reader = null;
        List<List<String>> datas = Lists.newArrayList();
        try {
            reader = new CsvReader(in, split == null ?  Constant.DOUHAO : split, Charset.forName(StringUtils.isEmpty(charset) ? Constant.DEFAULT_CHARSET : charset));
            // 跳过表头
            reader.readHeaders();
            while(reader.readRecord()) {
                datas.add(Lists.newArrayList(reader.getValues()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return datas;
    }
}
