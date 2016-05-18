package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.AppUsedInfo;
import entity.CallInfo;
import entity.MessageInfo;
import entity.SensorInfo;
import entity.UserInfo;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by jonny.peng on 2016/4/25 11:15.
 * 邮箱:Jonny.peng@tinno.com
 */
public class GsonUtil
{
    Type mType;
    Gson mGson;
    public String getJsonData(List<?> list)
    {
        mGson = new Gson();
        return mGson.toJson(list);
    }
    public List<Object> fromJson(String data)
    {
        mType = new TypeToken<List<Object>>(){}.getType();
        mGson = new Gson();
        List<Object> list = mGson.fromJson(data, mType);
        return list;
    }

    public List<UserInfo> userInfosFromJson(String jsonData)
    {
        mType = new TypeToken<List<UserInfo>>(){}.getType();
        mGson = new Gson();
        List<UserInfo> list = mGson.fromJson(jsonData, mType);
        return list;
    }

    public List<AppUsedInfo> appInfoFromJson(String jsonData)
    {
        mType = new TypeToken<List<AppUsedInfo>>(){}.getType();
        mGson = new Gson();
        List<AppUsedInfo> list = mGson.fromJson(jsonData, mType);
        return list;
    }

    public  List<CallInfo> callInfoFromJson(String jsonData)
    {
        mType = new TypeToken<List<CallInfo>>(){}.getType();
        mGson = new Gson();
        List<CallInfo> list = mGson.fromJson(jsonData, mType);
        return list;
    }

    public List<MessageInfo> messageInfoFromJson(String jsonData)
    {
        mType = new TypeToken<List<MessageInfo>>(){}.getType();
        mGson = new Gson();
        List<MessageInfo> list = mGson.fromJson(jsonData, mType);
        return list;
    }

    public List<SensorInfo> sensorInfoFromJson(String jsonData)
    {
        mType = new TypeToken<List<SensorInfo>>(){}.getType();
        mGson = new Gson();
        List<SensorInfo> list = mGson.fromJson(jsonData, mType);
        return list;
    }

}
