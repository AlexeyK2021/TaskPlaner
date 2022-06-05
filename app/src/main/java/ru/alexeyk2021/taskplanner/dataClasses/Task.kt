package ru.alexeyk2021.taskplanner.dataClasses

import com.google.firebase.firestore.QueryDocumentSnapshot


class Task() {
    var userEmail: String = ""
    var name: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var description: String = ""
    var status: Int = 0

    constructor(
        userEmail: String,
        name: String,
        startDate: String,
        endDate: String,
        description: String = "",
        status: Int = TaskStatus.NOT_STARTED.ordinal
    ) : this() {
        this.userEmail = userEmail
        this.name = name
        this.startDate = startDate
        this.endDate = endDate
        this.description = description
        this.status = status

//        Firebase.firestore.collection("tasks").get().addOnCompleteListener {
//            id = it.result.documents.size
//        }
    }

    constructor(data: HashMap<String, String>) : this() {
        this.userEmail = data["userId"] as String
        this.name = data["name"] as String
        this.startDate = data["startDate"] as String
        this.endDate = data["endDate"] as String
        this.description = data["description"] as String
        this.status = data["status"]!!.toInt()
    }

    fun getInfo(): HashMap<String, Any> = hashMapOf(
        "userEmail" to userEmail,
        "name" to name,
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
        return "Task(userEmail=$userEmail, name='$name', startDate='$startDate', endDate='$endDate', description='$description', status=$status)"
    }
}

enum class TaskStatus {
    WORKING,
    NOT_STARTED,
    DONE
}
