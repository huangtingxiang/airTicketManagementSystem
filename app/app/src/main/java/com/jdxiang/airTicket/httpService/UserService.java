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

    public void resetPassWord(Long id, String password, BaseHttpService.CallBack callBack) {
        User user = new User();
        user.setPassWord(password);
        httpService.put("user/resetPassword/" + id, user, callBack, null);
    }

    public void resetUserName(Long id, String username, BaseHttpService.CallBack callBack) {
        httpService.put("user/resetUserName/" + id, username, callBack, null);
    }
}
