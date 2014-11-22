package org.jasig.cas.web.captcha;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.AbstractManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * Created by peng.luwei on 2014/11/15.
 */
public class SimpleImageCaptchaService extends
        AbstractManageableImageCaptchaService implements ImageCaptchaService {

    /*private static SimpleImageCaptchaService instance;

    public static SimpleImageCaptchaService getInstance() {
        if (instance == null) {
            //use customized engine
            ListImageCaptchaEngine engine = new SimpleImageCaptchaService();
            instance = new SimpleImageCaptchaService(
                    new FastHashMapCaptchaStore(), engine, 180, 100000, 75000);
        }
        return instance;
    }*/

    protected SimpleImageCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }
}
