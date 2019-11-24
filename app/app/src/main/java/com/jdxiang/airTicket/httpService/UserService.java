package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.User;

public class UserService {

    public static final String tokenHeader = "Authorization";

    BaseHttpService httpService = new BaseHttpService();

    /**
     * 注册旅客
     *
     * @param callBack
     * @param user
     */
    public void register(BaseHttpService.CallBack callBack, User user) {
        httpService.post("user/register", user, callBack, User.class);
    }

    /**
     * 旅客登陆
     *
     * @param callBack
     * @param user
     */
    public void login(BaseHttpService.CallBack callBack, User user) {
        httpService.post("user/loginByToken", user, callBack, User.class);
    }
}
