package com.wuhobin.common.util.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shixiaohao
 * @Date 2020/10/28 18:18
 * @Description: CustomHandler
 */
@Component
public class CustomHandler extends AbstractColumnWidthStyleStrategy {
    private static final int MAX_COLUMN_WIDTH = 6000;
    private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);

    public CustomHandler() {
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer integer, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            // 所有是head的cell设置固定的宽度 MAX_COLUMN_WIDTH
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), MAX_COLUMN_WIDTH);
            // 设置默认行高
            // writeSheetHolder.getCachedSheet().setDefaultRowHeightInPoints();
        }
    }

}

