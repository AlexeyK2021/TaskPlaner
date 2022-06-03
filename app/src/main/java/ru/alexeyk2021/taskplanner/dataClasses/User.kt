package ru.alexeyk2021.taskplanner.dataClasses

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//import org.bson.*

class User() {
    var email: String = ""
    var name: String = ""
    var chiefId: String = ""
    var slavesId: List<User> = mutableListOf()
    var tasksId: List<Task> = mutableListOf()

    constructor(
        email: String,
        name: String,
        chiefId: String = "",
        tasksId: List<Task> = mutableListOf(),
        slavesId: List<User> = mutableListOf()
    ) : this() {
        this.email = email
        this.name = name
        this.chiefId = chiefId
        this.tasksId = tasksId
        this.slavesId = slavesId
    }

    fun getInfo(): HashMap<String, Any> {
        return hashMapOf(
            "email" to email,
            "name" to name,
            "tasks" to tasksId,
            "chiefId" to -1,
            "slaves" to slavesId
        )
    }

    constructor(data: QueryDocumentSnapshot) : this() {
        this.email = data["email"] as String
        this.name = data["name"] as String
        this.chiefId = data["chiefId"] as String
        this.slavesId = data["slavesId"] as List<User>
        this.tasksId = data["tasksId"] as List<Task>
    }

    override fun toString(): String {
        return "User(email='$email', name='$name', chiefId=$chiefId, slavesId=$slavesId, tasksId=$tasksId)"
    }

//    constructor(userInfo: Document) : this() {
//        val bsonReader = BsonDocumentReader(userInfo.toBsonDocument())
//        bsonReader.readStartDocument()
//        if (bsonReader.readName() == "_id") {
//            id = bsonReader.readInt32()
//        }
//        if (bsonReader.readName() == "email") {
//            email = bsonReader.readString()
//        }
//        if (bsonReader.readName() == "passwd") {
//            password = bsonReader.readString()
//        }
//        if (bsonReader.readName() == "name") {
//            name = bsonReader.readString()
//        }
//        bsonReader.readEndDocument()
//    }
//
//    fun toDocument(): Document {
//        val doc = Document()
//        doc.append("_id", BsonInt32(id))
//        doc.append("email", BsonString(email))
//        doc.append("passwd", BsonString(password))
//        doc.append("name", BsonString(name))
//        return doc
//    }


}
