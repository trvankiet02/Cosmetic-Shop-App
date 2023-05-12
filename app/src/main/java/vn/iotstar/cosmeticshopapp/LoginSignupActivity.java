package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import vn.iotstar.cosmeticshopapp.adapter.ViewPagerAdapter;
import vn.iotstar.cosmeticshopapp.fragment_login_signup.LoginFragment;
import vn.iotstar.cosmeticshopapp.fragment_login_signup.SignupFragment;

public class LoginSignupActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter loginSignupAdapter;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        AnhXa();
        setTabLayout();


    }

    private void AnhXa(){
        //component
        tabLayout = (TabLayout) findViewById(R.id.tlLoginSignup);
        viewPager = (ViewPager) findViewById(R.id.vpLoginSignup);
        //adapter
        loginSignupAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        LoginFragment loginFragment = new LoginFragment();
        SignupFragment signupFragment = new SignupFragment();
        loginSignupAdapter.addFragment(loginFragment, "Đăng nhập");
        loginSignupAdapter.addFragment(signupFragment, "Đăng ký");

        signupFragment.setOnButtonClickListener(new SignupFragment.OnButtonClickListener() {
            @Override
            public void onButtonClick() {
                viewPager.setCurrentItem(0);
            }
        });
    }

    private void setTabLayout(){
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(loginSignupAdapter);
        loginSignupAdapter.notifyDataSetChanged();


    }
}