package com.wf.tutor.service;

import com.wf.tutor.common.LoggerBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.apache.commons.fileupload.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadFileService extends LoggerBase {
    public List<String> uploadFile(HttpServletRequest request, String path, String namePrefix) {
        List<String> filePathList = new ArrayList<>();
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        try {
            List<FileItem> fileItemList = servletFileUpload.parseRequest(request);
            int count = 0;
            for (FileItem fileItem : fileItemList) {
                if (!fileItem.isFormField()) {
                    String fileName = fileItem.getName();
                    File file = new File(path, namePrefix + "_" + count++ + fileName.substring(fileName.lastIndexOf(".")));
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    filePathList.add(file.getPath());
                    getLogger().info("路径:[{}]", file.getPath());
                    fileItem.write(file);
                }
            }
        } catch (FileUploadException e) {
            getLogger().error("解析图片错误{}", e);
        } catch (IOException e) {
            getLogger().error("获取图片流出错{}", e);
        } catch (Exception e) {
            getLogger().error("写图片出错{}", e);
        }
        return filePathList;
    }
}
