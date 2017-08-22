import java.util.*;
/**
 * Write a description of TitleAndDepthComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleAndDepthComparator implements Comparator<QuakeEntry>{
    @Override 
    public int compare(QuakeEntry q1, QuakeEntry q2) {
        int strComp = q1.getInfo().compareTo(q2.getInfo());
        if (strComp != 0) {
            return strComp;
        }
        return Double.compare(q1.getDepth(), q2.getDepth());
    }
}
