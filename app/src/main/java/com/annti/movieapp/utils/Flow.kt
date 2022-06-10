package com.annti.movieapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun SearchView.onQueryTextChange(): Flow<String>{
    return callbackFlow {
        val textChangedListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                sendBlocking(newText.orEmpty())
                return true
            }

        }
        this@onQueryTextChange.setOnQueryTextListener(textChangedListener)
        awaitClose {
            this@onQueryTextChange.setOnQueryTextListener(null)
        }
    }

}