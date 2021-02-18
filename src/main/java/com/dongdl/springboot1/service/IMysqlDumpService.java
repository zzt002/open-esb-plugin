package com.dongdl.springboot1.service;

public interface IMysqlDumpService {

    int backup();

    int revert(String gzfile);
}
