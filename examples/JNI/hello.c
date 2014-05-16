#include <jni.h>
#include <stdio.h>
#include "Main.h"
 
// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT void JNICALL Java_Main_hello(JNIEnv *env, jobject thisObj) {
   printf("Hello World!\n");
   return;
}
