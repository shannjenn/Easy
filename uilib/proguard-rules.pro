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

-libraryjars 'E:\Program Files\Java\jdk1.8.0_20\jre\lib\rt.jar'
-libraryjars 'E:\Android\sdk\platforms\android-20\android.jar'

-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontusemixedcaseclassnames

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

###版本信息
-keep class com.jen.easyui.EasyUIVersion {
    public <fields>;
    public <methods>;
}
###版本信息

###控件
-keep class com.jen.easyui.listview.EasyListView {
    public <fields>;
    public <methods>;
}
###控件
