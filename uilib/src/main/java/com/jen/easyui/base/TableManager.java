package com.jen.easyui.base;

import com.jen.easy.sqlite.DBDao;

public class TableManager {
    private static final String TAG = TableManager.class.getSimpleName();

    private static TableManager manager;
    private DBDao dbDao;

    private TableManager() {
        dbDao = new DBDao(EasyApplication.getAppContext());
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
        DBDao dbDao = new DBDao(XGApplication.getApplication());
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
        DBDao dbDao = new DBDao(XGApplication.getApplication());
        FundAccountInfo model = dbDao.searchById(FundAccountInfo.class, XGApplication.getApplication().getMyInfo().getUserId() + "");
        return model;
    }

    public ClientCashSumInfo searchMyClientCashSumInfo() {
        DBDao dbDao = new DBDao(XGApplication.getApplication());
        ClientCashSumInfo model = dbDao.searchById(ClientCashSumInfo.class, XGApplication.getApplication().getMyInfo().getUserId() + "");
        return model;
    }*/
}
