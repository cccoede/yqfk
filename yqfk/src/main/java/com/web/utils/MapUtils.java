package com.web.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Map工具类
 *
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * request.getParameterMap()获取到的map转换为string=string的map 支持一对一的form表单提交的数据
     * 比如select text radio等 不支持checkbox 该方法主要是把查询条件封装在map里面，方便查询
     *
     * @param map <String, String[]>
     * @return Map<String, String>
     */
    public static Map<String, Object> changeMap(Map<String, String[]> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<Entry<String, String[]>> set = map.entrySet();
        Iterator<Entry<String, String[]>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();
            if (entry.getValue().length == 1 ) {
                // 值为空的数据不放入map中
                if (null != entry.getValue()[0] && !"".equals(entry.getValue()[0])) {
                    if ("sort".equals(entry.getKey()) || "order".equals(entry.getKey())) {
                        // 过滤sort order字段的sql注入
                        resultMap.put(entry.getKey(), StringEscapeUtils.escapeSql(entry.getValue()[0]));
                    } else {
                        resultMap.put(entry.getKey(), entry.getValue()[0].trim());
                    }

                }
            }
        }
        return resultMap;
    }

    /**
     * Map包含指定key,且value的值不为null和“”
     * @param map
     * @param key
     * @return
     */
    public static boolean isNotBlank(Map<String, Object> map, String key) {
        if (map.containsKey(key) && map.get( key )!=null && StringUtils.isNotBlank( map.get( key ).toString() )) {
            return true;
        }
        return false;
    }


    public static String isNotNull(Map<String, Object> condition, String str) {
        if (condition.get(str) == null || "null".equals(condition.get(str))) {
            return "";
        } else {
            return (String) condition.get(str);
        }
    }
}
