package com.example.tpmodulonativo.Models

import android.health.connect.datatypes.units.Length
import com.google.firebase.firestore.GeoPoint
import java.util.Date

class User (nickName : String,name:String,lastName:String,email:String,birthday:Date,geoPoint: GeoPoint){
    var nickName : String = nickName
    var name : String = name
    var lastName : String = lastName
    var email : String = email
    var birthday : Date =  birthday
    var ubication : GeoPoint = geoPoint
}
