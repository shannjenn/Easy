# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
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

-libraryjars 'E:\Program Files\Java\jdk1.8.0_20\jre\lib\rt.jar'
-libraryjars 'E:\Android\sdk\platforms\android-20\android.jar'

-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontusemixedcaseclassnames

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

##############################################################
###application
##############################################################
-keep class com.jen.easy.app.EasyApplication {
    *;
}
-keep class com.jen.easy.app.EasyVersion {
    *;
}
-keep class com.jen.easy.EasyMain {
    *;
}
-keep class com.jen.easy.EasyMouse$* {
    *;
}
-keep class com.jen.easy.EasyUtil {
    *;
}

##############################################################
###控件绑定模块
##############################################################
-keep class com.jen.easy.bind.Bind {
    *;
}

##############################################################
###网络模块
##############################################################
-keep class com.jen.easy.http.Http {
    *;
}
-keep class com.jen.easy.http.HttpParam$* {
    *;
}
-keep class com.jen.easy.http.BaseParamRequest$* {
    *;
}
-keep class com.jen.easy.http.DownloadParamRequest$* {
    *;
}
-keep class com.jen.easy.http.UploadParamRequest$* {
    *;
}
-keep class com.jen.easy.http.imp.HttpBaseListener {
    *;
}
-keep class com.jen.easy.http.imp.HtppDownloadListener {
    *;
}
-keep class com.jen.easy.http.imp.HttpUploadListener {
    *;
}

##############################################################
###Log模块
##############################################################
-keep class com.jen.easy.log.Logcat {
    *;
}
-keep class com.jen.easy.log.EasyLog {
    *;
}
-keep class com.jen.easy.log.LogcatHelper {
    *;
}
-keep class com.jen.easy.log.imp.CrashListener {
    *;
}

##############################################################
###数据库模块
##############################################################
-keep class com.jen.easy.sqlite.DBHelper {
    *;
}
-keep class com.jen.easy.sqlite.DBDao {
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
###文件加密解密
##############################################################
-keep class com.jen.easy.decrypt.FileDecrypt {
    *;
}

##############################################################
###数据存储Share
##############################################################
-keep class com.jen.easy.share.Share {
    *;
}

##############################################################
###工具类
##############################################################
-keep class com.jen.easy.util.DataFormat {
    *;
}
-keep class com.jen.easy.util.StringToList {
    *;
}