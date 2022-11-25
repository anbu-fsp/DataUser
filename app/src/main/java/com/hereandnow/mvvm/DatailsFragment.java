package com.hereandnow.mvvm;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hereandnow.data.dao.DataDao;
import com.hereandnow.mvvm.databinding.FragmentLoginBinding;
import com.hereandnow.mvvm.databinding.FragmentSigninBinding;
import com.hereandnow.utils.Constant;
import com.hereandnow.viewModel.DataViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DatailsFragment extends Fragment {

    private int id;
    private String first_name, last_name, email, comment;
    private Context mContext;

    private View view;

    NavController navController;

    private FragmentSigninBinding fragmentSigninBinding;

    @Inject
    DataDao dataDao;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        configureDagger();
        mContext = context;
    }

    /**
     * Dagger Configuration
     */
    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentSigninBinding = FragmentSigninBinding.inflate(inflater, container, false);
        view = fragmentSigninBinding.getRoot();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt(Constant.ID);
            first_name = bundle.getString(Constant.FIRST_NAME);
            last_name = bundle.getString(Constant.LAST_NAME);
            email = bundle.getString(Constant.EMAIL);
            comment = bundle.getString(Constant.COMMENT);

            fragmentSigninBinding.firstNameValueTview.setText(first_name);
            fragmentSigninBinding.lastNameValueTview.setText(last_name);
            fragmentSigninBinding.emailValueTview.setText(email);
            fragmentSigninBinding.commentEditText.setText(comment);
        }

        fragmentSigninBinding.relativeBack.setOnClickListener(v-> {
//            Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
            navController.navigate(R.id.action_signupFragment_to_loginFragment);
        });

        fragmentSigninBinding.commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null) {
                    fragmentSigninBinding.submitBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        fragmentSigninBinding.submitBtn.setOnClickListener(v-> {
            String comments = fragmentSigninBinding.commentEditText.getText().toString();
            if (comments.length()>0) {
                dataDao.updateData(comments, id);
                navController.navigate(R.id.action_signupFragment_to_loginFragment);
            }
        });

        return view;
    }
}
