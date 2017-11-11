package com.phuctv.englishpodcast;

import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void reactive_test() throws Exception {
        Single<List<String>> todosSingle = Single.create(new SingleOnSubscribe<List<String>>() {
            @Override
            public void subscribe(final SingleEmitter<List<String>> emitter) throws Exception {
                List<String> todosFromWeb = new ArrayList<>();
                todosFromWeb.add("Test 1");
                todosFromWeb.add("Test 2");
                todosFromWeb.add("Test 3");

                System.out.println("I am only called once!");

                emitter.onSuccess(todosFromWeb);
            }
        });
        Single<List<String>> cachedSingle = todosSingle.cache();

        cachedSingle.subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                System.out.println(strings);
            }
        });


        cachedSingle.subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                System.out.println(strings);
            }
        });
    }
}