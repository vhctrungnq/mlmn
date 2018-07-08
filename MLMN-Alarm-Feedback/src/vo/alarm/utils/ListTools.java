package vo.alarm.utils;

import java.util.StringTokenizer;
import java.util.Vector;

public class ListTools {
	public static java.util.List<String> toList(StringTokenizer st, java.util.List<String> list) {
        java.util.List<String> v = (list != null)? list : new Vector<String>();
        if (st != null) { for (;st.hasMoreTokens();) { v.add(st.nextToken()); } }
        return v;
    }
}