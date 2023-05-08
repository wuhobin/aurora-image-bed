package com.wuhobin.common.util;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.Arrays;

/**
 * @author wuhongbin
 * @description: excel导出样式
 * @datetime 2023/04/20 18:01
 */
@SuppressWarnings("all")
public class ExcelStyleUtil {

    private static HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy();

    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy(){
        horizontalCellStyleStrategy.setHeadWriteCellStyle(getHeadStyle());
        horizontalCellStyleStrategy.setContentWriteCellStyleList(Arrays.asList(getContentStyle()));
        return horizontalCellStyleStrategy;
    }


    public static WriteCellStyle getHeadStyle(){
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("Arial");//设置字体名字
        headWriteFont.setFontHeightInPoints((short)10);//设置字体大小
        headWriteFont.setBold(true);//字体加粗
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont); //在样式用应用设置的字体;

        // 样式
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);//设置底边框;
        headWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置底边框颜色;
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);  //设置左边框;
        headWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置左边框颜色;
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);//设置右边框;
        headWriteCellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置右边框颜色;
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);//设置顶边框;
        headWriteCellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex()); //设置顶边框颜色;

        headWriteCellStyle.setWrapped(true);  //设置自动换行;

        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);//设置水平对齐的样式为居中对齐;
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐的样式为居中对齐;
        return headWriteCellStyle;
    }


    public static WriteCellStyle getContentStyle(){
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

        // 背景绿色
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        // 设置字体
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 10);//设置字体大小
        contentWriteFont.setFontName("Arial"); //设置字体名字
        contentWriteFont.setColor(IndexedColors.BLACK.getIndex());
        contentWriteCellStyle.setWriteFont(contentWriteFont);//在样式用应用设置的字体;

        //设置样式;
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);//设置底边框;
        contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置底边框颜色;
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);  //设置左边框;
        contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置左边框颜色;
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);//设置右边框;
        contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置右边框颜色;
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);//设置顶边框;
        contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex()); ///设置顶边框颜色;
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);// 水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        contentWriteCellStyle.setWrapped(true); //设置自动换行;
        return contentWriteCellStyle;
    }




}
