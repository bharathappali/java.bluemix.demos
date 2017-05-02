/*
 * Copyright (c) 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <sys/types.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "killaction.h"

KillAction::KillAction() {
   signal = SIGKILL;
}

void KillAction::act(JNIEnv* jniEnv, jint resourceExhaustionFlags) {
	fprintf(stderr, "\njvmkill killing current process\n");
    kill(getpid(), signal);
}

void KillAction::setSignal(int sig) {
   signal = sig;
}
