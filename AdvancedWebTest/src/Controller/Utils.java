package Controller;

import Classi.Cdl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public static List<Cdl> randomizeCDL(List<Cdl> list) {
        Random rand = new Random();
        List<Cdl> rlist = new ArrayList();
        int n=4;
        int listSize = list.size();
        for (int i = 0; i < n; i++){
            if(!list.isEmpty() && i <= listSize){
                int randomIndex=rand.nextInt(list.size());
                rlist.add(list.get(randomIndex));
                list.remove(randomIndex);
            }
        }
        return rlist;
    }

    public static int getYear() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        if(month <= 8)
            year = year-1;
        return year;
    }
}
