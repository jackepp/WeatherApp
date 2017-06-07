package reader;

import parser.Parser;


import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


/**
 * @author Jacob Permansson
 *
 * This class is the controller. Its sort of a intermediary between the view and the model.
 *
 */
public class Controller {
    private Parser parser;
    private LocalDateTime cacheTime = LocalDateTime.now();

    public Controller(Parser parser){
        this.parser = parser;
    }

    public static void main(String[] args) {

    }
    public String search(String name, String time) {

        String temp;
        System.out.println("Location: "+ name + ". Time: " + time);

        LocalDateTime currentTime = LocalDateTime.now();

        if(currentTime.isAfter(cacheTime)) {
            parser.downloadFromYr(name);
            temp = parser.getTemp(name, time);
        } else {
            temp = parser.getTemp(name,time);
        }
        return temp;
    }


    public void updateCacheTime(String cacheTime) throws DateTimeParseException {
        try{
            this.cacheTime = LocalDateTime.parse(cacheTime);
            System.out.println("cache time updated");
        } catch (DateTimeParseException e){
            throw e;
        }

    }

}
