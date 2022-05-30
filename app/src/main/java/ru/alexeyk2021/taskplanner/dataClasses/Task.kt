package ru.alexeyk2021.taskplanner.dataClasses

import com.google.firebase.firestore.QueryDocumentSnapshot


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
        name: String,
        authorName: String,
        startDate: String,
        endDate: String,
        description: String = "",
        status: Int = TaskStatus.NOT_STARTED.ordinal
    ) : this() {
        this.userId = userId
        this.name = name
        this.authorName = authorName
        this.startDate = startDate
        this.endDate = endDate
        this.description = description
        this.status = status

//        Firebase.firestore.collection("tasks").get().addOnCompleteListener {
//            id = it.result.documents.size
//        }
    }

    constructor(data: QueryDocumentSnapshot) : this() {
        this.userId = data["userId"] as Int
        this.name = data["name"] as String
        this.authorName = data["authorName"] as String
        this.startDate = data["startDate"] as String
        this.endDate = data["endDate"] as String
        this.description = data["description"] as String
        this.status = data["status"] as Int
    }

    fun getInfo(): HashMap<String, Any> = hashMapOf(
        "userId" to userId,
        "name" to name,
        "authorName" to authorName,
        "startDate" to startDate,
        "endDate" to endDate,
        "description" to description,
        "status" to status
    )


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
    WORKING,
    NOT_STARTED,
    DONE
}
