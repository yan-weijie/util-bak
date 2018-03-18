package com.zdlh.file;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Created by Administrator on 2018/1/25.
 */
public class Dom4jXml {

    /**
     * 返回init.xml文件节点value
     * @param key
     * @return
     */
    public static String getValue(String key) {
        Map<String,String> init = Dom4jXml.parserXml("/data/init.xml");
        return init.get(key);
    }

    /**
     * 返回database.xml文件节点value
     * @param key
     * @return
     */
    public static String getDataBaseValue(String key) {
        Map<String, String> init = Dom4jXml.parserXml("/data/database.xml");
        return init.get(key);
    }

    /**
     * 返回user.xml文件节点value
     * @param key
     * @return
     */
    public static String getUserValue(String key) {
        Map<String, String> init = Dom4jXml.parserXml("/data/user.xml");
        return init.get(key);
    }

    /**
     * 读取xml文件
     * @param regInfoPath string 文件路径
     * @return
     */
    public static Map<String, String> parserXml(String regInfoPath) {
        Map<String, String>  temp = new LinkedHashMap<String, String>();
        String path = System.getProperty("user.dir");

        path += regInfoPath;
        File inputXml = new File(path);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(inputXml); //把未见读入到文档
            Element employees = document.getRootElement();  //获取文档根节点
            for(Iterator<?> i = employees.elementIterator(); i.hasNext();) {
                Element employee = (Element) i.next();
                for(Iterator<?> j = employee.elementIterator(); j.hasNext();) {
                    Element node = (Element) j.next();
                    String key = node.getName();
                    String value = node.getText();
                    temp.put(key, value);
                }
            }

        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

}
