package life.majiang.community.provider.dto;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class UcloundProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publickey;
    @Value("${ucloud.ufile.private-key}")
    private String privatekey;
    ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publickey, privatekey);
    ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");

    public String upload(File fileStream, String mimeType, String fileName) {
        File file = new File("your file path");

        String generateFileName = "";
        String[] filePaths = fileName.split(".");
        if (filePaths.length > 1) {
            generateFileName = UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }else {
            return null;
        }

        try {
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket("maven")
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
        } catch (UfileClientException e) {
            e.printStackTrace();
        } catch (UfileServerException e) {
            e.printStackTrace();
        }

        return generateFileName;
    }
}
