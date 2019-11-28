package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.TransactionRecord;

public class TransactionRecordService {

    public static TransactionRecordService transactionRecordService;

    public static TransactionRecordService getInstance() {
        if (transactionRecordService == null) {
            transactionRecordService = new TransactionRecordService();
        }
        return transactionRecordService;
    }

    private BaseHttpService baseHttpService = BaseHttpService.getInstance();

    public void getAll(BaseHttpService.CallBack callBack) {
        baseHttpService.get("transactionRecord/", callBack, TransactionRecord[].class);
    }

}
