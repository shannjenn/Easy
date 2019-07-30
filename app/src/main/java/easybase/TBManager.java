package easybase;

import com.jen.easy.sqlite.EasyTBDao;

public class TBManager {
    private static final String TAG = TBManager.class.getSimpleName();

    private static TBManager manager;
    private EasyTBDao dbDao;

    private TBManager() {
        dbDao = new EasyTBDao(EasyApplication.getAppContext());
    }

    public static TBManager getIns() {
        if (manager == null) {
            synchronized (TBManager.class) {
                if (manager == null) {
                    manager = new TBManager();
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
