package com.alps.shisu.service;

public interface OnTaskCompleted<T> {
    void onTaskCompleted(T response);
}