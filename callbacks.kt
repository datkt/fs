package datkt.fs

typealias StatCallback = (Error?, Stats?) -> Any?
typealias OpenCallback = (Error?, Int?) -> Any?
typealias EmptyCallback = (Error?) -> Any?
typealias BufferCallback = (Error?, ByteArray?) -> Any?
typealias ReadDirectoryCallback = (Error?, Array<String>?) -> Any?
typealias ReadFileCallback = BufferCallback
