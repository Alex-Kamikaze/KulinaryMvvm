package com.example.kulinarymvvm.ui.fragments;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kulinarymvvm.R;
import com.example.kulinarymvvm.databinding.FragmentAuthorizationBinding;

import java.util.concurrent.Executor;


public class AuthorizationFragment extends Fragment {
    FragmentAuthorizationBinding binding;

    public AuthorizationFragment() {}

    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthorizationBinding.inflate(inflater);
        int allowedAuthenticators = 0;
        BiometricManager manager = BiometricManager.from(requireContext());
        switch(manager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                allowedAuthenticators = BIOMETRIC_STRONG | DEVICE_CREDENTIAL;
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(requireContext(), "Не найдено устройств авторизации по биометрии", Toast.LENGTH_LONG).show();
                allowedAuthenticators = DEVICE_CREDENTIAL;
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(requireContext(), "Устройство авторизации занято в данный момент", Toast.LENGTH_LONG).show();
                allowedAuthenticators = BIOMETRIC_STRONG | DEVICE_CREDENTIAL;
                break;
        }
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Авторизуйтесь, используя биометрию или пин код")
                .setAllowedAuthenticators(allowedAuthenticators)
                .build();
        Executor mainExecutor = requireActivity().getMainExecutor();
        BiometricPrompt authPrompt = new BiometricPrompt(this, mainExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(requireContext(), "Произошла ошибка при авторизации: " + errString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authorizationFragment_to_mainFragment);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        binding.fingerPrint.setOnClickListener(v -> authPrompt.authenticate(promptInfo));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}