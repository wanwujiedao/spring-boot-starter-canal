package com.wwjd.starter.canal.client.abstracts.option.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 创建表操作
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 13:41:00
 * @Modified_By 阿导 2018/5/29 13:41
 */
public abstract class CreateTableOption extends AbstractDBOption {

    /**
     * 创建表操作
     *
     * @author 阿导
     * @CopyRight 萬物皆導
     * @created 2018/5/29 09:21
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.CREATE;
    }
}
