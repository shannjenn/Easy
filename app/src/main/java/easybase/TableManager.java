package easybase;

import com.jen.easy.sqlite.EasyTBDao;

public class TableManager {
    private static final String TAG = TableManager.class.getSimpleName();

    private static TableManager manager;
    private EasyTBDao dbDao;

    private TableManager() {
        dbDao = new EasyTBDao(EasyApplication.getAppContext());
    }

    public static TableManager getIns() {
        if (manager == null) {
            synchronized (TableManager.class) {
                if (manager == null) {
                    manager = new TableManager();
                }
            }
        }
        return manager;
    }
/*
    *//**
     * 用户缓存信息
     *
     * @return .
     *//*
    public UserTemp searchMyUserTemp() {
        EasyTBDao dbDao = new EasyTBDao(XGApplication.getApplication());
        UserTemp model = dbDao.searchById(UserTemp.class, XGApplication.getApplication().getMyInfo().getUserId() + "");
        return model;
    }

    *//**
     * 更新用户缓存
     * @param userTemp .
     *//*
    public synchronized void updateUserTemp(UserTemp userTemp) {
        boolean success = dbDao.replace(userTemp);
        EasyLog.d(TAG, "更新 " + UserTemp.class.getSimpleName() + " " + success);
    }

    public FundAccountInfo searchMyFundAccountInfo() {
        EasyTBDao dbDao = new EasyTBDao(XGApplication.getApplication());
        FundAccountInfo model = dbDao.searchById(FundAccountInfo.class, XGApplication.getApplication().getMyInfo().getUserId() + "");
        return model;
    }

    public ClientCashSumInfo searchMyClientCashSumInfo() {
        EasyTBDao dbDao = new EasyTBDao(XGApplication.getApplication());
        ClientCashSumInfo model = dbDao.searchById(ClientCashSumInfo.class, XGApplication.getApplication().getMyInfo().getUserId() + "");
        return model;
    }*/
}
