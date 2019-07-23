package com.sly.seata.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sly.seata.common.web.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sly.seata.account.service.AccountService;
import com.sly.seata.business.service.BusinessService;
import com.sly.seata.common.model.account.Account;
import com.sly.seata.common.model.order.Order;
import com.sly.seata.common.model.storage.Storage;
import com.sly.seata.common.utils.CommonUtils;
import com.sly.seata.common.utils.DateUtils;
import com.sly.seata.order.service.OrderService;
import com.sly.seata.storage.service.StorageService;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * 业务service实现
 *
 * @author sly
 * @time 2019年6月12日
 */
@RestController
@Slf4j
public class BusinessServiceImpl implements BusinessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StorageService storageService;

    /**
     * 付款
     *
     * @param accountId
     * @param orderId
     * @param storageId
     * @return
     * @author sly
     * @time 2019年6月12日
     */
    @GlobalTransactional
    @Override
    public Map<String, Object> purchase(@RequestParam("accountId") String accountId,
                                        @RequestParam("orderId") String orderId, @RequestParam("storageId") String storageId, @RequestParam("amount") Integer amount) {
        try {
            System.out.println("accountId:" + accountId);
            System.out.println("orderId" + orderId);
            System.out.println("storageId" + storageId);

            Storage storage = new Storage();
            storage.setStorageId(storageId);
            storage.setStorageName("name");
            storage.setStorageCount(20);
            storage.setRemark("备注");
            storage.setLogicDel("N");
            Order order = new Order();
            order.setOrderId(orderId);
            order.setPrice(amount);
            order.setOrderNo("NO" + System.currentTimeMillis());
            order.setOrderDetail("详情");
            order.setCreateTime(DateUtils.formateTime(new Date()));
            order.setRemark("备注");
            order.setLogicDel("N");

            log.info("============xid" + RootContext.getXID());
            System.out.println("---------------Thread Main:"+Thread.currentThread());
            Map<String, Object> insert = storageService.insert(storage);
            if ((int) insert.get("status") != 200) {
                throw new RuntimeException((String) insert.get("message"));
            }
            Map<String, Object> insert2 = orderService.insert(order);
            if ((int) insert2.get("status") != 200) {
                throw new RuntimeException((String) insert2.get("message"));
            }
            //扣取用户金额
            Map<String, Object> insert3 = accountService.decreaseAmount(accountId, amount);
            if ((int) insert3.get("status") != 200) {
                throw new RuntimeException((String) insert3.get("message"));
            }

            Map<String, Object> result = new HashMap<>(16);
            result.put("status", 200);
            result.put("message", "付款成功！");
            return result;
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

}
