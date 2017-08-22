import java.util.*;
/**
 * Write a description of TitleLastAndMagnitudeComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry>{
    @Override 
    public int compare(QuakeEntry q1, QuakeEntry q2) {
        String [] q1Title = q1.getInfo().split("\\s+");
        String [] q2Title = q2.getInfo().split("\\s+");
        int strComp = q1Title[q1Title.length-1].compareTo(q2Title[q2Title.length-1]);
        if (strComp != 0) {
            return strComp;
        }
        return Double.compare(q1.getMagnitude(), q2.getMagnitude());
    }
}
