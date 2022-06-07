package ru.alexeyk2021.taskplanner.dataClasses

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//import org.bson.*

class User() {
    var email: String = ""
    var name: String = ""
    var chiefId: String = ""
    var slavesId: MutableList<String> = mutableListOf()
    var tasksId: MutableList<String> = mutableListOf()

    constructor(
        email: String,
        name: String,
        chiefId: String = "",
        tasksId: MutableList<String> = mutableListOf(),
        slavesId: MutableList<String> = mutableListOf()
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
            "chiefId" to chiefId,
            "slaves" to slavesId
        )
    }

    constructor(data: MutableMap<String, Any>) : this() {
        this.email = data["email"] as String
        this.name = data["name"] as String
        this.chiefId = data["chiefId"] as String

        this.slavesId =
            (data["slavesId"] as? MutableList<String>)!! // I don't know how do it. Thinking hmmm
        this.tasksId = (data["tasksId"] as? MutableList<String>)!!
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
