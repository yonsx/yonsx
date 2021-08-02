package io.github.yonsx.server.utils;

import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * JSONMapper
 *
 * @author yakir on 2020/07/27 10:56.
 */
public class JSONMapper extends JsonMapper {

    private static final JSONMapper MAPPER = new JSONMapper();

    public static JSONMapper instance() {
        return MAPPER;
    }

}
