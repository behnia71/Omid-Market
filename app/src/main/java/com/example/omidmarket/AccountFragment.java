package com.example.omidmarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class AccountFragment extends Fragment {

    private EditText email,ver1,ver2,ver3,ver4,ver5,ver6;
    private Button sendPhone,sendVerify;
    private LinearLayout sendLayout, verifyLayout;
    private int randomNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.acconut_fragment, container, false);

        email = view.findViewById(R.id.email);

        sendPhone = view.findViewById(R.id.send_phone);
        sendVerify = view.findViewById(R.id.send_verify);

        sendLayout = view.findViewById(R.id.send_layout);
        verifyLayout = view.findViewById(R.id.verify_layout);

        ver1 = view.findViewById(R.id.ver1);
        ver2 = view.findViewById(R.id.ver2);
        ver3 = view.findViewById(R.id.ver3);
        ver4 = view.findViewById(R.id.ver4);
        ver5 = view.findViewById(R.id.ver5);
        ver6 = view.findViewById(R.id.ver6);

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

                    Toast.makeText(getContext(), "Email is Successfully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "به امید مارکت خوش آمدید..", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void show_sendLayout(){
        sendLayout.setVisibility(View.VISIBLE);
        verifyLayout.setVisibility(View.GONE);
    }
    public void show_verifyLayout(){
        sendLayout.setVisibility(View.GONE);
        verifyLayout.setVisibility(View.VISIBLE);
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
