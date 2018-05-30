package com.wwjd.canal.canaltest.test;

import com.wwjd.starter.canal.client.abstracts.option.table.AlertTableOption;
import com.wwjd.starter.canal.client.abstracts.option.table.CreateTableOption;
import com.wwjd.starter.canal.client.abstracts.option.table.DropTableOption;
import com.wwjd.starter.canal.client.core.DealTableCanalEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 表结构操作
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 14:59:00
 * @Modified_By 阿导 2018/5/29 14:59
 */
@Component
public class MyTableEventListener extends DealTableCanalEventListener {
	
	@Autowired
	public MyTableEventListener(CreateTableOption createTableOption, DropTableOption dropTableOption, AlertTableOption alertTableOption) {
		super(createTableOption, dropTableOption, alertTableOption);
	}
}
