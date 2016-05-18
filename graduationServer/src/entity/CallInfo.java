package entity;

public class CallInfo 
{
	private String mRingTime;
    private String mCallStartTime;
    private String mCallStopTime;
    
    public CallInfo()
    {

    }

    public CallInfo(String callStartTime, String callStopTime, String ringTime)
    {
        mCallStartTime = callStartTime;
        mCallStopTime = callStopTime;
        mRingTime = ringTime;
    }

    public String getCallStopTime()
    {
        return mCallStopTime;
    }

    public void setCallStopTime(String callStopTime)
    {
        mCallStopTime = callStopTime;
    }

    public String getCallStartTime()
    {
        return mCallStartTime;
    }

    public void setCallStartTime(String callStartTime)
    {
        mCallStartTime = callStartTime;
    }

    public String getRingTime()
    {
        return mRingTime;
    }

    public void setRingTime(String ringTime)
    {
        mRingTime = ringTime;
    }
}
