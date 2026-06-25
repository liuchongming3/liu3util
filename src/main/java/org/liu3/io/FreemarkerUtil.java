package org.liu3.io;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.Map;

/**
 * 我是用在用html或者word_xml模板,变量用${变量名}的形式,
 * 使用freemarker替换变量的形式生成文件
 * 可以使用freemarker的if/else,list等标签进行逻辑操作
 * @Author: liutianshuo
 * @Date: 2026/1/23
 */
public class FreemarkerUtil {


    /**
     * 把字符串里的${变量名}用Map中对应的key的值替换
     * @param tempContent
     * @param params
     * @return
     */
    public static String format(String tempContent, Map<String, Object> params) {
        Configuration cfg = new Configuration();
        StringTemplateLoader stl = new StringTemplateLoader();
        stl.putTemplate("templateKey", tempContent);
        cfg.setTemplateLoader(stl);
        try (StringWriter writer = new StringWriter()) {
            cfg.getTemplate("templateKey").process(params, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("模板填充数据失败", e);
        }
    }
}
