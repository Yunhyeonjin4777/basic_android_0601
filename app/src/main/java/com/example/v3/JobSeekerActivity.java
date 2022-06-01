package com.example.v3;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class JobSeekerActivity extends AppCompatActivity {

    //프래그먼트 객체생성
    BottomNavigationView bottomNavigationView;
    //Deque 새로운 자료구조
    Deque<Integer> integerDeque = new ArrayDeque<>(3);
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker);

        //프래그먼트찾기
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //add home fragment in deque list
        integerDeque.push(R.id.item_joblist);
        //load home fragment
        loadFragment(new Fragment_joblist());

        //바텀네비게이션에서 클릭이 되는 것을 메인액티비티가 알아야한다
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        int id = item.getItemId();
                        //Check condition
                        if (integerDeque.contains(id)){
                            //when deque list contains selected id
                            //deque list에 선택한 id가 포함된 경우
                            //check condition
                            if (id == R.id.item_joblist){
                                //when selected id is equal to home fragment id
                                //선택한 id == home fragment id가 같은 경우
                                //check condition
                                if (integerDeque.size() != 1){
                                    //when deque list size is not equal to 1
                                    //check condition
                                    if (flag){
                                        //when falg value is true
                                        //add home fragment in deque list
                                        integerDeque.addFirst(R.id.item_joblist);
                                        //set flag is equal to false
                                        flag = false;
                                    }
                                }
                            }
                            //remove selected id from deque list
                            integerDeque.remove(id);
                        }
                        //push selected id in deque list
                        integerDeque.push(id);
                        //load fragment
                        loadFragment(getFragment(item.getItemId()));
                        //return true
                        return true;
                    }
                }
        );
    }//onCreate
    //프래그먼트 불러오기
    private Fragment getFragment(int itemId) {
        switch (itemId){
            //joblist 눌렀을 때
            case R.id.item_joblist:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                //return home fragment
                return new Fragment_joblist();
            //applyjob 눌렀을 때
            case R.id.item_applyjob:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new Fragment_applyjob();
            //jobSeekermenu 눌렀을 때
            case R.id.item_jobSeekermenu:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new Fragment_jobseekermenu();
        }
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        //return home fragment
        return new Fragment_joblist();
    }

    private void loadFragment(Fragment fragment) {
        //FragmentManager객체를 리턴
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    //뒤로가기처리
    public void onBackPressed() {
        //이전 fragment으로 가다
        integerDeque.pop();
        //check condition
        if (!integerDeque.isEmpty()){
            //when deque list is not empty
            //load fragment
            loadFragment(getFragment(integerDeque.peek()));
        }else {
            //when deque list is empty
            //finish activity
            finish();
        }
    }

}
