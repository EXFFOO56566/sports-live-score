# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Aron\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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
-dontwarn com.squareup.**
-dontwarn org.apache.**
-dontwarn org.w3c.dom.**
-dontwarn org.openxmlformats.**
-dontwarn schemasMicrosoftComOfficeExcel.**
-dontwarn schemasMicrosoftComOfficeOffice.**
-dontwarn schemasMicrosoftComOfficeWord.**
-dontwarn schemasMicrosoftComVml.**
-dontwarn okio.**
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn retrofit.**
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** {*;}
-keep interface com.squareup.okhttp.** {*;}
-keep class com.crashlytics.** { *; }
-keep class org.apache.** { *; }
-keep class org.openxmlformats.** { *; }
-keep class schemaorg_apache_xmlbeans.** { *; }
-keep class com.google.firebase.*.* { *; }

#### -- Support Library --

# support-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }

# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class com.startapp.** {
      *;
}

-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

-dontwarn org.jetbrains.annotations.**
