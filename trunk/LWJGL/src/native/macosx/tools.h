#ifndef TOOLS_H
#define TOOLS_H

#include <JavaVM/jni.h>
#include <Carbon/Carbon.h>

#define lock() {lockLWJGL();
#define unlock() unlockLWJGL();}

extern bool getDictLong(CFDictionaryRef dict, CFStringRef key, long *key_value);
extern bool registerHandler(JNIEnv* env, WindowRef win_ref, EventHandlerProcPtr func, UInt32 event_class, UInt32 event_kind);
extern bool initLock(JNIEnv* env);
extern void destroyLock(void);
extern void lockLWJGL(void);
extern void unlockLWJGL(void);

#endif
