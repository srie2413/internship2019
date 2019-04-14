package utils;

public class Utils {
    public static int buildIntFromString(String str) throws NumberFormatException{
        int nr=-1;

        try{

        nr = Integer.parseInt(str);
        return nr;
        }
        catch (NumberFormatException nfEx){
            throw nfEx;
        }
        // won't actually get here
    }
}
