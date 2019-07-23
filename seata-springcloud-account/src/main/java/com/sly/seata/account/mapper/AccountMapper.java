package com.sly.seata.account.mapper;

import com.sly.seata.common.model.account.Account;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户mapper
 * 
 * @author sly
 * @time 2019年6月12日
 */
public interface AccountMapper {

	/**
	 * 新增
	 * 
	 * @param account
	 * @return
	 * @author sly
	 * @time 2019年6月12日
	 */
	int insert(Account account);

	//扣除用户账号上面的余额
	int decrease(@RequestParam("accountId") String accountId,@RequestParam("amount") int amount);

}
