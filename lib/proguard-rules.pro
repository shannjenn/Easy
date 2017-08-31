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

-libraryjars 'E:\Program Files\Java\jdk1.8.0_20\jre\lib\rt.jar'
-libraryjars 'E:\Android\sdk\platforms\android-20\android.jar'

-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontusemixedcaseclassnames

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

###application
-keep class com.jen.easy.app.EasyApplication {
    native <methods>;
    public <fields>;
    public <methods>;
    protected <methods>;
}
-keep class com.jen.easy.app.EasyVersion {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyMain {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyMouse$* {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyClass$* {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyFinal$* {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyListener$* {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyFactory$* {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.EasyUtil {
    public <fields>;
    public <methods>;
}
###application


###控件绑定模块
-keep class com.jen.easy.bind.imp.BindImp {
    public <fields>;
    public <methods>;
}
###控件绑定模块

###网络模块
-keep class com.jen.easy.http.imp.HttpImp {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.parse.imp.HttpParseImp {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.HttpParam$* {
    public <fields>;
    public <methods>;
}
###网络模块

###Log模块
-keep class com.jen.easy.log.Logcat {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.log.EasyLog {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.log.imp.LogcatHelperImp {
    public <fields>;
    public <methods>;
}
###Log模块

###数据库模块
-keep class com.jen.easy.sqlite.imp.DBHelperImp {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.sqlite.imp.DBDaoImp {
    public <fields>;
    public <methods>;
}
###数据库模块

###切面编程
-keep class com.jen.easy.aop.imp.DynamicProxyImp {
    public <fields>;
    public <methods>;
}
###切面编程

###数据存储Share
-keep class com.jen.easy.share.imp.ShareImp {
    public <fields>;
    public <methods>;
}
###数据存储Share

###工具类
-keep class com.jen.easy.util.imp.DataFormatImp {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.util.imp.StringToListImp {
    public <fields>;
    public <methods>;
}
###工具类