-dontobfuscate

-keep class org.tasks.** { *; }

# remove logging statements
-assumenosideeffects class timber.log.Timber* {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
}

# guava
-dontwarn sun.misc.Unsafe
-dontwarn java.lang.ClassValue
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn com.google.j2objc.annotations.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn com.google.errorprone.annotations.CanIgnoreReturnValue
-dontwarn com.google.errorprone.annotations.concurrent.LazyInit
-dontwarn com.google.errorprone.annotations.ForOverride

# https://github.com/square/okhttp/blob/0b74bba08805c28f6aede626cf06f213ef6480f2/README.md
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# https://gitlab.com/bitfireAT/davdroid/blob/9fc3921b3293e19bd7be7bfc3f24d799ed2446bc/app/proguard-rules.txt
-dontwarn aQute.**
-dontwarn groovy.**                       # Groovy-based ContentBuilder not used
-dontwarn javax.cache.**                  # no JCache support in Android
-dontwarn net.fortuna.ical4j.model.**
-dontwarn org.codehaus.groovy.**
-dontwarn org.apache.log4j.**             # ignore warnings from log4j dependency
-keep class net.fortuna.ical4j.** { *; }  # keep all model classes (properties/factories, created at runtime)
-keep class at.bitfire.** { *; }       # all DAVdroid code is required

# https://github.com/google/google-api-java-client-samples/blob/34c3b43cb15f4ee1b636a0e01521cc81a2451dcd/tasks-android-sample/proguard-google-api-client.txt
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}
-dontwarn com.google.api.client.extensions.android.**
-dontwarn com.google.api.client.googleapis.extensions.android.**
-dontwarn com.google.android.gms.**
-dontnote java.nio.file.Files, java.nio.file.Path
-dontnote **.ILicensingService
-dontnote sun.misc.Unsafe
-dontwarn sun.misc.Unsafe