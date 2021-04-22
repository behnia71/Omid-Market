package com.example.omidmarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.security.keystore.StrongBoxUnavailableException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class AccountFragment extends Fragment {

    private EditText email,ver1,ver2,ver3,ver4,ver5,ver6;
    private EditText firstLastName,phoneNumber, address, favorite;
    private Button sendPhone,sendVerify, sendInformation;
    private LinearLayout sendLayout, verifyLayout,informationLayout;
    private int randomNumber;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.acconut_fragment, container, false);

        email = view.findViewById(R.id.email);

        sendPhone = view.findViewById(R.id.send_phone);
        sendVerify = view.findViewById(R.id.send_verify);
        sendInformation = view.findViewById(R.id.send_information_BTN);

        sendLayout = view.findViewById(R.id.send_layout);
        verifyLayout = view.findViewById(R.id.verify_layout);
        informationLayout = view.findViewById(R.id.information_layout);

        ver1 = view.findViewById(R.id.ver1);
        ver2 = view.findViewById(R.id.ver2);
        ver3 = view.findViewById(R.id.ver3);
        ver4 = view.findViewById(R.id.ver4);
        ver5 = view.findViewById(R.id.ver5);
        ver6 = view.findViewById(R.id.ver6);

        firstLastName = view.findViewById(R.id.first_last_name_EDT);
        phoneNumber = view.findViewById(R.id.phone_number_EDT);
        address = view.findViewById(R.id.address_EDT);
        favorite = view.findViewById(R.id.favorites_EDT);

        sharedPreferences = getActivity().getSharedPreferences("login_signUp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        show_sendLayout();


        sendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = "omidbehnia9@gmail.com";
                final String password = "Omid1371";
                Random random = new Random();
                randomNumber = random.nextInt(99999)+100000;
                String messageToSend = "Your Code is : " + randomNumber;
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.port","587");
                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator(){
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                try{
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email.getText().toString()));
                    message.setSubject("به امید مارکت خوش آمدید");
                    message.setText(messageToSend);
                    Transport.send(message);

                    Toast.makeText(getContext(), "ایمیل حاوی لینک ارسال شد", Toast.LENGTH_SHORT).show();
                    show_verifyLayout();
                }catch(MessagingException e){
                    Toast.makeText(getContext(), "Error" + e.getMessage() , Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "onClick: " + e.getMessage() );
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        next_editText(ver1 , ver2);
        next_editText(ver2 , ver3);
        next_editText(ver3 , ver4);
        next_editText(ver4 , ver5);
        next_editText(ver5 , ver6);




    sendVerify.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String verify1= ver1.getText().toString();
            String verify2= ver2.getText().toString();
            String verify3= ver3.getText().toString();
            String verify4= ver4.getText().toString();
            String verify5= ver5.getText().toString();
            String verify6= ver6.getText().toString();

            String verifyCode = verify1+verify2+verify3+verify4+verify5+verify6;

            if(randomNumber == Integer.valueOf(verifyCode)){
                final String SERVER_LOGIN = "http://php-market.ir/login.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code2 = jsonObject.getString("code");
                                    if(code2.equals("success")){
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        getActivity().startActivity(intent);
                                        editor.putString("name_JS", jsonObject.getString("name"));
                                        editor.putString("phone_JS", jsonObject.getString("phone"));
                                        editor.putString("address_JS", jsonObject.getString("address"));
                                        editor.putString("favorite_JS", jsonObject.getString("favorite"));
                                        editor.putString("email_JS", jsonObject.getString("email"));
                                        editor.putBoolean("login", true);
                                        editor.commit();
                                        Toast.makeText(getContext(), "شما با موفقیت وارد شدید.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        show_informationLayout();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> params = new HashMap<String, String>();
                     params.put("email" , email.getText().toString());

                     return params;
                    }
                };
                requestQueue.add(stringRequest);
            }else{
                Toast.makeText(getContext(), "رمز اشتباه است", Toast.LENGTH_SHORT).show();
            }
        }
    });
    sendInformation.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String SERVER_REGISTER = "http://php-market.ir/register.php";
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                String firstLastName_str = firstLastName.getText().toString();
                                String phone_str = phoneNumber.getText().toString();
                                String address_str = address.getText().toString();
                                String favorite_str = favorite.getText().toString();
                                String email_str = email.getText().toString();
                                if(!firstLastName_str.isEmpty() && !phone_str.isEmpty() &&
                                        !address_str.isEmpty() && !favorite_str.isEmpty()){
                                if(code.equals("success")){
                                    Toast.makeText(getContext(), "ثبت نام شما با موافقت انجام شد", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    getActivity().startActivity(intent);
                                    editor.putString("name", firstLastName_str);
                                    editor.putString("phone", phone_str);
                                    editor.putString("address", address_str);
                                    editor.putString("favorite",favorite_str);
                                    editor.putString("email", email_str);
                                    editor.putBoolean("sign_up", true);
                                    editor.commit();

                                }else{
                                    show_sendLayout();
                                    Toast.makeText(getContext(), "You Sign-UP have ERROR...Please Try Again", Toast.LENGTH_SHORT).show();
                                }
                                }else{
                                    Toast.makeText(getContext(), "لطفا تمام فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", firstLastName.getText().toString());
                    params.put("phone", phoneNumber.getText().toString());
                    params.put("address", address.getText().toString());
                    params.put("favorite", favorite.getText().toString());
                    params.put("email", email.getText().toString());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    });

        return view;
    }

    public void show_sendLayout(){
        sendLayout.setVisibility(View.VISIBLE);
        verifyLayout.setVisibility(View.GONE);
        informationLayout.setVisibility(View.GONE);

    }
    public void show_verifyLayout(){
        sendLayout.setVisibility(View.GONE);
        verifyLayout.setVisibility(View.VISIBLE);
        informationLayout.setVisibility(View.GONE);
    }
    public void show_informationLayout(){
        sendLayout.setVisibility(View.GONE);
        verifyLayout.setVisibility(View.GONE);
        informationLayout.setVisibility(View.VISIBLE);
    }


    public void next_editText(EditText ver , EditText verFocus){

        ver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ver.getText().toString().length() == 1){
                    verFocus.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

}
