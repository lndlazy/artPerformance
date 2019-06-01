package com.art.recruitment.common.http.config;

/**
 *
 * @Desc: Api的配置类
 */

public class ApiConfig {
    /**
     * 服务器域名
     */
    private String mHostServer;
    /**
     * 资产钱包2.0服务器支付域名
     */
    private String mAssetsWallet2HostServer;

    /**
     * 读超时
     */
    private int mReadTimeOut;
    /**
     * 连接超时
     */
    private int mConnectTimeOut;

    public String getHostServer() {
        return mHostServer;
    }

    public void setHostServer(String hostServer) {
        this.mHostServer = hostServer;
    }

    public int getReadTimeOut() {
        return mReadTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.mReadTimeOut = readTimeOut;
    }

    public int getConnectTimeOut() {
        return mConnectTimeOut;
    }

    public void setConnectTimeOut(int writeTimeOut) {
        this.mConnectTimeOut = writeTimeOut;
    }

    public String getmAssetsWallet2HostServer() {
        return mAssetsWallet2HostServer;
    }

    public void setmAssetsWallet2HostServer(String mAssetsWallet2HostServer) {
        this.mAssetsWallet2HostServer = mAssetsWallet2HostServer;
    }

    @Override
    public String toString() {
        return mHostServer + "///" + mReadTimeOut + "///" + mConnectTimeOut;
    }
}
