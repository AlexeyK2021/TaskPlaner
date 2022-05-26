package ru.alexeyk2021.taskplanner.dataClasses

import io.realm.RealmObject

//import org.bson.*

class User() : RealmObject {
    var id: Int = -1
    var email: String = ""
    var password: String = ""
    var name: String = ""
    val chiefId: Int = -1
    var subUsersId: List<User> = mutableListOf()

    constructor(
        id: Int,
        email: String,
        password: String,
        name: String,
    ) : this() {
        this.id = id
        this.email = email
        this.password = password
        this.name = name
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

    override fun toString(): String {
        return "User(id=$id, email='$email', password='$password', name='$name')"
    }
}
