package com.example.picasso;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RXhelper {
    static List<String> users = Arrays.asList("Andrian", "Irina", "Timoshka");

    public static Observable<String> getList() {
        Observable observable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            observable = Observable.just("Andrian", "Irina", "Timoshka")
                    .subscribeOn(Schedulers.newThread())
                    .map(String::toUpperCase)
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return observable;

    }


    public static Observable<ArrayList> getList2() {
        Observable observable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            observable = Observable.fromArray(users)
                    .subscribeOn(Schedulers.newThread())
                    .map(user -> user.stream().map(String::toUpperCase).collect(Collectors.toList()))
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return observable;

    }

}


