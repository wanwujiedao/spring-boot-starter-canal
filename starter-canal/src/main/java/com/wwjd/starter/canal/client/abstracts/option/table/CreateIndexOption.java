package com.wwjd.starter.canal.client.abstracts.option.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 創建索引操作
 *
 * @author 阿导
 * @CopyRight 青团社
 * @created 2018年05月30日 17:16:00
 * @Modified_By 阿导 2018/5/30 17:16
 */
public abstract class CreateIndexOption extends AbstractDBOption {
    /**
     * 創建索引操作
     *
     * @author 阿导
     * @CopyRight 萬物皆導
     * @created 2018/5/29 09:21
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.CINDEX;
    }
}
