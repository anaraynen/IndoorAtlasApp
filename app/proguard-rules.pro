# Add any project specific keep options here:
-keepattributes Signature
-keepclassmembers class com.mignot.kumar.androidMapAmeerCW.models.** {
  *;
}
# Required by IndoorAtlas SDK
-keep public class com.indooratlas.algorithm.ClientProcessingManager { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
