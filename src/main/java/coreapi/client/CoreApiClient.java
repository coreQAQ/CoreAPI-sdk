package coreapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import coreapi.model.BaseResponse;
import coreapi.model.dto.ValidateRequest;
import coreapi.model.entity.IpDetail;
import coreapi.model.entity.PhoneLocation;
import coreapi.model.entity.ValidateResult;
import coreapi.util.SignUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.*;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
public class CoreApiClient {

    private String accessKey;

    private String secretKey;

    private final String url = "http://1.15.122.131:8070/v1";

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
                .url(url + "/ip?ip=" + ip)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()){
            return mapper.readValue(response.body().string(), new TypeReference<BaseResponse<IpDetail>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取手机号归属地
     *
     * @param phone
     * @return
     */
    public BaseResponse<PhoneLocation> getPhoneLocation(String phone) {
        Request request = this.genReq()
                .url(url + "/phone?phone=" + phone)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()){
            return mapper.readValue(response.body().string(), new TypeReference<BaseResponse<PhoneLocation>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据格式校验
     *
     * @param validateRequest
     * @return
     */
    public BaseResponse<ValidateResult> regexValidate(ValidateRequest validateRequest) {
        String json;
        try {
            json = mapper.writeValueAsString(validateRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = this.genReq()
                .url(url + "/validate")
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()){
            return mapper.readValue(response.body().string(), new TypeReference<BaseResponse<ValidateResult>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
