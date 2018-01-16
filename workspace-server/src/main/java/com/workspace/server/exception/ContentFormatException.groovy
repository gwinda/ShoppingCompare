package com.workspace.server.exception

class ContentFormatException extends RuntimeException {

    static final class ContentFormatExceptionCode {

        /**
         * 格式化错误
         */
        static final Integer CONTENT_FORMAT_EXCEPTION = 1011

    }

    /**
     * 异常码
     */
    Integer exceptionCode

    ContentFormatException(){
        this.exceptionCode = ContentFormatExceptionCode.CONTENT_FORMAT_EXCEPTION
    }

    ContentFormatException(Integer exceptionCode){
        this.exceptionCode = exceptionCode
    }

    @Override
    String toString() {
        switch (exceptionCode){
            case ContentFormatExceptionCode.CONTENT_FORMAT_EXCEPTION:
                return 'server.exception.content-format-exception'
            default:
                return 'server.exception.content-format-exception'
        }
    }

}
