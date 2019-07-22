# Add project specific ProGuard rules here.
# By default, the str in this file are appended to str specified
# in E:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#    native <methods>;
#    public <fields>;
#    public <methods>;
#    protected <methods>;

#-libraryjars 'E:\Program Files\Java\jdk1.8.0_20\jre\lib\rt.jar'
#-libraryjars 'E:\Android\sdk\platforms\android-20\android.jar'

-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontusemixedcaseclassnames#混淆时不会产生形形色色的类名
-dontoptimize    #不优化输入的类文件
#-assumenosideeffects {class com.jen.easy.EasyVersion}    #优化时假设指定的方法，没有任何副作用
-allowaccessmodification    #优化时允许访问并修改有修饰符的类和类的成员

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }
#泛型不混淆
-keepattributes Signature

##############################################################
###版本号
##############################################################
-keep class com.jen.easy.Version {
    *;
}

##############################################################
###注释类
##############################################################
-keep class com.jen.easy.EasyAopAfter {
    *;
}
-keep class com.jen.easy.EasyAopBefore {
    *;
}
-keep class com.jen.easy.EasyColumn {
    *;
}
-keep class com.jen.easy.EasyHttpGet {
    *;
}
-keep class com.jen.easy.EasyHttpPost {
    *;
}
-keep class com.jen.easy.EasyHttpPut {
    *;
}
-keep class com.jen.easy.EasyRequest {
    *;
}
-keep class com.jen.easy.EasyRequestType {
    *;
}
-keep class com.jen.easy.EasyRequestCommit {
    *;
}
-keep class com.jen.easy.EasyResponse {
    *;
}
-keep class com.jen.easy.invalid.EasyInvalid {
    *;
}
-keep class com.jen.easy.invalid.EasyInvalidType {
    *;
}
-keep class com.jen.easy.EasyTable {
    *;
}
-keep class com.jen.easy.EasyBindId {
    *;
}
-keep class com.jen.easy.EasyBindClick {
    *;
}

##############################################################
###控件绑定模块
##############################################################
-keep class com.jen.easy.bind.EasyBind {
    *;
}

##############################################################
###网络模块
##############################################################
-keep class com.jen.easy.http.EasyHttp {
    *;
}
-keep class com.jen.easy.http.request.EasyHttpRequest* {
    *;
}
-keep class com.jen.easy.http.request.EasyHttpDataRequest* {
     *;
}
-keep class com.jen.easy.http.request.EasyHttpDownloadRequest* {
    *;
}
-keep class com.jen.easy.http.request.EasyHttpUploadRequest* {
    *;
}
-keep class com.jen.easy.http.response.EasyHttpResponse* {
    *;
}
-keep class com.jen.easy.http.response.EasyHttpFileResponse* {
    *;
}
-keep class com.jen.easy.http.imp.EasyHttpListener {
    *;
}
-keep class com.jen.easy.http.EasyHttpTool {
    *;
}
-keep class com.jen.easy.http.request.EasyRequestState {
    *;
}

##############################################################
###Log模块
##############################################################
-keep class com.jen.easy.log.EasyLog {
    *;
}
-keep class com.jen.easy.log.LogLevel {
    *;
}
-keep class com.jen.easy.log.EasyUILog {
    *;
}
-keep class com.jen.easy.log.LogcatHelper {
    *;
}
-keep class com.jen.easy.log.LogcatLevel {
    *;
}
-keep class com.jen.easy.log.imp.LogcatListener {
    *;
}

##############################################################
###数据库模块
##############################################################
-keep class com.jen.easy.sqlite.EasyDBHelper {
    *;
}
-keep class com.jen.easy.sqlite.EasyTBDao {
    *;
}
-keep class com.jen.easy.sqlite.imp.DatabaseListener {
    *;
}

##############################################################
###切面编程
##############################################################
-keep class com.jen.easy.aop.DynamicProxy {
    *;
}

##############################################################
###数据存储Share
##############################################################
-keep class com.jen.easy.share.Shared {
    *;
}

##############################################################
###图片加载器
##############################################################
-keep class com.jen.easy.imageLoader.ImageLoader {
    *;
}
-keep class com.jen.easy.imageLoader.ImageLoaderConfig {
    *;
}