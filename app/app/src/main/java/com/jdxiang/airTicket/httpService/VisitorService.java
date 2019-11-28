package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.Visitor;

public class VisitorService {


    static VisitorService visitorService;

    public static VisitorService getInstance() {
        if (visitorService == null) {
            visitorService = new VisitorService();
        }
        return visitorService;
    }

    BaseHttpService httpService = new BaseHttpService();

    /**
     * 获取所有城市
     *
     * @param callBack
     */
    public void getCurrentVisitor(BaseHttpService.CallBack callBack) {
        httpService.get("visitor/currentVisitor", callBack, Visitor.class);
    }

    /**
     * 充值
     *
     * @param price
     * @param callBack
     */
    public void recharge(Double price, BaseHttpService.CallBack callBack) {
        httpService.put("visitor/recharge", price, callBack, null);
    }

    /**
     * 修改姓名
     *
     * @param id
     * @param name
     * @param callBack
     */
    public void changeName(Long id, String name, BaseHttpService.CallBack callBack) {
        httpService.put("visitor/changeName/" + id, name, callBack, null);
    }

    /**
     * 修改身份证
     *
     * @param id
     * @param idCard
     * @param callBack
     */
    public void changeIdCard(Long id, String idCard, BaseHttpService.CallBack callBack) {
        httpService.put("visitor/changeIdCard/" + id, idCard, callBack, null);
    }

    /**
     * 修改手机号
     *
     * @param id
     * @param phoneNumber
     * @param callBack
     */
    public void changePhoneNumber(Long id, String phoneNumber, BaseHttpService.CallBack callBack) {
        httpService.put("visitor/changePhone/" + id, phoneNumber, callBack, null);
    }

}
