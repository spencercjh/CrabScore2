package top.spencer.crabscore.common.util;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * https://blog.csdn.net/qq_37664986/article/details/80288763
 *
 * @author spencercjh
 */
public class QiNiuUploadUtil {
    private static String accessKey;
    private static String secretKey;
    private static String bucketName;
    private final static String MAC_NAME = "HmacSHA1";
    private final static String ENCODING = "UTF-8";
    private static Configuration configuration;

    /**
     * 初始化
     *
     * @param accessKey  accesskey
     * @param secretKey  secret
     * @param bucketName bucket
     */
    public static void init(String accessKey, String secretKey, String bucketName) {
        QiNiuUploadUtil.accessKey = accessKey;
        QiNiuUploadUtil.secretKey = secretKey;
        QiNiuUploadUtil.bucketName = bucketName;
        configuration = new Configuration.Builder().build();
    }

    /**
     * 上传图片
     * <p>
     *
     * @param keys     上传到空间后的文件名的名字
     * @param path     上传文件的路径地址
     * @param callBack 回调接口
     */
    public static void uploadPicture(final String path, final String keys, final UploadCallBack callBack) {
        try {
            JSONObject json = new JSONObject();
            long delayTimes = 3029414400L;
            json.put("deadline", delayTimes);
            json.put("scope", bucketName);
            String encodedPutPolicy = UrlSafeBase64.encodeToString(json.toString().getBytes());
            byte[] sign = hmacSha1Encrypt(encodedPutPolicy, secretKey);
            String encodedSign = UrlSafeBase64.encodeToString(sign);
            final String uploadToken = accessKey + ':' + encodedSign + ':' + encodedPutPolicy;
            UploadManager uploadManager = new UploadManager(configuration);
            uploadManager.put(path, keys, uploadToken, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        callBack.success(key);
                    } else {
                        callBack.fail(key, info);
                    }
                }
            }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return byte[]
     * @throws Exception exception
     */
    private static byte[] hmacSha1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        javax.crypto.SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        return mac.doFinal(text);
    }

    /**
     * call back
     */
    public interface UploadCallBack {
        /**
         * success call back
         *
         * @param url http://spencercjh.top/{url}
         */
        void success(String url);

        /**
         * fail call back
         *
         * @param key  code
         * @param info message
         */
        void fail(String key, ResponseInfo info);
    }
}

