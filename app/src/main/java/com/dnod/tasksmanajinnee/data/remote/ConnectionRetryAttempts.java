package com.dnod.tasksmanajinnee.data.remote;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface ConnectionRetryAttempts {
}
