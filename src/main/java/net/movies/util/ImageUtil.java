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
public class ImageUtil {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_IMAGE_PATH = "C:\\Users\\Erik\\IdeaProjects\\spring-boot\\MoviesWebsite\\src\\main\\resources\\static\\images";
    private static final String []IMAGE_FORMATS = {"image/jpeg","image/png"};

    public boolean checkImageFormat(String format){
        for (String imageFormat : IMAGE_FORMATS) {
            if(imageFormat.equals(format)){
                return true;
            }
        }
        return false;
    }

    public void save(String parent,String name, MultipartFile file){
        File imageFile = new File(DEFAULT_IMAGE_PATH + "\\" + parent, name);
        try {
            @Cleanup FileOutputStream out = new FileOutputStream(imageFile);
            out.write(file.getBytes());
        } catch (IOException e) {
            if (imageFile.exists()){
                boolean result = imageFile.delete();
                LOGGER.debug("file '{}' delete : {}",name,result);
            }
            throw new RuntimeException("file '" + name+ "' failed save",e);
        }
        LOGGER.debug("image '{}' saved",name);
    }

    public void delete(String parent,String name){
        File file = new File(DEFAULT_IMAGE_PATH + "\\" + parent, name);
        if(file.exists()){
            boolean result = file.delete();
            LOGGER.debug("file '{}' delete : {}",name,result);
        }
    }
}
