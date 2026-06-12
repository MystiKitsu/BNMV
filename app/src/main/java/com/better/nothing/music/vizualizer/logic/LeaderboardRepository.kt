package com.better.nothing.music.vizualizer.logic

import com.better.nothing.music.vizualizer.model.LeaderboardEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class LeaderboardRepository {
    private val database = FirebaseDatabase.getInstance("https://bnmv-67120-default-rtdb.europe-west1.firebasedatabase.app").getReference("leaderboard")

    fun getTopUsers(limit: Int = 100): Flow<List<LeaderboardEntry>> = callbackFlow {
        val query = database.orderByChild("totalTimeMs").limitToLast(limit)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val entries = snapshot.children.mapNotNull { 
                    it.getValue(LeaderboardEntry::class.java)
                }.reversed() // orderByChild is ascending
                trySend(entries)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun updateScore(entry: LeaderboardEntry) {
        database.child(entry.userId).setValue(entry).await()
    }
}
