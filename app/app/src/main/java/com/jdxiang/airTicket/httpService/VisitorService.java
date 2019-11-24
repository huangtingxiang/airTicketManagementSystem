package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.Visitor;

public class VisitorService {

    BaseHttpService httpService = new BaseHttpService();

    /**
     * 获取所有城市
     *
     * @param callBack
     */
    public void getCurrentVisitor(BaseHttpService.CallBack callBack) {
        httpService.get("visitor/currentVisitor", callBack, Visitor.class);
    }

}
