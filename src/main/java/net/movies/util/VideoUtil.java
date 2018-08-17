package net.movies.util;

import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class VideoUtil {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_VIDEO_PATH = "C:\\Users\\Erik\\IdeaProjects\\spring-boot\\MoviesWebsite\\src\\main\\resources\\static\\videos";
    private static final String [] VIDEO_FORMATS = {"video/mp4"};

    public boolean checkVideoFormat(String format){
        System.out.println("video format : " + format);
        for (String videoFormat : VIDEO_FORMATS) {
            if(videoFormat.equals(format)){
                return true;
            }
        }
        return false;
    }

    public void save(String name, MultipartFile file){
        File videoFile = new File(DEFAULT_VIDEO_PATH, name);
        try {
            @Cleanup FileOutputStream out = new FileOutputStream(videoFile);
            out.write(file.getBytes());
        } catch (IOException e) {
            if (videoFile.exists()){
                boolean result = videoFile.delete();
                LOGGER.debug("file '{}' delete : {}",name,result);
            }
            throw new RuntimeException("file '" + name+ "' failed save",e);
        }
        LOGGER.debug("video '{}' saved",name);
    }

    public void delete(String name){
        File file = new File(DEFAULT_VIDEO_PATH, name);
        if(file.exists()){
            boolean result = file.delete();
            LOGGER.debug("file '{}' delete : {}",name,result);
        }
    }
}
