package com.viettel.connect.data.sources

import com.google.firebase.database.FirebaseDatabase

class FirebaseDataSource {
    private val databaseReference = FirebaseDatabase.getInstance().getReference()
}