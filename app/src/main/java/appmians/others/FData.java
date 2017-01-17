package appmians.others;

import java.util.HashMap;

/**
 * Created by user on 2017/1/12.
 */

public class FData {
    public static HashMap<String, String> data1 = new HashMap<>();
    public static HashMap<String, String> data2 = new HashMap<>();
    static {
        initData1();
        initData2();
    }
    private static void initData1() {
        data1.put("百度","https://www.baidu.com/");
        data1.put("搜狗","https://www.sogou.com/");
        data1.put("宜搜","http://book.easou.com/");
        data1.put("雅虎","https://www.yahoo.com/");
    }
    private static void initData2() {
        data2.put("爱奇艺","http://www.iqiyi.com/");
        data2.put("腾讯视频","https://v.qq.com/");
        data2.put("优酷","http://www.youku.com/");
        data2.put("乐视TV","http://www.le.com/");
    }
}
