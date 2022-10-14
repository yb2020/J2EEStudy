package com.gebilaoyi.j2ee.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SnowflakeApi {
    @GetMapping("/nextId")
    public Long nextId() {
        log.info("i'm in....");
        return SnowflakeIdUtil.nextId();
    }

    @GetMapping("/nextStringId")
    public String nextStringId() {
        return SnowflakeIdUtil.uniqueLongHex();
    }
}
