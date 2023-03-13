package com.wwjd.starter.canal.client.abstracts.option.content;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 更新数据
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 09:02:00
 * @Modified_By 阿导 2018/5/29 09:02
 */

public abstract class UpdateOption extends AbstractDBOption {


    /**
     * 设置更新属性
     *
     * @param
     * @return
     * @author 阿导
     * @time 2018/5/29 09:27
     * @CopyRight 万物皆导
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.UPDATE;
    }

}
