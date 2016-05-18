package entity;

	public class MessageInfo {
	private String mMessageTime;
    private int mMessageType;
    public MessageInfo()
    {

    }

    public MessageInfo(String messageTime, int messageType)
    {
        mMessageTime = messageTime;
        mMessageType = messageType;
    }

    public String getMessageTime()
    {
        return mMessageTime;
    }

    public void setMessageTime(String messageTime)
    {
        mMessageTime = messageTime;
    }

    public int getMessageType()
    {
        return mMessageType;
    }

    public void setMessageType(int messageType)
    {
        mMessageType = messageType;
    }
}
