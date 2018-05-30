package com.wwjd.canal.canaltest.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.content.DeleteOption;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 需要自己实现的删除处理机制
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 09:48:00
 * @Modified_By 阿导 2018/5/29 09:48
 */
@Component
public class RealDeleteOption extends DeleteOption {
	
	/**
	 * 删除操作操作
	 *
	 * @param destination 指令
	 * @param schemaName  实例名称
	 * @param tableName   表名称
	 * @param rowChange   数据
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 08:59
	 * @CopyRight 万物皆导
	 */
	@Override
	public void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
		System.out.println("======================接口方式（删除数据操作）==========================");
		List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
		for (CanalEntry.RowData rowData : rowDatasList) {
			if (!CollectionUtils.isEmpty(rowData.getBeforeColumnsList())) {
				String sql = "use " + schemaName + ";\n";
				
				sql += "DELETE FROM " + tableName + " WHERE ";
				StringBuffer idKey = new StringBuffer();
				StringBuffer idValue = new StringBuffer();
				for (CanalEntry.Column c : rowData.getBeforeColumnsList()) {
					if (c.getIsKey()) {
						idKey.append(c.getName());
						idValue.append(c.getValue());
						break;
					}
					
					
				}
				
				sql += idKey + " =" + idValue + ";";
				
				System.out.println(sql);
			}
			System.out.println("\n======================================================");
			
		}
	}
}
