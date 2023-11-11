package com.example.tpmodulonativo.Models

import com.google.firebase.firestore.GeoPoint
import java.util.Date

class User (nickName : String,name:String,lastName:String,email:String,birthday:Date,geoPoint: GeoPoint,password:String){
    var nickName : String = nickName
    var name : String = name
    var lastName : String = lastName
    var email : String = email
    var birthday : Date =  birthday
    var ubication : GeoPoint = geoPoint
    var password : String = password
}
