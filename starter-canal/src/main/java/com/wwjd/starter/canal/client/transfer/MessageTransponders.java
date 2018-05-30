package com.wwjd.starter.canal.client.transfer;

import com.wwjd.starter.canal.client.interfaces.TransponderFactory;

/**
 * 信息转换类
 *
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(万物皆导)
 * @created 2018/5/28 14:46
 * @Modified_By 阿导 2018/5/28 14:46
 */
public class MessageTransponders {
    
    /**
     * 返回默认的信息工厂
     *
     * @author 阿导
     * @time 2018/5/28 15:51
     * @CopyRight 万物皆导
     * @param
     * @return
     */
    public static TransponderFactory defaultMessageTransponder() {
        return new DefaultTransponderFactory();
    }

}
