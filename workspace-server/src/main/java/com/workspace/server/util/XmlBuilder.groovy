package com.workspace.server.util

import groovy.xml.MarkupBuilder

class XmlBuilder extends MarkupBuilder {

    StringWriter stringWriter = null
    MarkupBuilder builder = null

    XmlBuilder(){
        stringWriter = new StringWriter()
        builder = new MarkupBuilder(stringWriter)
        builder.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
    }

    @Override
    String toString() {
        return stringWriter.toString()
    }

}
