package coreapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import coreapi.exception.ApiException;
import coreapi.exception.ErrorCode;
import coreapi.model.dto.ValidateRequest;
import coreapi.model.entity.IpDetail;
import coreapi.model.entity.PhoneLocation;
import coreapi.model.entity.ValidateResult;
import coreapi.util.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class CoreApiClient {

    private String accessKey;

    private String secretKey;

    private static final String baseUrl = "http://1.15.122.131:8070/v1";

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 构建请求头，用于认证
     * @return
     */
    private Headers buildHeaders() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return new Headers.Builder()
                .add("accessKey", accessKey)
                .add("nonce", UUID.randomUUID().toString())
                .add("timestamp", timestamp)
                .add("sign", SignUtil.genSign(timestamp, secretKey))
                .build();
    }

    /**
     * 用于 GET 请求拼接参数
     * @param path /ip
     * @param query
     * @return
     */
    private HttpUrl buildUrl(@NonNull String path, @NonNull Map<String, String> query) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl + path).newBuilder();
        for (Map.Entry<String, String> entry : query.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    /**
     * 构建 GET 请求
     * @param path
     * @param query
     * @return
     */
    private Request buildGet(@NonNull String path, Map<String, String> query) {
        HttpUrl httpUrl = buildUrl(path, query);
        Headers headers = buildHeaders();
        return new Request.Builder()
                .url(httpUrl)
                .headers(headers)
                .get()
                .build();
    }

    /**
     * 构建 POST 请求
     * @param path
     * @param body
     * @return
     */
    private Request buildPost(@NonNull String path, Object body) {
        String json;
        try {
            json = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Headers headers = buildHeaders();
        return new Request.Builder()
                .url(baseUrl + path)
                .headers(headers)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .build();
    }

    /**
     * 发送请求、处理异常
     * @param request
     * @param dataType IpDetail.class
     * @return
     */
    private <T> T exec(@NonNull Request request, Class<T> dataType) throws ApiException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // HTTP 状态码非 2xx 异常
                throw new ApiException(ErrorCode.OPERATION_ERROR, "请求失败，HTTP 状态码:" + response.code());
            }

            if (response.body() != null) {
                JsonNode root = mapper.readTree(response.body().byteStream());
                JsonNode code = root.path("code");
                JsonNode data = root.path("data");
                JsonNode message = root.path("message");

                // 无 code 节点，服务器响应有误
                if (code.isMissingNode())
                    throw new ApiException(ErrorCode.SYSTEM_ERROR);

                // code 不为 0，抛出异常
                if (code.asInt() != 0)
                    throw new ApiException(code.asInt(), message.asText());

                return mapper.readValue(data.toString(), dataType);
            }
            // body 为空，服务器响应有误
            throw new ApiException(ErrorCode.SYSTEM_ERROR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取ip详情
     *
     * @param ip
     * @return
     */
    public IpDetail getIpDetail(String ip) throws ApiException {
        Map<String, String> query = Map.of("ip", ip);
        return exec(buildGet("/ip", query), IpDetail.class);
    }

    /**
     * 获取手机号归属地
     *
     * @param phone
     * @return
     */
    public PhoneLocation getPhoneLocation(String phone) throws ApiException {
        Map<String, String> query = Map.of("phone", phone);
        return exec(buildGet("/phone", query), PhoneLocation.class);
    }

    /**
     * 数据格式校验
     *
     * @param validateRequest
     * @return
     */
    public ValidateResult regexValidate(ValidateRequest validateRequest) throws ApiException {
        return exec(buildPost("/validate", validateRequest), ValidateResult.class);
    }

}
