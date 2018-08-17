package net.movies.util;

import org.springframework.stereotype.Service;

@Service
public class ControllerUtil {

    public int parseToInteger(String strId){
        int id;
        try {
            id = Integer.parseInt(strId);
        }catch (NumberFormatException e){
            id = -1;
        }
        if(id < 1){
            id = -1;
        }
        return id;
    }
}
