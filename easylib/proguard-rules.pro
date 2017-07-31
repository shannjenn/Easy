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
-dontusemixedcaseclassnames

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

#application
-keep class com.jen.easy.app.EasyApplication {
    native <methods>;
    public <fields>;
    public <methods>;
}

###控件绑定模块
-keep class com.jen.easy.bind.EasyBind {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.bind.imp.EasyBindID {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.bind.imp.EasyBindMethod {
    public <fields>;
    public <methods>;
}
###控件绑定模块

###常量
-keep class com.jen.easy.constant.FieldType {
    public <fields>;
    public <methods>;
}
###常量

###网络模块
-keep class com.jen.easy.http.EasyHttp {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.EasyHttpCode {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.EasyParse {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.imp.EasyHttpModelName {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.imp.EasyHttpParamName {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.listener.EasyHttpDownloadListener {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.listener.EasyHttpListener {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.listener.EasyHttpUploadListener {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.param.EasyHttpBaseParam {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.param.EasyHttpDownloadParam {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.param.EasyHttpUploadParam {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.param.FinalBaseParam {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.param.FinalDownloadParam {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.http.param.FinalUploadParam {
    public <fields>;
    public <methods>;
}
###网络模块

###Log模块
-keep class com.jen.easy.log.Logcat {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.log.LogcatHelper {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.log.listener.LogcatCrashListener {
    public <fields>;
    public <methods>;
}
###Log模块

###数据库模块
-keep class com.jen.easy.sqlite.DBHelper {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.sqlite.EasyDBDao {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.sqlite.imp.EasyColumn {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.sqlite.imp.EasyTable {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.sqlite.listener.DatabaseListener {
    public <fields>;
    public <methods>;
}
###数据库模块

###其他
-keep class com.jen.easy.util.DataFormat {
    public <fields>;
    public <methods>;
}
-keep class com.jen.easy.app.EasyVersion {
    public <fields>;
    public <methods>;
}
###其他
