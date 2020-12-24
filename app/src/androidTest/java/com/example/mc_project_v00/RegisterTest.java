package com.example.mc_project_v00;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class RegisterTest {

    private static final String TAG = "Instrumented test";

    @Test
    public void testRegister() throws InterruptedException {
        Log.d(TAG, "Starting resgister test");
        Context appContext = InstrumentationRegistry.getInstrumentation().getContext();

        CountDownLatch lock = new CountDownLatch(1);

        ComunicationController cc = new ComunicationController(appContext);
        cc.register(response -> {
            Log.d(TAG, "resposne OK: " + response.toString());
            lock.countDown();
        }, error -> {
            Log.d(TAG, "resposne KO: " + error.toString());
            lock.countDown();
            fail("Register should always be ok");
        });
        lock.await();
  }
}
