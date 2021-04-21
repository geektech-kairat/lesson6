package com.example.lesson6.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lesson6.databinding.FragmentAuthBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class AuthFragment extends Fragment {


    private FragmentAuthBinding binding;
    private String num;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCallbacks();
        binding = FragmentAuthBinding.inflate(inflater, container, false);



        binding.btn.setOnClickListener(v -> {

            requestSms(binding.edit.getText().toString());
        });
        return binding.getRoot();
    }

    private void requestSms(String num) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(num)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void setCallbacks(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e(TAG, "onVerificationCompleted: " );

            }



            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e(TAG, "onVerificationFailed: " );

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e(TAG, "onCodeSent: "  );
                binding.edi2.setHint("Введите смс код");
                binding.btn.setText("Проверить");
                binding.edit.setText("");




            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                binding.edi2.setHint("Введите номер телефона");
                binding.btn.setText("Отправить");
                binding.edit.setText("");
            }
        };

    }



}