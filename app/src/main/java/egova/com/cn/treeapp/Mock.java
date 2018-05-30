package egova.com.cn.treeapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanchao on 2018/5/30.
 */

public class Mock {

    public static List<RegionBean> getData() {
        //省
        List<RegionBean> dataList = new ArrayList<>();
        RegionBean bean1 = new RegionBean(1, 0, "湖北省", "省级");
        RegionBean bean2 = new RegionBean(2, 0, "广东省", "省级");
        RegionBean bean3 = new RegionBean(3, 0, "浙江省", "省级");
        //市
        RegionBean bean4 = new RegionBean(4, 1, "武汉市", "市级");
        RegionBean bean5 = new RegionBean(5, 1, "宜昌市", "市级");
        RegionBean bean6 = new RegionBean(6, 1, "黄冈市", "市级");

        RegionBean bean7 = new RegionBean(7, 2, "广州市", "市级");
        RegionBean bean8 = new RegionBean(8, 2, "深圳市", "市级");
        RegionBean bean9 = new RegionBean(9, 2, "佛山市", "市级");

        RegionBean bean10 = new RegionBean(10, 3, "杭州市", "市级");
        RegionBean bean11 = new RegionBean(11, 3, "台州市", "市级");
        RegionBean bean12 = new RegionBean(12, 3, "温州市", "市级");

        //县区
        RegionBean bean13 = new RegionBean(13, 4, "武昌区", "区县级");
        RegionBean bean14 = new RegionBean(14, 4, "汉口区", "区县级");
        RegionBean bean15 = new RegionBean(15, 4, "汉阳区", "区县级");

        RegionBean bean16 = new RegionBean(16, 5, "夷陵区", "区县级");
        RegionBean bean17 = new RegionBean(17, 5, "东陵区", "区县级");
        RegionBean bean18 = new RegionBean(18, 5, "猇亭区", "区县级");

        dataList.add(bean1);
        dataList.add(bean2);
        dataList.add(bean3);
        dataList.add(bean4);
        dataList.add(bean5);
        dataList.add(bean6);
        dataList.add(bean7);
        dataList.add(bean8);
        dataList.add(bean9);
        dataList.add(bean10);
        dataList.add(bean11);
        dataList.add(bean12);
        dataList.add(bean13);
        dataList.add(bean14);
        dataList.add(bean15);
        dataList.add(bean16);
        dataList.add(bean17);
        dataList.add(bean18);

        return dataList;
    }
}
