package pl.edu.agh.pp.utils;

import java.util.List;

public class Utils {
    public static String longsToString(List<Long> movieIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Long movieId : movieIds) {
            if (movieId != null && movieId != -1) {
                stringBuilder.append(movieId);
                stringBuilder.append(',');
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
