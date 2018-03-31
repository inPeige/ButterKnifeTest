package com.ruiyi.lib;

import com.google.auto.service.AutoService;
import com.ruiyi.annotaten.BundView;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by liupei on 2018/3/27.
 */
//注册
@AutoService(Processor.class)
//编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
//指定处理的注解
@SupportedAnnotationTypes(Consts.ANN_TYPE_BUNDVIEW)
public class BundleProcessor extends AbstractProcessor {
    /**
     * type(类信息)工具类
     */
    private Types typeUtils;
    /**
     * 文件生成器 类/资源
     */
    private Filer filer;
    private Messager messager;
    /**
     * 节点工具类 (类、函数、属性都是节点)
     */
    private Elements elementUtils;
    private Log log;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        log = Log.newLog(processingEnvironment.getMessager());
        typeUtils = processingEnvironment.getTypeUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        log.i("BundleProcessor:init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null) {
            HashMap<TypeElement, ArrayList<BundViewParams>> typeElementArrayListHashMap = processElement(roundEnvironment);
            Set<Map.Entry<TypeElement, ArrayList<BundViewParams>>> entries = typeElementArrayListHashMap.entrySet();
            for (Map.Entry<TypeElement, ArrayList<BundViewParams>> entry : entries) {
                TypeElement key = entry.getKey();
                ArrayList<BundViewParams> value = entry.getValue();
                creatJavaFile(key, value);
            }
            return true;
        }
        return false;
    }

    private void creatJavaFile(TypeElement key, ArrayList<BundViewParams> value) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(key.getSimpleName() + Consts.NAME);
        classBuilder.addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
        builder.addParameter(TypeName.get(key.asType()), "target");
        for (BundViewParams params : value) {
            String bundViewName = params.getBundViewName();
            int rid = params.getRid();
            builder.addCode("target." + bundViewName + "=target.getWindow().getDecorView().findViewById(" + rid + ");");
        }
        MethodSpec methodSpec = builder.build();
        classBuilder.addMethod(methodSpec);
        TypeSpec build = classBuilder.build();
        JavaFile javaFile = JavaFile.builder(Consts.PACKAGE_OF_GENERATE_FILE, build).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<TypeElement, ArrayList<BundViewParams>> processElement(RoundEnvironment roundEnvironment) {
        HashMap<TypeElement, ArrayList<BundViewParams>> typeElementObjectHashMap = new HashMap<>();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BundView.class);
        for (Element element : elements) {
            //拿到所有的注解Activity
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            ArrayList<BundViewParams> bundViewParams = typeElementObjectHashMap.get(enclosingElement);
            if (bundViewParams == null) {
                bundViewParams = new ArrayList<>();
                typeElementObjectHashMap.put(enclosingElement, bundViewParams);
                BundViewParams params = new BundViewParams();
                BundView annotation = element.getAnnotation(BundView.class);
                if (annotation != null) {
                    params.setRid(annotation.value());
                }
                params.setBundViewName(element.getSimpleName().toString());//获取view的name 如text1
                bundViewParams.add(params);
            } else {
                BundViewParams params = new BundViewParams();
                BundView annotation = element.getAnnotation(BundView.class);
                if (annotation != null) {
                    params.setRid(annotation.value());
                }
                params.setBundViewName(element.getSimpleName().toString());//获取view的name 如text1

                bundViewParams.add(params);
            }
            log.i("bundleProcessor:" + enclosingElement.getQualifiedName());
        }
        return typeElementObjectHashMap;
    }
}
