package com.example.omidmarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {

    private LinearLayout userAccount, userUpdate;
    private TextView firstName, phone, address, favorite, email, email_UP_TXT;
    private EditText firstName_EDT,phone_EDT, address_EDT, favorite_EDT;

    private Button signOut, editUser, saveUpdate;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        userAccount = view.findViewById(R.id.user_account);
        userUpdate = view.findViewById(R.id.user_update);

        firstName = view.findViewById(R.id.first_last_name_TXT);
        phone = view.findViewById(R.id.phone_number_TXT);
        address = view.findViewById(R.id.address_TXT);
        favorite = view.findViewById(R.id.favorites_TXT);
        email = view.findViewById(R.id.email_TXT);

        firstName_EDT = view.findViewById(R.id.first_last_name_UP_EDT);
        phone_EDT = view.findViewById(R.id.phone_number_UP_EDT);
        address_EDT = view.findViewById(R.id.address_UP_EDT);
        favorite_EDT = view.findViewById(R.id.favorites_UP_EDT);
        email_UP_TXT = view.findViewById(R.id.email_UP_TXT);


        editUser = view.findViewById(R.id.edit_user);
        saveUpdate = view.findViewById(R.id.save_update);
        signOut = view.findViewById(R.id.sign_out);

        sharedPreferences = getActivity().getSharedPreferences("login_signUp" , Context.MODE_PRIVATE);

        String name_UP = sharedPreferences.getString("name_UP", "");
        String phone_UP = sharedPreferences.getString("phone_UP", "");
        String address_UP = sharedPreferences.getString("address_UP", "");
        String favorite_UP = sharedPreferences.getString("favorite_UP", "");
        String email_UP = sharedPreferences.getString("email_UP", "");
        boolean update_account = sharedPreferences.getBoolean("update_account", false);

        if(!update_account) {

            String name_str = sharedPreferences.getString("name", "");
            String phone_str = sharedPreferences.getString("phone", "");
            String address_str = sharedPreferences.getString("address", "");
            String favorite_str = sharedPreferences.getString("favorite", "");
            String email_str = sharedPreferences.getString("email", "");
            boolean sign_up = sharedPreferences.getBoolean("sign_up", false);
            if (sign_up) {
                firstName.setText(name_str);
                phone.setText(phone_str);
                address.setText(address_str);
                favorite.setText(favorite_str);
                email.setText(email_str);

                firstName_EDT.setText(name_str);
                phone_EDT.setText(phone_str);
                address_EDT.setText(address_str);
                favorite_EDT.setText(favorite_str);
                email_UP_TXT.setText(email_str);

            }

            String name_LOG = sharedPreferences.getString("name_JS", "");
            String phone_LOG = sharedPreferences.getString("phone_JS", "");
            String address_LOG = sharedPreferences.getString("address_JS", "");
            String favorite_LOG = sharedPreferences.getString("favorite_JS", "");
            String email_LOG = sharedPreferences.getString("email_JS", "");
            boolean login = sharedPreferences.getBoolean("login", false);

            if (login) {

                firstName.setText(name_LOG);
                phone.setText(phone_LOG);
                address.setText(address_LOG);
                favorite.setText(favorite_LOG);
                email.setText(email_LOG);

                firstName_EDT.setText(name_LOG);
                phone_EDT.setText(phone_LOG);
                address_EDT.setText(address_LOG);
                favorite_EDT.setText(favorite_LOG);
                email_UP_TXT.setText(email_LOG);
            }
        }else{
            firstName.setText(name_UP);
            phone.setText(phone_UP);
            address.setText(address_UP);
            favorite.setText(favorite_UP);
            email.setText(email_UP);

            firstName_EDT.setText(name_UP);
            phone_EDT.setText(phone_UP);
            address_EDT.setText(address_UP);
            favorite_EDT.setText(favorite_UP);
            email_UP_TXT.setText(email_UP);
        }

        show_user_account();


        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_user_update();

            }
        });

        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String SERVER_UPDATE = "http://php-market.ir/update.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_UPDATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    if(code.equals("success")){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name_UP", jsonObject.getString("name"));
                                        editor.putString("phone_UP", jsonObject.getString("phone"));
                                        editor.putString("address_UP", jsonObject.getString("address"));
                                        editor.putString("favorite_UP", jsonObject.getString("favorite"));
                                        editor.putString("email_UP", jsonObject.getString("email"));
                                        editor.putBoolean("update_account", true);
                                        editor.commit();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        getActivity().startActivity(intent);
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
                        params.put("name" , firstName_EDT.getText().toString());
                        params.put("phone" , phone_EDT.getText().toString());
                        params.put("address" , address_EDT.getText().toString());
                        params.put("favorite" , favorite_EDT.getText().toString());
                        params.put("email" , email_UP_TXT.getText().toString());
                        Log.e("TAG", "getParams: " + email_UP_TXT.getText().toString());

                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                show_user_account();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    public void show_user_account(){
        userUpdate.setVisibility(View.GONE);
        userAccount.setVisibility(View.VISIBLE);
    }
    public void show_user_update(){
        userUpdate.setVisibility(View.VISIBLE);
        userAccount.setVisibility(View.GONE);
        firstName_EDT.requestFocus();
    }
}
