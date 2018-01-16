package com.workspace.server.util

import com.workspace.server.exception.ContentFormatException
import groovy.json.JsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ContentFormatter {

    private Logger logger = LoggerFactory.getLogger(ContentFormatter.class)

    static final class ContentType {
        public static final String TYPE_JSON = 'JSON'
        public static final String TYPE_XML = 'XML'
        public static final String TYPE_JSONP = 'JSONP'
    }

    def builder = null
    String currentFormatType = null
    String outputFormatTypes = null
    String defaultOutputFormatType = null

    @Override
    String toString() {
        switch (currentFormatType){
            case ContentType.TYPE_XML:
            case ContentType.TYPE_JSON:
                return builder?.toString()
            case ContentType.TYPE_JSONP:
                return "(${builder?.toString()})"
        }
        return null
    }

    Object content(){
        if(builder){
            switch (currentFormatType){
                case ContentType.TYPE_XML:
                    return builder.builder
                case ContentType.TYPE_JSON:
                case ContentType.TYPE_JSONP:
                    return builder
                default:
                    return null
            }
        }else
            throw new ContentFormatException()
    }

    void initializeBuilder(String formatType){
        if(formatType?.trim()?.toUpperCase() in outputFormatTypes.split(',')){
            switch (formatType?.trim()?.toUpperCase()){
                case ContentType.TYPE_XML:
                    currentFormatType = ContentType.TYPE_XML
                    builder = new XmlBuilder()
                    break
                case ContentType.TYPE_JSON:
                    currentFormatType = ContentType.TYPE_JSON
                    builder = new JsonBuilder()
                    break
                case ContentType.TYPE_JSONP:
                    currentFormatType = ContentType.TYPE_JSONP
                    builder = new JsonBuilder()
                    break
            }
        }else{
            logger.error("[workspace-server] ${formatType} Is Not Support Content Format, Please Check!")
            throw new ContentFormatException()
        }
    }

    void initializeDefaultBuilder(){
        initializeBuilder(defaultOutputFormatType)
    }

}
