package com.ruiyi.lib;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by liupei on 2018/3/28.
 */

/**
 * 注册 类似于在activity的注册
 * 我们可以引入implementation 'com.google.auto.service:auto-service:1.0-rc2'
 * 谷歌提供的第三方注册框架完成注册
 * 声明这个类注册的是Processor.class
 */
@AutoService(Processor.class)

@SupportedSourceVersion(SourceVersion.RELEASE_7)//编译版本
@SupportedAnnotationTypes("注解的全类名")//定义可以处理的注解 如：com.ruiyi.annotaten.BundView
public class TestPRocessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    /**
     * 核心处理类
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
