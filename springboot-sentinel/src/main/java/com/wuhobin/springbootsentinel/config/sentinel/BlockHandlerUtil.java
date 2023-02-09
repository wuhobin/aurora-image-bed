package com.wuhobin.springbootsentinel.config.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuhongbin
 * @description: 限流处理
 * @datetime 2023/02/09 16:54
 */
@Slf4j
public class BlockHandlerUtil {

    /**
     * blockHandler  blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项。blockHandler 函数访问范围需要是 public，
     * 返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。blockHandler 函数默认需要和原方法在同一个类中。
     * 若希望使用其他类的函数，则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     */

    /**
     *
     * @param e BlockException
     * @return
     */
    public static CommonResult testSentinel(BlockException e){
        log.info("触发sentinel限流",e);
        return CommonResult.failed(ResultCode.TOUCH_LIMIT);
    }


}
