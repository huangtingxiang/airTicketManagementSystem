package com.jdxiang.airTicket.httpService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommonService {
    static CommonService commonService;

    BaseHttpService baseHttpService = BaseHttpService.getInstance();

    public static CommonService getInstance() {
        if (commonService == null) {
            commonService = new CommonService();
        }
        return commonService;
    }

    public void upload(RequestBody data, BaseHttpService.CallBack callBack) {
        baseHttpService.putByForm("common/upload", data, callBack, String.class);
    }

}
