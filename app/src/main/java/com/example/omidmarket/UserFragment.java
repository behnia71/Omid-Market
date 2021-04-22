package com.example.omidmarket;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {

    private TextView firstName, phone, address, favorite, email;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        firstName = view.findViewById(R.id.first_last_name_TXT);
        phone = view.findViewById(R.id.phone_number_TXT);
        address = view.findViewById(R.id.address_TXT);
        favorite = view.findViewById(R.id.favorites_TXT);
        email = view.findViewById(R.id.email_TXT);

        sharedPreferences = getActivity().getSharedPreferences("login_signUp" , Context.MODE_PRIVATE);


        String name_str= sharedPreferences.getString("name", "");
        String phone_str= sharedPreferences.getString("phone", "");
        String address_str= sharedPreferences.getString("address", "");
        String favorite_str= sharedPreferences.getString("favorite", "");
        String email_str= sharedPreferences.getString("email", "");
        boolean sign_up = sharedPreferences.getBoolean("sign_up", false);
        if(sign_up == true) {
            firstName.setText(name_str);
            phone.setText(phone_str);
            address.setText(address_str);
            favorite.setText(favorite_str);
            email.setText(email_str);
        }

        String name_LOG = sharedPreferences.getString("name_JS", "");
        String phone_LOG = sharedPreferences.getString("phone_JS", "");
        String address_LOG = sharedPreferences.getString("address_JS", "");
        String favorite_LOG = sharedPreferences.getString("favorite_JS", "");
        String email_LOG = sharedPreferences.getString("email_JS", "");
        boolean login = sharedPreferences.getBoolean("login", false);

        if(login == true){

            firstName.setText(name_LOG);
            phone.setText(phone_LOG);
            address.setText(address_LOG);
            favorite.setText(favorite_LOG);
            email.setText(email_LOG);
        }


        return view;
    }
}
