package com.example.phong_assignment.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.View.ChangePasswordActivity;
import com.example.phong_assignment.databinding.FragmentSettingBinding;


public class FragmentSetting extends Fragment {
    private FragmentSettingBinding binding;
    private HttpRequest httpRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        binding = FragmentSettingBinding.bind(view);
        httpRequest = new HttpRequest();

        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });
        return view;
    }
}