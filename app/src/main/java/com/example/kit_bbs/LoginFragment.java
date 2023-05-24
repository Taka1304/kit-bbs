package com.example.kit_bbs;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPassword;

    private Button submitButton;
    private Button cancelButton;
    private Button switchButton;
    private String email;
    private String password;
    private String email_pattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.kanazawa-it\\.ac\\.jp$";

    public LoginFragment() {
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "ログインしました！");
                            Toast.makeText(getContext(), "ログインしました！",
                                    Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        etEmail = view.findViewById(R.id.email_edittext_login);
        etPassword = view.findViewById(R.id.password_edittext_login);
        submitButton = view.findViewById(R.id.login_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        switchButton = view.findViewById(R.id.send_signup_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if (email.length() == 0 || password.length() == 0) {
                    Toast.makeText(getContext(), "入力されていない項目があります",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(email.matches(email_pattern)){
                    //match
                    signIn(email, password);
                }else{
                    //unmatched
                    Toast.makeText(getContext(),"学校のメールアドレスを入力してください",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment signupFragment = new SignupFragment();
                // loginFragmentに切り替え
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.popBackStack(); // loginFragmentをStackから消す
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, signupFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
