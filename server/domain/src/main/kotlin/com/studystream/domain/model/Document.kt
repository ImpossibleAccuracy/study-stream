package com.studystream.domain.model

interface Document : BaseModel {
    var title: String
    var hash: String
    var path: String
    var type: Type

    interface Type : BaseModel {
        var title: String
        var mimeType: String
    }
}
