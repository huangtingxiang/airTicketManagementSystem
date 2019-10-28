package com.xiang.airTicket.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    String uploadImage(MultipartFile file);

    String sha256(String content);

}
