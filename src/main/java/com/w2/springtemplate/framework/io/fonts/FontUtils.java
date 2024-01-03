package com.w2.springtemplate.framework.io.fonts;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontUtils {

    private static final String FONT_RESOURCE_LOCATION = "vfs://fonts/";

    private static final String MI_FONT_L3_OTF = "MiSans L3.otf";

    private static final String MI_FONT_L3_TTF = "MiSans L3.ttf";

    private static final String MI_FONT_TTF = "MiSans-Light.ttf";

    private final ResourceLoader resourceLoader;

    public FontUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public InputStream getMIFontL3OTF(){
        Resource resource = resourceLoader.getResource(FONT_RESOURCE_LOCATION + MI_FONT_L3_OTF);
        try {
            InputStream inputStream = resource.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public InputStream getMIFontL3TTF(){
        Resource resource = resourceLoader.getResource(FONT_RESOURCE_LOCATION + MI_FONT_L3_TTF);
        try {
            InputStream inputStream = resource.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public File getMIFontL3TTFFile(){
        Resource resource = resourceLoader.getResource(FONT_RESOURCE_LOCATION + MI_FONT_L3_TTF);
        try {
            File file = resource.getFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getMIFontTTF(){
        Resource resource = resourceLoader.getResource(FONT_RESOURCE_LOCATION + MI_FONT_TTF);
        try {
            InputStream inputStream = resource.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public File getMIFontTTFFile(){
        Resource resource = resourceLoader.getResource(FONT_RESOURCE_LOCATION + MI_FONT_TTF);
        try {
            File file = resource.getFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
