package com.mzw.image.glide.config;

import android.support.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpStreamFetcher implements DataFetcher<InputStream> {

    private InputStream stream = null;

    private ResponseBody responseBody = null;

    private Call call = null;

    private Call.Factory factory = null;
    private GlideUrl url = null;

    public OkHttpStreamFetcher(Call.Factory factory, GlideUrl url) {
        this.factory = factory;
        this.url = url;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull final DataCallback<? super InputStream> callback) {
        Request.Builder builder = new Request.Builder().url(url.toStringUrl());
        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            builder.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }
        Request request = builder.build();
        call = factory.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onLoadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseBody = response.body();
                if (response.isSuccessful()) {
                    assert responseBody != null;
                    long contentLength = responseBody.contentLength();
                    stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
                    callback.onDataReady(stream);
                } else {
                    callback.onLoadFailed(new HttpException(response.message(), response.code()));
                }
            }
        });
    }

    @Override
    public void cleanup() {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        responseBody.close();
    }

    @Override
    public void cancel() {
        Call localCall = call;
        if (localCall != null) {
            localCall.cancel();
        }
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
