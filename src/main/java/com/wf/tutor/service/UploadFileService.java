package com.wf.tutor.service;

import com.wf.tutor.common.LoggerBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.apache.commons.fileupload.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadFileService extends LoggerBase {
    private static final String PROJECT_PATH = System.getProperty("user.dir") + "/ROOT";

    public List<String> uploadFile(HttpServletRequest request, String path, String namePrefix) {
        //拼接目标文件夹地址
        String targetPath = PROJECT_PATH + path;
        List<String> filePathList = new ArrayList<>();
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        try {
            List<FileItem> fileItemList = servletFileUpload.parseRequest(request);
            int count = 0;
            for (FileItem fileItem : fileItemList) {
                if (!fileItem.isFormField()) {
                    String fileName = fileItem.getName();
                    File file = new File(targetPath, namePrefix + "_" + count++ + fileName.substring(fileName.lastIndexOf(".")));
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    filePathList.add(interceptPath(file.getPath()));
                    getLogger().info("路径:[{}]", file.getPath());
                    fileItem.write(file);
                }
            }
        } catch (FileUploadException e) {
            getLogger().error("解析图片错误{}", e);
            return new ArrayList<>();
        } catch (IOException e) {
            getLogger().error("获取图片流出错{}", e);
            return new ArrayList<>();
        } catch (Exception e) {
            getLogger().error("写图片出错{}", e);
            return new ArrayList<>();
        }
        return filePathList;
    }
    private String interceptPath(String path){
        int index = path.indexOf("/ROOT");
        return path.substring(index,path.length()-1);
    }
}
