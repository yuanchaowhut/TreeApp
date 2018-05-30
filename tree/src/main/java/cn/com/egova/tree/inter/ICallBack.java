package cn.com.egova.tree.inter;


public interface ICallBack<T> {
    public static final int STATUS_ERROR = -1;
    public static final int STATUS_SUCCESS = 1;
    public static final int TYPE_CLICK = 100;
    public static final int TYPE_LONG_CLICK = 200;

    /**
     * 获取数据、处理结束时回调
     *
     * @param obj       数量/状态等
     * @param resultObj 列表数据或其他
     *                  这两个参数可以只使用其中的一个，也可以同时使用，请根据需要需要选择
     * @return
     */
    public void onResult(int obj, T resultObj);
}
