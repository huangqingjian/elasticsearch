package com.hqj.utils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.springframework.util.ObjectUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * sql 工具
 */
public class SqlUtils {

    /**
     * 解析查询sql
     *
     * @param sql
     * @return
     */
    public static List<String> parseSelectSQL(String sql) {
        List<String> result = new ArrayList<String>();
        try {
            CCJSqlParserManager pm = new CCJSqlParserManager();
            Statement statement = pm.parse(new StringReader(sql));
            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;
                PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();
                List<SelectItem> selectItemlist = selectBody.getSelectItems();
                SelectItem selectItem = null;
                SelectExpressionItem selectExpressionItem = null;
                AllTableColumns allTableColumns = null;
                Alias alias = null;
                SimpleNode node = null;
                if (selectItemlist != null) {
                    for (int i = 0; i < selectItemlist.size(); i++) {
                        selectItem = selectItemlist.get(i);
                        if (selectItem instanceof SelectExpressionItem) {
                            selectExpressionItem = (SelectExpressionItem) selectItemlist
                                    .get(i);
                            alias = selectExpressionItem.getAlias();
                            node = selectExpressionItem.getExpression()
                                    .getASTNode();
                            Object value = node.jjtGetValue();
                            String columnName = "";
                            if (value instanceof Column) {
                                columnName = ((Column) value).getColumnName();
                            } else if (value instanceof Function) {
                                columnName = ((Function) value).toString();
                            } else {
                                // 增加对select 'aaa' from table; 的支持
                                columnName = ObjectUtils.identityToString(value);
                                columnName = columnName.replace("'", "");
                                columnName = columnName.replace("\"", "");
                            }
                            if (alias != null) {
                                columnName = alias.getName();
                            }
                            result.add(columnName);
                        } else if (selectItem instanceof AllTableColumns) {
                            allTableColumns = (AllTableColumns) selectItemlist
                                    .get(i);
                            result.add(allTableColumns.toString());
                        } else {
                            result.add(selectItem.toString());
                        }

                    }
                }
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return result;
    }

}
