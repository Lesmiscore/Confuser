package com.nao20010128nao.confuser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.*;

import org.apache.commons.math3.random.MersenneTwister;

/**
* http://mike-neck.hatenadiary.com/entry/2014/08/28/194306
* http://fits.hatenablog.com/entry/2015/01/17/172651
* http://qiita.com/opengl-8080/items/beda51fe4f23750c33e9
* */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.nao20010128nao.confuser.WillConfuse","com.nao20010128nao.confuser.NestedOverridden"})
public class ConfusionProcessor extends AbstractProcessor {
    Set<String> usedClassNames=new HashSet<>(100);

    @Override
    public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnvironment) {
        for (TypeElement classes : roundEnvironment.getElementsAnnotatedWith(WillConfuse.class).stream()
            .filter(a->a.getKind()==ElementKind.CLASS)
            .filter(a->a instanceof TypeElement)
            .map(a->(TypeElement)a)
            .collect(Collectors.toList())) {
            String pkgName=classes.toString();
            if(pkgName.contains("."))
                pkgName=pkgName.substring(0,pkgName.lastIndexOf("."))+".";
            else
                pkgName="";

            WillConfuse opts=classes.getAnnotation(WillConfuse.class);
            int modifier=opts.modifier();
            int nest=opts.nest();
            if(nest<=0){
                // 10..110
                nest=Math.abs(new SecureRandom().nextInt())%101+10;
            }
            String className=opts.value();
            boolean printNest=opts.printNest();
            String randomSeed=opts.randomSeed();

            MersenneTwister twister;
            if("".equals(randomSeed)){
                twister=new MersenneTwister();
            }else{
                twister=new MersenneTwister(randomSeed.hashCode());
            }

            Filer filer=processingEnv.getFiler();
            List<String> middleClassNames=createNumberedStream(nest).map(a->twister).map(this::nextClassName).collect(Collectors.toList());
            List<String> finalClasses=Stream.concat(Stream.of(className),middleClassNames.stream()).collect(Collectors.toList());
            List<String> extendFrom=Stream.concat(middleClassNames.stream(),Stream.of(classes.getSimpleName().toString())).map(pkgName::concat).collect(Collectors.toList());

            try(Writer javaFile=new BufferedWriter(filer.createSourceFile(pkgName+className).openWriter())){
                javaFile.append("/* AUTO GENERATED FILE. DO NOT CHANGE. */\n");
                if (!"".equals(pkgName)) {
                    javaFile.append("package ").append(pkgName).append(';').append('\n');
                }
                for(int i=0;i<finalClasses.size();i++){
                    if(printNest){
                        javaFile.append("/* ")
                            .append("No. ").append(String.valueOf(i+1)).append(" nesting from user, ")
                            .append("No. ").append(String.valueOf(finalClasses.size()-i)).append(" nesting from the class.")
                            .append(" */");
                    }
                    if(i==0){
                        // first should follow the annotation
                        if((modifier& Modifier.PUBLIC)!=0){
                            javaFile.append("public ");
                        }else if((modifier& Modifier.PROTECTED)!=0){
                            javaFile.append("protected ");
                        }else{
                            javaFile.append("/* package */ ");
                        }
                        if((modifier& Modifier.ABSTRACT)!=0){
                            javaFile.append("abstract ");
                        }else if((modifier& Modifier.FINAL)!=0){
                            javaFile.append("final ");
                        }else{
                            javaFile.append("/* no extend limitation */ ");
                        }
                    }else{
                        javaFile.append("abstract ");
                    }
                    javaFile.append("class ").append(finalClasses.get(i));
                    javaFile.append(" extends ").append(extendFrom.get(i));
                    javaFile.append("{\n");
                    /* Class body */
                    for(ExecutableElement constructor:classes.getEnclosedElements().stream()
                        .filter(a->a.getKind()==ElementKind.CONSTRUCTOR)
                        .filter(a->a instanceof ExecutableElement)
                        .map(a->(ExecutableElement)a)
                        .filter(a->a.getAnnotation(NestedOverridden.class)!=null)
                        .collect(Collectors.toList())){
                        NestedOverridden params=constructor.getAnnotation(NestedOverridden.class);

                        int methodModifier=params.modifier();

                        javaFile.append("    ");
                        if(i==0){
                            // first should follow the annotation
                            if((methodModifier& Modifier.PUBLIC)!=0){
                                javaFile.append("public ");
                            }else if((methodModifier& Modifier.PROTECTED)!=0){
                                javaFile.append("protected ");
                            }else{
                                javaFile.append("/* package */ ");
                            }
                            if((methodModifier& Modifier.ABSTRACT)!=0){
                                javaFile.append("abstract ");
                            }else if((methodModifier& Modifier.FINAL)!=0){
                                javaFile.append("final ");
                            }else{
                                javaFile.append("/* no extend limitation */ ");
                            }
                        }else{
                            for(javax.lang.model.element.Modifier childMethodModifier:constructor.getModifiers()){
                                javaFile.append(childMethodModifier.toString()).append(" ");
                            }
                        }
                        javaFile.append(finalClasses.get(i)).append("(");
                        String[] types=constructor.getParameters().stream()
                            .map(VariableElement::asType)
                            .map(this::typeMirrorToClassName)
                            .toArray(String[]::new);
                        int id=0;
                        for(String type:types){
                            javaFile.append(type).append(" arg").append(String.valueOf(id));
                            id++;
                            if(id!=types.length){
                                javaFile.append(',');
                            }
                        }
                        javaFile.append("){\n");
                        javaFile.append("        super(");
                        for(int j=0;j<types.length;j++){
                            javaFile.append("arg").append(String.valueOf(j));
                            if(j!=types.length-1){
                                javaFile.append(',');
                            }
                        }
                        javaFile.append(");\n");
                        javaFile.append("    }\n");
                    }
                    for(ExecutableElement method:classes.getEnclosedElements().stream()
                        .filter(a->a.getKind()==ElementKind.METHOD)
                        .filter(a->a instanceof ExecutableElement)
                        .map(a->(ExecutableElement)a)
                        .filter(a->a.getAnnotation(NestedOverridden.class)!=null)
                        .collect(Collectors.toList())){
                        NestedOverridden params=method.getAnnotation(NestedOverridden.class);

                        int methodModifier=params.modifier();
                        boolean retReq=params.returnRequired();

                        javaFile.append("    ");
                        if(i==0){
                            // first should follow the annotation
                            if((methodModifier& Modifier.PUBLIC)!=0){
                                javaFile.append("public ");
                            }else if((methodModifier& Modifier.PROTECTED)!=0){
                                javaFile.append("protected ");
                            }else{
                                javaFile.append("/* package */ ");
                            }
                            if((methodModifier& Modifier.ABSTRACT)!=0){
                                javaFile.append("abstract ");
                            }else if((methodModifier& Modifier.FINAL)!=0){
                                javaFile.append("final ");
                            }else{
                                javaFile.append("/* no override limitation */ ");
                            }
                        }else{
                            for(javax.lang.model.element.Modifier childMethodModifier:method.getModifiers()){
                                javaFile.append(childMethodModifier.toString()).append(" ");
                            }
                        }
                        /* Return type */
                        javaFile.append(typeMirrorToClassName(method.getReturnType())).append(' ');
                        /* Name and arg brace */
                        javaFile.append(method.getSimpleName()).append("(");
                        String[] types=method.getParameters().stream()
                            .map(VariableElement::getConstantValue)
                            .map(processingEnv.getElementUtils()::getConstantExpression)
                            .toArray(String[]::new);
                        int id=0;
                        for(String type:types){
                            javaFile.append(type).append(" arg").append(String.valueOf(id));
                            id++;
                            if(id!=types.length){
                                javaFile.append(',');
                            }
                        }
                        javaFile.append("){\n");
                        javaFile.append("        ");
                        if(retReq){
                            javaFile.append("return ");
                        }
                        javaFile.append("super.").append(method.getSimpleName()).append("(");
                        for(int j=0;j<types.length;j++){
                            javaFile.append("arg").append(String.valueOf(j));
                            if(j!=types.length-1){
                                javaFile.append(',');
                            }
                        }
                        javaFile.append(");\n");
                        javaFile.append("    }\n");
                    }
                    /* Class end */
                    javaFile.append("}\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    String nextClassName(MersenneTwister generator){
        byte[] bytes=new byte[1024];
        generator.nextBytes(bytes);
        String name="_"+ UUID.nameUUIDFromBytes(bytes).toString().replace("-","").toUpperCase();
        if(usedClassNames.contains(name)) {
            return nextClassName(generator);
        } else{
            usedClassNames.add(name);
            return name;
        }
    }

    Stream<Object> createNumberedStream(int limit){
        return Stream.generate(Object::new).limit(limit);
    }

    String typeMirrorToClassName(TypeMirror input){
        if(input instanceof ArrayType){
            return typeMirrorToClassName(((ArrayType) input).getComponentType())+"[]";
        }else if(input instanceof DeclaredType){
            return ((DeclaredType) input).asElement().toString();
        }else if(input instanceof PrimitiveType){
            return input.toString();
        }else if(input instanceof NoType){
            return "void";
        }else{
            return "null";
        }
    }

    String[] removeRedundantArgs(String[] input){
        return Stream.of(input)
            .filter(Objects::nonNull)
            .toArray(String[]::new);
    }

    String[] internalClassFormToClassNames(String input){
        char[] chars=input.toCharArray();
        StringBuilder classNameTmp=new StringBuilder();
        boolean classNameNow=false;
        int arrayDim=0;
        List<String> results=new ArrayList<>();
        for(int i=0;i<chars.length;i++){
            if(classNameNow){
                if(chars[i]==';'){
                    classNameNow=false;
                    results.add((classNameTmp+repeat("[]",arrayDim)).replace('/','.'));
                    arrayDim=0;
                    continue;
                }
                classNameTmp.append(chars[i]);
            }else{
                switch (chars[i]){
                    case 'B':
                        results.add("boolean"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'C':
                        results.add("char"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'D':
                        results.add("double"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'F':
                        results.add("float"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'I':
                        results.add("int"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'J':
                        results.add("long"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'S':
                        results.add("short"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'Z':
                        results.add("boolean"+repeat("[]",arrayDim));
                        arrayDim=0;
                        break;
                    case 'L':
                        classNameNow=true;
                        classNameTmp.setLength(0);
                        break;
                    case '[':
                        arrayDim++;
                        break;
                }
            }
        }
        return results.toArray(new String[results.size()]);
    }

    String repeat(String str,int times){
        StringBuilder sb=new StringBuilder(str.length()*times);
        for(int i=0;i<times;i++){
            sb.append(str);
        }
        return sb.toString();
    }
}
