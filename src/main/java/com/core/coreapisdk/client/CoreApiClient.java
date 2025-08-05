package com.core.coreapisdk.client;

import com.core.coreapisdk.model.BaseResponse;
import com.core.coreapisdk.model.IpDetail;
import com.core.coreapisdk.util.SignUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
public class CoreApiClient {

    private String accessKey;

    private String secretKey;

    private final OkHttpClient httpClient = new OkHttpClient();

    private final ObjectMapper mapper = new ObjectMapper();

    private Request.Builder genReq() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return new Request.Builder()
                .header("accessKey", accessKey)
                .header("nonce", UUID.randomUUID().toString())
                .header("timestamp", timestamp)
                .header("sign", SignUtil.genSign(timestamp, secretKey));
    }

    /**
     * 获取ip详情
     *
     * @param ip
     * @return
     */
    public BaseResponse<IpDetail> getIpDetail(String ip) {
        Request request = this.genReq()
                .url("http://localhost:8070/v1/ip?ip=" + ip)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()){
            return mapper.readValue(response.body().string(), new TypeReference<BaseResponse<IpDetail>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
