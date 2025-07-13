#include <windows.h>
#include "injector.h"

DWORD WINAPI RunInjectorThreadProxy(LPVOID lpParam) {
  RunInjector();
  return 0;
}

BOOL WINAPI DllMain(HINSTANCE dll_instance, DWORD reason, LPVOID reserved) {
  if (reason == DLL_PROCESS_ATTACH) {
    ::global_dll_instance = dll_instance;
    CreateThread(nullptr, 0, &RunInjectorThreadProxy, nullptr, 0, nullptr);
  }
  return TRUE;
}
