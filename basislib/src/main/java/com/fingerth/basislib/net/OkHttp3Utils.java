package com.fingerth.basislib.net;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ======================================================
 * Created by admin  on /08/07.
 * <p>
 * <p/>
 */
public class OkHttp3Utils {

    public void get() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().get().url("").build();

        Call call = client.newCall(request);

        //1.
        try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void post() {
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .build();

        Request request = new Request.Builder().post(formBody).url("").build();

        Call call = client.newCall(request);

        //1.
        try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void post2(String jsonStr) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "{username:admin;password:admin}");
        Request request = new Request.Builder().post(body).url("").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void postFile(File file) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("application/octet-stream"), file);


    }

    public void postFrom(File file) {
        RequestBody muiltipartBody = new MultipartBody.Builder()
                //一定要设置这句
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "admin")//
                .addFormDataPart("password", "admin")//
                .addFormDataPart("myfile", "1.png", RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
    }


    public void downGet() {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get().url("").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                int len = 0;
                File file = new File(Environment.getExternalStorageDirectory(), "n.png");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buff = new byte[128];
                while ((len = is.read(buff)) != -1) {
                    fos.write(buff, 0, len);
                }
                fos.flush();
                fos.close();
                is.close();
            }
        });
    }

    public void downPro(){
        OkHttpClient client = new OkHttpClient();
        FormBody form =new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .build();

         Request request = new Request.Builder().post(form).url("").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                long sum = 0L;
                //文件总大小
                final long total = response.body().contentLength();
                int len = 0;
                File file  = new File(Environment.getExternalStorageDirectory(), "n.png");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[128];
                while ((len = is.read(buf)) != -1){
                    fos.write(buf, 0, len);
                    //每次递增
                    sum += len;

                    final long finalSum = sum;
                    Log.d("pyh1", "onResponse: " + finalSum + "/" + total);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //将进度设置到TextView中
//                            contentTv.setText(finalSum + "/" + total);
//                        }
//                    });
                }
                fos.flush();
                fos.close();
                is.close();
            }
        });
    }

    public void uploadPro(File file){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        //使用我们自己封装的类
        CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long byteWritted, long contentLength) {
                //打印进度
                Log.d("pyh", "进度 ：" + byteWritted + "/" + contentLength);
            }
        });

        Request request = new Request.Builder().post(countingRequestBody).url("").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
