package org.jasig.cas.web.captcha;

import com.octo.captcha.service.image.ImageCaptchaService;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class CaptchaImageCreateController implements Controller, InitializingBean {
    private ImageCaptchaService jcaptchaService;

    public CaptchaImageCreateController() {
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String id = request.getRequestedSessionId();

        BufferedImage bi = jcaptchaService.getImageChallengeForID(id);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        return null;
    }

    public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
        this.jcaptchaService = jcaptchaService;
    }

    public void afterPropertiesSet() throws Exception {
        if (jcaptchaService == null)
            throw new RuntimeException("Image captcha service wasn`t set!");
        else
            return;
    }
}