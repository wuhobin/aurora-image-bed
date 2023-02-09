package com.wuhobin.springbootsentinel.config.sentinel;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuhongbin
 * @date 2022/11/09 11:48
 **/
@Data
public class SentinelRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流量规则
     */
    private List<FlowRule> flowRules;

    /**
     * 熔断规则
     */
    private List<DegradeRule> degradeRules;

}