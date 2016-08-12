package com.plusub.lib.example.ui.tab1.loadview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.plusub.lib.example.R;

/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com> <br>
 * date     : 2016/5/24 15:05 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description:
 */
public class LoadActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_main);
    }

    public void showSimple(View view) {
        startActivity(new Intent(getApplicationContext(), SimpleActivity.class));
    }

    public void showExample1(View view) {
        startActivity(new Intent(getApplicationContext(), Example1Activity.class));
    }

    public void showExample2(View view) {
        startActivity(new Intent(getApplicationContext(), Example2Activity.class));
    }
}
