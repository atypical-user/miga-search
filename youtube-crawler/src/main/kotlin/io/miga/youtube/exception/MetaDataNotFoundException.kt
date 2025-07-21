package io.miga.youtube.exception

class MetaDataNotFoundException: RuntimeException {
    constructor(type: String): super("MetaData ($type) not found")
}