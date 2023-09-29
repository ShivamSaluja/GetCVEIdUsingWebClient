package com.example.demowebclient.utils.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
Methods in the class Parse the JSONNode and
 fetch value for the corresponding path.
 */
public final class JSONUtils {
    private JSONUtils() {
    }

    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    public static final String MISSING_NODE_MESSAGE = "Node is Missing or is NULL for path : {}";

    public static void valueAtJSONNodePath(JsonNode val, String path) {
        JsonNode nd = val.at(path);
        if (!nd.isNull() && !nd.isMissingNode()) {
            String value = String.valueOf(nd);
            log.info(value);
        }else
            log.error(MISSING_NODE_MESSAGE, path);

    }

    public static void valueAtJSONArrayNodePath(JsonNode val, String arrayNodePath, String pathInsideArrayNodeObject) {
        JsonNode nd = val.at(arrayNodePath);
        if ( !nd.isNull() && !nd.isMissingNode() ) {
            JsonNode cvssMetricVNode = val.at(arrayNodePath);
            forEachJSONNodeObject(cvssMetricVNode, pathInsideArrayNodeObject);
        } else
            log.error(MISSING_NODE_MESSAGE, arrayNodePath);
    }

    public static void forEachJSONNodeObject(JsonNode cvssMetricVNode, String pathInsideArray) {
        for (JsonNode nodeV : cvssMetricVNode) {
            JsonNode nd = nodeV.at(pathInsideArray);
            if( !nd.isNull() && !nd.isMissingNode() ) {
                String value = String.valueOf(nd);
                log.info(value);
            } else
                log.error(MISSING_NODE_MESSAGE, pathInsideArray);
        }
    }


}
