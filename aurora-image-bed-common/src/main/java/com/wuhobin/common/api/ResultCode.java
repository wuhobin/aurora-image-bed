package com.wuhobin.common.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author 刘博
 */
public enum ResultCode implements IErrorCode {

    /**
     * 返回信息
     */
    SUCCESS(200, "操作成功"),
    FAILED(-500, "系统异常"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "未登录"),
    DATA_IS_NOT(-402,"数据不存在"),
    FORBIDDEN(403, "无权限"),
    ACCOUNT_ERR(405,"账号密码错误"),
    SERVER_ERROR(-503, "服务器异常"),
    PLEASE_TRY_AGAIN_LATER(-600, "请稍后重试"),

    TASK_LIST_ERR(-5001, "获取任务列表异常"),
    PLEASE_TRY_LATER(-5002, "还未到限制发送时间，稍后再试"),
    TASK_NOT_EXIST(-5003, "任务不存在"),
    TASK_FINISH(-4000,"任务已完成"),
    TASK_AWARD(-4001, "任务异常"),
    TO_FINISH_ERR(-4008, "去完成任务接口异常"),
    CHECK_TASK_INVALID(-6036, "校验任务未通过"),
    SEND_VERIFY_ERR(-5004, "验证码错误"),
    LOTTERY_ERR(-4006, "抽奖异常"),
    TWISTED_EGG_MONEY_NOT_ENOUGH(-4007, "扭蛋币数量不足"),
    AWARD_RECORD_ERR(-4008, "获取抽奖记录出错"),
    EGG_SHELL_NOT_ENOUGH(-4009,"蛋壳数量不足"),
    USER_NOT_FOLLOW(-4010,"用户未关注该幸福号"),
    USER_NOT_SIGN_CONTRACT(-4011,"用户未签约"),
    //-----------------------后台错误返回码------------------------



    //-----------------------移动端错误返回码------------------------
    UN_REAL(-2003, "未实名"),
    UN_REGISTER(-2004, "未注册"),
    LOGIN_FAILURE(-2001, "登陆失败"),
    TOUCH_LIMIT(-2002, "触发限流器"),
    TOUCH_FALL(-2003, "触发熔断"),
    USER_NOT_AUTH(-4012, "用户一网通"),
    USER_NOT_REAL(-4013, "用户未实名"),

    USER_CUS_NO_NOT(-2005,"用户渠道客户码为空"),

    USER_NOT_FOUR(-6001,"用户还未达到连续签到四天"),
    USER_NOT_SEVEN(-6002,"用户还未达到连续签到七天"),
    USER_RECEIVE_FOR_DAY(-6003,"用户已领取连续签到四天奖励"),
    USER_RECEIVE_SEVEN_DAY(-6004,"用户已领取连续签到七天奖励"),


    //-----------------------移动端完成答题任务错误返回码------------------------
    NOT_ANSWER_ALL(-7001,"还有题目未作答哦~"),
    NOT_ANSWER_TRUE(-7002,"有题目未全部答对哦~"),
    DO_ANSWER_COMPLETE(200,"任务已完成，快去领取奖励吧~")

    ;
    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
