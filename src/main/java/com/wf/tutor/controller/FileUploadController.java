package com.wf.tutor.controller;

import com.wf.tutor.common.ApiResult;
import com.wf.tutor.common.ErrorCodeEnum;
import com.wf.tutor.common.FilePathEnum;
import com.wf.tutor.common.LoggerBase;
import com.wf.tutor.service.UploadFileService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController extends LoggerBase {
    @Autowired
    UploadFileService uploadFileService;

    @PostMapping(value = "/upload")
    public ApiResult<List<String>> upload(HttpServletRequest request, @Param("userId") String userId) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return ApiResult.createError(ErrorCodeEnum.FAIL);
        }

        List<String> result = uploadFileService.uploadFile(request, "/" + userId + "/" + FilePathEnum.CARD.getValue(), "card");
        return ApiResult.createSuccess(result);
    }
}
