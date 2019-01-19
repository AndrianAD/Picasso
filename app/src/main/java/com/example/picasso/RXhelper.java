package com.example.picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.schedulers.Schedulers;


public class RXhelper {
    static List<String> userName = Arrays.asList("Andrian", "Irina", "Timoshka");

    public static Observable<ArrayList> getList() {
        Observable observable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            observable = Observable.fromArray(userName)
                    .subscribeOn(Schedulers.newThread())
                    .map(user -> user.stream().map(String::toUpperCase).collect(Collectors.toList()))
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return observable;

    }
}


