package github.hongbeomi.macgyver.mlkit.reply

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

class ReplyProcessor {

    private val smartReplyGenerator = SmartReply.getClient()
    private val conversation = mutableListOf<TextMessage>()

    fun postMessage(text: String, receiveLiveData: MutableLiveData<String>) {
        conversation.add(TextMessage.createForLocalUser(text, System.currentTimeMillis()))
        smartReplyGenerator.suggestReplies(conversation)
            .addOnSuccessListener { result ->
                when (result.status) {
                    SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE -> TODO()
                    SmartReplySuggestionResult.STATUS_NO_REPLY -> TODO()
                    SmartReplySuggestionResult.STATUS_SUCCESS -> receiveLiveData.postValue(result.suggestions.joinToString { " " })
                }
            }
            .addOnFailureListener { Log.e("reply", it.toString()) }
    }

}