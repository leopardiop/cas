package org.jasig.cas.web.captcha;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.awt.image.ImageFilter;

/**
 * Created by peng.luwei on 2014/11/15.
 */
public class SimpleListImageCaptchaEngine extends ListImageCaptchaEngine {

    private static final String RAMDOM_VALUE = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final int IMAGE_CAPTCHA_WIDTH = 40;
    private static final int IMAGE_CAPTCHA_HEIGHT = 40;


    @Override
    protected void buildInitialFactories() {

        // 图片和字体大小设置
        int minWordLength = 5;
        int maxWordLength = 5;
        int fontSize = 20;
        int imageWidth = 100;
        int imageHeight = 36;

        //create text parser
        TextPaster randomPaster = new DecoratedRandomTextPaster(new Integer(minWordLength),
                new Integer(maxWordLength), new SingleColorGenerator(Color.BLACK),
                new TextDecorator[] { new BaffleTextDecorator(new Integer(1), Color.orange) });

        ImageDeformation postDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation backDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation textDef = new ImageDeformationByFilters(
                new ImageFilter[]{});

        BackgroundGenerator background = new UniColorBackgroundGenerator(
                imageWidth, imageHeight, Color.white);
        FontGenerator font = new RandomFontGenerator(fontSize, fontSize,
                new Font[]{new Font("nyala", Font.BOLD, fontSize),
                        new Font("Bell MT", Font.PLAIN, fontSize),
                        new Font("Credit valley", Font.BOLD, fontSize)}
        );

        //create image captcha factory
/*
        ImageCaptchaFactory factory = new GimpyFactory(
                new RandomWordGenerator(RAMDOM_VALUE),
                new ComposedWordToImage(new TwistedRandomFontGenerator(new Integer(50),
                        new Integer(50)), new FunkyBackgroundGenerator(new Integer(300), new Integer(100)), randomPaster));
*/


        ImageCaptchaFactory factory = new GimpyFactory(
                new RandomWordGenerator(RAMDOM_VALUE),
                new DeformedComposedWordToImage(font,
                        background, randomPaster, backDef, textDef, postDef));


        ImageCaptchaFactory characterFactory[] = { factory};
        this.addFactories(characterFactory);
    }
}
