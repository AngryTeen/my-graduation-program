package entity;

public class AppUsedInfo
{
	private String mAppName;
    private String mAppStartTime;
    private String mAppStopTime;

    public AppUsedInfo()
    {
    }

    public AppUsedInfo(String appName, String appStartTime, String appStopTime)
    {
        mAppName = appName;
        mAppStartTime = appStartTime;
        mAppStopTime = appStopTime;
    }

    public String getAppName()
    {
        return mAppName;
    }

    public void setAppName(String appName)
    {
        mAppName = appName;
    }

    public String getAppStartTime()
    {
        return mAppStartTime;
    }

    public void setAppStartTime(String appStartTime)
    {
        mAppStartTime = appStartTime;
    }

    public String getAppStopTime()
    {
        return mAppStopTime;
    }

    public void setAppStopTime(String appStopTime)
    {
        mAppStopTime = appStopTime;
    }
}
