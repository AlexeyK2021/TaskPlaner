package ru.alexeyk2021.taskplanner.dataClasses


class Task() {
    var userId: Int = -1
    var id: Int = 0
    var name: String = ""
    var startDate: String = ""
    var authorName: String = ""
    var endDate: String = ""
    var description: String = ""
    var status: Int = 0

    constructor(
        userId: Int,
        id: Int,
        name: String,
        authorName: String,
        startDate: String,
        endDate: String,
        description: String,
        status: Int
    ) : this() {
        this.userId = userId
        this.id = id
        this.name = name
        this.authorName = authorName
        this.startDate = startDate
        this.endDate = endDate
        this.description = description
        this.status = status
    }

//    constructor(userInfo: Document) : this() {
//        val bsonReader = BsonDocumentReader(userInfo.toBsonDocument())
//        bsonReader.readStartDocument()
//        if (bsonReader.readName() == "userId") {
//            userId = bsonReader.readInt32()
//        }
//        if (bsonReader.readName() == "_id") {
//            id = bsonReader.readInt32()
//        }
//        if (bsonReader.readName() == "name") {
//            name = bsonReader.readString()
//        }
//        if (bsonReader.readName() == "startDate") {
//            startDate = bsonReader.readString()
//        }
//        if (bsonReader.readName() == "endDate") {
//            endDate = bsonReader.readString()
//        }
//        if (bsonReader.readName() == "description") {
//            description = bsonReader.readString()
//        }
//        if (bsonReader.readName() == "status") {
//            status = TaskStatus.values()[bsonReader.readInt32()]
//        }
//        bsonReader.readEndDocument()
//    }
//
//    fun toDocument(): Document {
//        val doc = Document()
//        doc.append("userId", BsonInt32(userId))
//        doc.append("_id", BsonInt32(id))
//        doc.append("name", BsonString(name))
//        doc.append("startDate", BsonString(startDate))
//        doc.append("endDate", BsonString(endDate))
//        doc.append("endDate", BsonString(description))
//        doc.append("endDate", BsonInt32(status.ordinal))
//        return doc
//    }

    override fun toString(): String {
        return "Task(userId=$userId, id=$id, name='$name', startDate='$startDate', endDate='$endDate', description='$description', status=$status)"
    }
}

enum class TaskStatus {
    NOT_STARTED,
    WORKING,
    DONE
}
