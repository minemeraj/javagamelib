// 
// File:   Callback.h
// Author: alterself
//
// Created on November 28, 2002, 3:37 PM
//

#ifndef _JavaMethod_H
#define	_JavaMethod_H
#include <string>
#include <jni.h>

class JavaMethod {
public:
	JavaMethod(JNIEnv *, jobject, std::string);
	~JavaMethod();
        
        JNIEnv* env;
        jobject obj;
        std::string method;
protected:

private:
};

#endif	/* _JavaMethod_H */

