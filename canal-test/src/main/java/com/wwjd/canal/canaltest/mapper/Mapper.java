package com.wwjd.canal.canaltest.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据防问层
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 10:17:00
 * @Modified_By 阿导 2018/5/29 10:17
 */
@Repository
public interface Mapper {
	@Insert("${sql}")
	void doOption(@Param("sql") String sql);
}
