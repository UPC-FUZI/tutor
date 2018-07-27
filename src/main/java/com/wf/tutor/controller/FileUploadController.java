package com.wf.tutor.controller;

import com.wf.tutor.common.LoggerBase;
import com.wf.tutor.service.UploadFileService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController extends LoggerBase {
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    @Autowired
    UploadFileService uploadFileService;

    @PostMapping(value = "/upload")
    public String upload(HttpServletRequest request) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return "失败";
        }

        uploadFileService.uploadFile(request, PROJECT_PATH + "/pic", "card");
        return "成功";
    }
}
