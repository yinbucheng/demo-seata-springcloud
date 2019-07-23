package com.sly.seata.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.sly.seata.account.mapper.AccountMapper;
import com.sly.seata.account.service.AccountService;
import com.sly.seata.common.model.account.Account;

import io.seata.core.context.RootContext;

/**
 * 账户service实现
 *
 * @author sly
 * @time 2019年6月12日
 */
@RestController
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 新增
     *
     * @param account
     * @return
     * @author sly
     * @time 2019年6月12日
     */
    @Override
    @SuppressWarnings("all")
    public Map<String, Object> insert(Account account) {
        log.info("=========xid:" + RootContext.getXID());
        accountMapper.insert(account);
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 200);
        result.put("message", "新增成功！");
        return result;
    }


    @Transactional
    public Map<String, Object> decreaseAmount(String accountId, Integer amount) {
        log.info("=========xid:" + RootContext.getXID());
        if (ObjectUtils.isEmpty(accountId) || ObjectUtils.isEmpty(amount)) {
            throw new RuntimeException("非法参数");
        }
        int row = accountMapper.decrease(accountId, amount);
        if (row <= 0) {
            throw new RuntimeException("操作失败");
        }
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 200);
        result.put("message", "新增成功！");
        return result;
    }

}
