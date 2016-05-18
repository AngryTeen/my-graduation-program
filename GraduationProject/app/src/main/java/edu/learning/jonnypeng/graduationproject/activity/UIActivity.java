package edu.learning.jonnypeng.graduationproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.learning.jonnypeng.graduationproject.R;
import edu.learning.jonnypeng.graduationproject.adapter.NumericWheelAdapter;
import edu.learning.jonnypeng.graduationproject.adapter.SimpleDialog;
import edu.learning.jonnypeng.graduationproject.entities.UserInfo;
import edu.learning.jonnypeng.graduationproject.handler.UserInfoHandler;
import edu.learning.jonnypeng.graduationproject.listener.TextWatcherListener;
import edu.learning.jonnypeng.graduationproject.service.GetInfoService;
import edu.learning.jonnypeng.graduationproject.utils.CharacterParser;
import edu.learning.jonnypeng.graduationproject.utils.ServiceUtil;
import edu.learning.jonnypeng.graduationproject.utils.SharedPreferenceUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;
import edu.learning.jonnypeng.graduationproject.utils.ValidateUtil;
import edu.learning.jonnypeng.graduationproject.wheelview.WheelView;

public class UIActivity extends BaseActivity implements View.OnClickListener,
        View.OnFocusChangeListener {
    private static final String TAG = "UIActivity";
    private static final int NINETEEN_CENTURY = 1900;
    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;

    //Define User Information Views
    private EditText mAccount;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmail;
    private AutoCompleteTextView mSchoolName;
    private EditText mStudentId;
    private RadioButton mMale;
    private RadioButton mFemale;
    private Button mSubmit;
    private WheelView mYear;
    private WheelView mMonth;
    private WheelView mDay;

    //Define Prompt Information Views
    private TextView mAccountError;// Account Error TextView
    private LinearLayout mPasswordStrength; //Strength Of Password
    private LinearLayout mPasswordWeak; //Weak Strength
    private LinearLayout mPasswordMiddle;//Middle Strength
    private LinearLayout mPasswordStrong; //Strong Strength
    private TextView mPasswordError; //Password Error
    private TextView mConfirmPasswordError;//Confirm Error
    private TextView mEmailError;//Email Error View
    private TextView mSchoolNameError;// School Name Error View
    private TextView mStudentIdError;// Student ID Error View
    private ArrayAdapter<String> mSchoolNameAdapter;
    private List<String> mSchoolDataSource = new ArrayList<>();
    private NumericWheelAdapter mMonthAdapter;
    private NumericWheelAdapter mDayAdapter;
    private NumericWheelAdapter mYearAdapter;
    private Calendar mCalendar;

    //calculate password number
    private int passwordCount = 0;
    private ProgressDialog mProgressDialog;

    //define send to server
    private RequestQueue mQueue;
    private StringRequest mRequest;
    private String mReturnValue;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        ServiceUtil serviceUtil = new ServiceUtil(this);
        if (false == serviceUtil.isServiceRunning(GetInfoService.class.getName())) {

            Intent mIntent = new Intent(this, GetInfoService.class);
            startService(mIntent);
        }
        getAllSchoolName();
        initUUID();
        initViews();
        setListener();

    }

    public void initUUID() {
        String uuid = SharedPreferenceUtil.getString(this, StringConstant.UUID_KEY);
        if (null == uuid) {
            uuid = UUID.randomUUID().toString();
        }
        Log.d(TAG, "uuid value : " + uuid);
        SharedPreferenceUtil.putStringParam(this, StringConstant.UUID_KEY, uuid);
    }

    public void getAllSchoolName() {
        try {

            InputStream inputStream = getAssets().open("school_zh.xml");

            XmlPullParser parser = Xml.newPullParser();

            parser.setInput(inputStream, "UTF-8");

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (XmlPullParser.START_TAG == eventType) {
                    if (parser.getName().equals("school_name")) {
                        eventType = parser.next();
                        mSchoolDataSource.add(parser.getText());
                    }
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IOException : " + e.getMessage());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.e(TAG, "XmlPullParserException : " + e.getMessage());
        }

        //check system language
        Log.i(TAG, "system language : " + getResources().getConfiguration().locale.getCountry());
        if (getResources().getConfiguration().locale.getCountry().equals("UK")
                || getResources().getConfiguration().locale.getCountry().equals("US")
                || getResources().getConfiguration().locale.getCountry().equals("GB")) {
            int count = mSchoolDataSource.size();
            CharacterParser parser = CharacterParser.getInstance();
            for (int i = 0; i < count; i++) {
                parser.setResource(mSchoolDataSource.get(i));
                mSchoolDataSource.set(i, parser.getSpelling());
            }
        }
    }

    public void initViews() {
        //Init User Information Views
        mAccount = (EditText) findViewById(R.id.etAccount);
        mPassword = (EditText) findViewById(R.id.etPassword);
        mConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mSchoolName = (AutoCompleteTextView) findViewById(R.id.etSchoolName);
        mStudentId = (EditText) findViewById(R.id.etStudentId);
        mMale = (RadioButton) findViewById(R.id.btnMale);
        mFemale = (RadioButton) findViewById(R.id.btnFemale);
        mSubmit = (Button) findViewById(R.id.btnSubmit);

        //Init Prompt Information Views
        mAccountError = (TextView) findViewById(R.id.tvAccountErr);
        mPasswordError = (TextView) findViewById(R.id.tvPasswordErr);
        mConfirmPasswordError = (TextView) findViewById(R.id.tvConfirmPasswordErr);
        mEmailError = (TextView) findViewById(R.id.tvEmailErr);
        mSchoolNameError = (TextView) findViewById(R.id.tvSchoolNameErr);
        mStudentIdError = (TextView) findViewById(R.id.tvStudentIdErr);
        mPasswordStrength = (LinearLayout) findViewById(R.id.layoutPasswordStrength);
        mPasswordWeak = (LinearLayout) findViewById(R.id.layoutWeak);
        mPasswordMiddle = (LinearLayout) findViewById(R.id.layoutMiddle);
        mPasswordStrong = (LinearLayout) findViewById(R.id.layoutStrong);
        //Set School Name Auto Complete Adapter
        mSchoolNameAdapter = new ArrayAdapter<>(this, R.layout.school_name_list_view_item, R.id.tvSchoolName, mSchoolDataSource);
        mSchoolName.setAdapter(mSchoolNameAdapter);

        //Init Birth Adapter
        mYear = (WheelView) findViewById(R.id.year);
        mMonth = (WheelView) findViewById(R.id.month);
        mDay = (WheelView) findViewById(R.id.day);
        mCalendar = Calendar.getInstance();


        mMonthAdapter = new NumericWheelAdapter(this, MIN_MONTH, MAX_MONTH);
        mMonthAdapter.setItemResource(R.layout.wheel_text_item);
        mMonthAdapter.setItemTextResource(R.id.text);
        mMonth.setViewAdapter(mMonthAdapter);


        mDayAdapter = new NumericWheelAdapter(this, MIN_DAY, MAX_DAY);
        mDayAdapter.setItemResource(R.layout.wheel_text_item);
        mDayAdapter.setItemTextResource(R.id.text);
        mDay.setViewAdapter(mDayAdapter);

        mYearAdapter = new NumericWheelAdapter(this, NINETEEN_CENTURY, mCalendar.get(Calendar.YEAR));
        mYearAdapter.setItemResource(R.layout.wheel_text_item);
        mYearAdapter.setItemTextResource(R.id.text);
        mYear.setViewAdapter(mYearAdapter);
        //Get Current Time
        mDay.setCurrentItem(mCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        mMonth.setCurrentItem(mCalendar.get(Calendar.MONTH));
        mYear.setCurrentItem(mCalendar.get(Calendar.YEAR) - NINETEEN_CENTURY);


    }


    public void setListener() {
        mSubmit.setOnClickListener(this);//Set Click Submit Listener

        //Set Focus Change Listener, Cursor into Text and out Text
        mAccount.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);
        mConfirmPassword.setOnFocusChangeListener(this);
        mEmail.setOnFocusChangeListener(this);
        mSchoolName.setOnFocusChangeListener(this);
        mStudentId.setOnFocusChangeListener(this);

        //Set Text Change Listener,
        mAccount.addTextChangedListener(new TextWatcherListener(mAccountError));
        setPasswordTextWatcherListener();
        mConfirmPassword.addTextChangedListener(new TextWatcherListener(mConfirmPasswordError));
        mEmail.addTextChangedListener(new TextWatcherListener(mEmailError));
        mSchoolName.addTextChangedListener(new TextWatcherListener(mSchoolNameError));
        mStudentId.addTextChangedListener(new TextWatcherListener(mStudentIdError));


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                //Validate All User Information
                mProgressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.dialog_waite_send_to_server));
                if (validateAll()) {
                    Log.d(TAG, "user info is right");
                    UserInfo userInfo = getUserInfo();
                    mParams = new HashMap<>();
                    mParams.put(StringConstant.ACCOUNT, userInfo.getAccount());
                    mParams.put(StringConstant.PASSWORD, userInfo.getPassword());
                    mParams.put(StringConstant.EMAIL, userInfo.getEmail());
                    mParams.put(StringConstant.SCHOOL, userInfo.getSchoolName());
                    mParams.put(StringConstant.STUDENT_ID, userInfo.getStudentId());
                    mParams.put(StringConstant.BIRTH, userInfo.getBirth());
                    mParams.put(StringConstant.SEX, userInfo.getSex());
                    mHeaders = null;
                    sendUserDataToServer(StringConstant.USER_URL);


                } else {
                    mProgressDialog.dismiss();
                    new SimpleDialog(this).showTextDialog(getResources().getString(R.string.dialog_title),
                            getResources().getString(R.string.dialog_text_info_not_full));
                }
                break;
        }
    }

    /**
     * Listen Password Text Change
     */
    public void setPasswordTextWatcherListener() {
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordCount = s.length();
                Log.d(TAG, "password count : " + passwordCount);
                Log.d(TAG, "count value " + count);
                Log.d(TAG, "start value " + start);
                Log.d(TAG, "before value " + before);
                Log.d(TAG, "s value " + s);

                if (passwordCount > 0 && passwordCount < 6) {
                    mPasswordStrength.setVisibility(View.VISIBLE);
                    mPasswordError.setVisibility(View.GONE);
                    mPasswordWeak.setVisibility(View.VISIBLE);

                    mPasswordMiddle.setVisibility(View.INVISIBLE);
                    mPasswordStrong.setVisibility(View.INVISIBLE);
                } else if (passwordCount >= 6 && passwordCount <= 10) {
                    mPasswordStrength.setVisibility(View.VISIBLE);
                    mPasswordError.setVisibility(View.GONE);
                    mPasswordWeak.setVisibility(View.INVISIBLE);
                    mPasswordMiddle.setVisibility(View.VISIBLE);
                    mPasswordStrong.setVisibility(View.INVISIBLE);
                } else if (passwordCount > 10 && passwordCount <= 16) {
                    mPasswordStrength.setVisibility(View.VISIBLE);
                    mPasswordError.setVisibility(View.GONE);
                    mPasswordWeak.setVisibility(View.INVISIBLE);
                    mPasswordMiddle.setVisibility(View.INVISIBLE);
                    mPasswordStrong.setVisibility(View.VISIBLE);
                } else {
                    mPasswordStrength.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * validate data when has not focus
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG, "hasFocus value : " + hasFocus);
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.etAccount:
                    validateAccount();
                    break;
                case R.id.etPassword:
                    validatePassword();
                    break;
                case R.id.etConfirmPassword:
                    validateConfirmPassword();
                    break;
                case R.id.etEmail:
                    validateEmail();
                    break;
                case R.id.etSchoolName:
                    validateSchoolName();
                    break;
                case R.id.etStudentId:
                    validateStudentId();
                    break;
                default:
                    break;
            }
        }
    }

    public void validateAccount() {
        //Log.d(TAG, "checkValue:" + validateResult);
        if (ValidateUtil.isNull(mAccount.getText().toString())) {
            //Log.d(TAG, "checkValue:" + validateResult);
            //mAccount.setBackground(getDrawable(R.drawable.edit_error));
            mAccountError.setVisibility(View.VISIBLE);
            mAccountError.setText(R.string.account_null);
        } else if (ValidateUtil.hasSpecialCharacter(mAccount.getText().toString())) {
            mAccountError.setVisibility(View.VISIBLE);
            mAccountError.setText(R.string.account_error);
        } else {
            //mAccount.setBackground(getDrawable(R.drawable.edit_text_not_focused));
        }
    }

    public void validatePassword() {
        if (ValidateUtil.isNull(mPassword.getText().toString())) {
            //mPassword.setBackground(getDrawable(R.drawable.edit_error));
            mPasswordError.setVisibility(View.VISIBLE);
            mPasswordStrength.setVisibility(View.GONE);
            mPasswordError.setText(R.string.password_null);
        } else if (ValidateUtil.hasSpecialCharacter(mPassword.getText().toString())) {
            mPasswordError.setVisibility(View.VISIBLE);
            mPasswordStrength.setVisibility(View.GONE);
            mPasswordError.setText(R.string.password_error);
        } else {
            //mPassword.setBackground(getDrawable(R.drawable.edit_text_selector));
            mPasswordStrength.setVisibility(View.INVISIBLE);
        }
    }

    public void validateConfirmPassword() {
        /*String checkError =ValidateUtil.validateConfirmPassword(mPassword.getText().toString(),
                mConfirmPassword.getText().toString());*/
        if (ValidateUtil.isNull(mConfirmPassword.getText().toString())) {
            //mConfirmPassword.setBackground(getDrawable(R.drawable.edit_error));
            mConfirmPasswordError.setVisibility(View.VISIBLE);
            mConfirmPasswordError.setText(R.string.confirm_password_null);
        } else if (!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            //mConfirmPassword.setBackground(getDrawable(R.drawable.edit_error));
            mConfirmPasswordError.setVisibility(View.VISIBLE);
            mConfirmPasswordError.setText(R.string.confirm_error);
        } else {
            //mConfirmPassword.setBackground(getDrawable(R.drawable.edit_text_selector));
        }
    }

    public void validateEmail() {
        if (ValidateUtil.isNull(mEmail.getText().toString())) {
            //mEmail.setBackground(getDrawable(R.drawable.edit_error));
            mEmailError.setVisibility(View.VISIBLE);
            mEmailError.setText(R.string.email_null);
        } else if (!ValidateUtil.validateEmail(mEmail.getText().toString())) {
            //mEmail.setBackground(getDrawable(R.drawable.edit_text_selector));
            mEmailError.setVisibility(View.VISIBLE);
            mEmailError.setText(R.string.email_format_error);
        }
    }

    public void validateSchoolName() {
        if (ValidateUtil.isNull(mSchoolName.getText().toString())) {
            //mSchoolName.setBackground(getDrawable(R.drawable.edit_error));
            mSchoolNameError.setVisibility(View.VISIBLE);
            mSchoolNameError.setText(R.string.school_name_null);
        } else {
            //mSchoolName.setBackground(getDrawable(R.drawable.edit_text_selector));
        }
    }

    public void validateStudentId() {
        if (ValidateUtil.isNull(mStudentId.getText().toString())) {
            // mStudentId.setBackground(getDrawable(R.drawable.edit_error));
            mStudentIdError.setVisibility(View.VISIBLE);
            mStudentIdError.setText(R.string.student_id_null);
        } else if (ValidateUtil.hasSpecialCharacter(mStudentId.getText().toString())) {
            mStudentIdError.setVisibility(View.VISIBLE);
            mStudentIdError.setText(R.string.student_id_error);
        } else {
            //mStudentId.setBackground(getDrawable(R.drawable.edit_text_selector));
        }
    }

    public boolean validateAll() {
        validateAccount();
        validatePassword();
        validateConfirmPassword();
        validateEmail();
        validateSchoolName();
        validateStudentId();
        if (View.VISIBLE == mAccountError.getVisibility()) {
            return false;
        }
        if (View.VISIBLE == mPasswordError.getVisibility()) {
            return false;
        }
        if (View.VISIBLE == mConfirmPasswordError.getVisibility()) {
            return false;
        }
        if (View.VISIBLE == mEmailError.getVisibility()) {
            return false;
        }
        if (View.VISIBLE == mSchoolNameError.getVisibility()) {
            return false;
        }
        if (View.VISIBLE == mStudentIdError.getVisibility()) {
            return false;
        }
        return true;
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(mAccount.getText().toString());
        Log.d(TAG, "Account is : " + userInfo.getAccount());
        userInfo.setPassword(mPassword.getText().toString());
        Log.d(TAG, "Password is : " + userInfo.getPassword());
        userInfo.setEmail(mEmail.getText().toString());
        Log.d(TAG, "Email is : " + userInfo.getEmail());
        userInfo.setSchoolName(mSchoolName.getText().toString());
        Log.d(TAG, "School is : " + userInfo.getSchoolName());
        userInfo.setStudentId(mStudentId.getText().toString());
        Log.d(TAG, "Student ID is : " + userInfo.getStudentId());

        userInfo.setBirth((mYear.getCurrentItem() + 1900) + "-"
                + (mMonth.getCurrentItem() + 1) + "-"
                + (mDay.getCurrentItem() + 1));
        Log.d(TAG, "Birth date is : " + userInfo.getBirth());
        if (mMale.isChecked()) {
            userInfo.setSex("male");
        } else {
            userInfo.setSex("female");
        }
        Log.d(TAG, "Sex is : " + userInfo.getSex());


        return userInfo;
    }

    public void sendUserDataToServer(String url) {
        mQueue = Volley.newRequestQueue(this);
        mRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, "onResponse()");
                Message msg = Message.obtain();
                Log.d(TAG, "return value1 : ");
                s = s.replaceAll("\r|\n", "");//due to "return value\r\n" ,so replace "\r\n" that by ""
                if (StringConstant.USER_REGISTER_SUCCESS.equals(s)) {

                    msg.what = 0;
                } else if (StringConstant.USER_EXIST.equals(s)) {
                    msg.what = 2;

                }
                UserInfoHandler handler = new UserInfoHandler(UIActivity.this, mProgressDialog);
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message msg = Message.obtain();
                msg.what = 1;
                UserInfoHandler handler = new UserInfoHandler(UIActivity.this, mProgressDialog);
                handler.sendMessage(msg);
                Log.e(TAG, "SEND TO SERVER ERROR, " + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (null == mParams) {
                    return super.getParams();
                } else {
                    return mParams;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (null == mHeaders) {
                    return super.getHeaders();
                } else {
                    return mHeaders;
                }

            }
        };
        mQueue.add(mRequest);
    }

}
