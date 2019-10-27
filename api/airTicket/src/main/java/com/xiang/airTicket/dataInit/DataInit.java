package com.xiang.airTicket.dataInit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public abstract class DataInit implements ApplicationListener<ApplicationReadyEvent> {

    public abstract void init();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        this.init();
    }
}
