#include <windows.h>
#include "injector.h"
#include "jvm/jni.h"

#if __has_include("classes/injector.h")
#include "classes/injector.h"
#else
#include "stub_classes/injector.h"
#endif

#if __has_include("classes/jar.h")
#include "classes/jar.h"
#else
#include "stub_classes/jar.h"
#endif

HMODULE global_dll_instance = nullptr;

static JavaVM* GetJVM() {
  HMODULE jvm_dll = GetModuleHandleW(L"jvm.dll");
  if (!jvm_dll) {
    return nullptr;
  }

  typedef jint(JNICALL * GetCreatedJavaVMs_t)(JavaVM**, jsize, jsize*);
  GetCreatedJavaVMs_t GetCreatedJavaVMs = (GetCreatedJavaVMs_t)GetProcAddress(jvm_dll, "JNI_GetCreatedJavaVMs");
  if (!GetCreatedJavaVMs) {
    return nullptr;
  }

  JavaVM* jvm;
  jsize jvm_count;
  jint result = GetCreatedJavaVMs(&jvm, 1, &jvm_count);
  if (result != JNI_OK || jvm_count == 0) {
    return nullptr;
  }

  return jvm;
}

static void GetJNIEnv(JavaVM* jvm, JNIEnv*& jni_env) {
  jint result = jvm->GetEnv((void**)&jni_env, JNI_VERSION_1_8);
  if (result == JNI_EDETACHED) {
    result = jvm->AttachCurrentThread((void**)&jni_env, nullptr);
  }
  if (result != JNI_OK) {
    jni_env = nullptr;
  }
}

static jclass DefineOrGetInjector(JNIEnv* jni_env) {
  jclass injector_class = jni_env->FindClass("ForgeInjector");
  if (injector_class) {
    return injector_class;
  }

  jni_env->ExceptionClear();

  injector_class = jni_env->FindClass("FabricInjector");
  if (injector_class) {
    return injector_class;
  }

  jni_env->ExceptionClear();

  injector_class = jni_env->DefineClass(nullptr, nullptr,
    (const jbyte*)injector_class_data, sizeof(injector_class_data));

  return injector_class;
}

static jobjectArray GetJarClassesArray(JNIEnv* jni_env) {
  jclass byte_array_class = jni_env->FindClass("[B");
  jobjectArray jar_classes_array = jni_env->NewObjectArray(jar_classes_count, byte_array_class, nullptr);

  for (int i = 0; i < jar_classes_count; i++) {
    jbyteArray byte_array = jni_env->NewByteArray(jar_class_sizes[i]);
    jni_env->SetByteArrayRegion(byte_array, 0, jar_class_sizes[i], (const jbyte*)jar_classes[i]);
    jni_env->SetObjectArrayElement(jar_classes_array, i, byte_array);
  }

  return jar_classes_array;
}

static void CallInjector(JNIEnv* jni_env, jclass injector_class, jobjectArray jar_classes_array) {
  const auto inject_method_id = jni_env->GetStaticMethodID(injector_class, "inject", "([[B)V");
  if (!inject_method_id) {
    return;
  }

  jni_env->CallStaticVoidMethod(injector_class, inject_method_id, jar_classes_array);
}

void RunInjector() {
  const auto jvm = GetJVM();
  if (!jvm) return;

  JNIEnv* jni_env;
  GetJNIEnv(jvm, jni_env);
  if (!jni_env) return;

  const auto injector_class = DefineOrGetInjector(jni_env);
  if (!injector_class) return;

  const auto jar_classes_array = GetJarClassesArray(jni_env);
  if (!jar_classes_array) return;

  CallInjector(jni_env, injector_class, jar_classes_array);

  FreeLibraryAndExitThread(::global_dll_instance, 0);
}
