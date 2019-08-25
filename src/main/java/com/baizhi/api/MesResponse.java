package com.baizhi.api;

import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.Map;

public class MesResponse {
    /**
     * @param code
     * @param mes
     * @param data
     * @return
     */
    public Map<String, Object> setResult(Integer code, String mes, Object data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(CustomConstant.HTTP_RES_CODE_NAME, code);
        map.put(CustomConstant.HTTP_RES_MES_NAME, mes);
        map.put(CustomConstant.HTTP_RES_DATA_NAME, data);
        return map;
    }


    /**
     * 执行代码 200 500  mes  结果信息  data  传递数据
     *
     * @param data
     * @return
     */
    public Map<String, Object> setResultSuceessAndData(Object data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(CustomConstant.HTTP_RES_CODE_NAME, CustomConstant.HTTP_RES_CODE_VALUE_200);
        map.put(CustomConstant.HTTP_RES_MES_NAME, CustomConstant.HTTP_RES_MES_VALUE_SUCCESS);
        map.put(CustomConstant.HTTP_RES_DATA_NAME, data);
        return map;
    }


    /**
     * 执行代码 200 500  mes  结果信息  data  传递数据
     *
     * @return
     */
    public Map<String, Object> setResultSuceess() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(CustomConstant.HTTP_RES_CODE_NAME, CustomConstant.HTTP_RES_CODE_VALUE_200);
        map.put(CustomConstant.HTTP_RES_MES_NAME, CustomConstant.HTTP_RES_MES_VALUE_SUCCESS);
        map.put(CustomConstant.HTTP_RES_DATA_NAME, null);
        return map;
    }

    /**
     * 执行代码 200 500  mes  结果信息  data  传递数据
     *
     * @return
     */
    public Map<String, Object> setResultError() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(CustomConstant.HTTP_RES_CODE_NAME, CustomConstant.HTTP_RES_CODE_VALUE_500);
        map.put(CustomConstant.HTTP_RES_MES_NAME, CustomConstant.HTTP_RES_MES_VALUE_ERROR);
        map.put(CustomConstant.HTTP_RES_DATA_NAME, null);
        return map;
    }

    /**
     * {"rows":[当前页结果(list)],"page":"当前页","total":"总页数","records":"总条数" count}
     *
     * @param page
     * @param count
     * @param rows
     * @param lists
     * @return
     */
    public Map<String, Object> setJqgridMap(Integer page,
                                            Integer count,
                                            Integer rows,
                                            Object lists) {
        HashMap<String, Object> map = new HashMap<>();
        Integer total = null;
        if (count % rows == 0) {
            total = count / rows;
        } else {
            total = count / rows + 1;
        }
        map.put(CustomConstant.HTTP_RES_JQGRID_TOTAL, total);
        map.put(CustomConstant.HTTP_RES_JQGRID_RECORDS, count);
        map.put(CustomConstant.HTTP_RES_JQGRID_ROWS, lists);
        map.put(CustomConstant.HTTP_RES_JQGRID_PAGE, page);
        return map;
    }

    /**
     * 创建rowbound  page 当前页  size 每页展示数量
     *
     * @param page
     * @param size
     * @return
     */
    public RowBounds setRowBound(Integer page, Integer size) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        return rowBounds;
    }

    ;

}
