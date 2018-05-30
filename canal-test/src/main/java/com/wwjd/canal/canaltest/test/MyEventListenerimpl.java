package com.wwjd.canal.canaltest.test;

import com.wwjd.starter.canal.client.abstracts.option.content.DeleteOption;
import com.wwjd.starter.canal.client.abstracts.option.content.InsertOption;
import com.wwjd.starter.canal.client.abstracts.option.content.UpdateOption;
import com.wwjd.starter.canal.client.core.DealCanalEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 实现接口方式测试
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018/5/28 17:31
 * @Modified_By 阿导 2018/5/28 17:31
 */
@Component
public class MyEventListenerimpl extends DealCanalEventListener {
	
	@Autowired
	public MyEventListenerimpl(@Qualifier("realInsertOptoin") InsertOption insertOption, @Qualifier("realDeleteOption") DeleteOption deleteOption, @Qualifier("realUpdateOption") UpdateOption updateOption) {
		super(insertOption, deleteOption, updateOption);
	}
	
}
