# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
# hide the original sourcePage file name.
#-renamesourcefileattribute SourceFile
-verbose
-ignorewarnings
-optimizationpasses 7
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-flattenpackagehierarchy
-dontpreverify
-printmapping mapping.txt
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-keepattributes *Annotation*
-keepclasseswithmembers class * {
    native <methods>;
}