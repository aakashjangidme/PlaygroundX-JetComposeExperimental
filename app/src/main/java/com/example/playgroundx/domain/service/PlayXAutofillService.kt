package com.example.playgroundx.domain.service

import android.app.assist.AssistStructure
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import com.example.playgroundx.R
import timber.log.Timber

class PlayXAutofillService : AutofillService() {

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback,
    ) {
        Timber.tag(TAG).d("onFillRequest : %s", request.toString())

        val structure = request.fillContexts.lastOrNull()?.structure

        Timber.tag(TAG).d("onFillRequest : %s", structure.toString())

        if (structure != null) {
            Timber.tag(TAG).d("onFillRequest : AssistStructure was NOT null")

            // Traverse the structure looking for nodes to fill out
            val parsedStructure: ParsedStructure = parseStructure(structure)

            // Fetch user data that matches the fields
            val (username: String, password: String) = fetchUserData(parsedStructure)

            val responseBuilder = FillResponse.Builder()

            parsedStructure.usernameId?.let { usernameId ->
                val usernamePresentation = createPresentationView(username)
                val usernameDataset = createDataset(usernameId, username, usernamePresentation)
                responseBuilder.addDataset(usernameDataset)
            }

            parsedStructure.passwordId?.let { passwordId ->
                val passwordPresentation = createPresentationView(password)
                val passwordDataset = createDataset(passwordId, password, passwordPresentation)
                responseBuilder.addDataset(passwordDataset)
            }

            val response = responseBuilder.build()

            callback.onSuccess(response)
        } else {
            callback.onFailure("Failed to retrieve structure.")
        }
    }

    private var usernameId: AutofillId? = null
    private var passwordId: AutofillId? = null

    private fun parseStructure(structure: AssistStructure): ParsedStructure {
        Timber.tag(TAG).d("parseStructure::isHomeActivity       : %s", structure.isHomeActivity)
        Timber.tag(TAG).d("parseStructure::windowNodeCount      : %s", structure.windowNodeCount)
        Timber.tag(TAG).d("parseStructure::activityComponent    : %s", structure.activityComponent)
        Timber.tag(TAG).d(
            "parseStructure::rootViewNode        : %s",
            structure.getWindowNodeAt(0).rootViewNode.toString()
        )

        traverseStructure(structure)

        /*  val windowNode = structure.getWindowNodeAt(0).rootViewNode

          val usernameId = findAutofillIdByHint(windowNode, "username")
          val passwordId = findAutofillIdByHint(windowNode, "password")*/

        Timber.tag(TAG).d("parseStructure::findAutofillIdByHint::usernameId : %s", usernameId)
        Timber.tag(TAG).d("parseStructure::findAutofillIdByHint::passwordId : %s", passwordId)

        return ParsedStructure(usernameId, passwordId)
    }

    private fun findAutofillIdByHint(
        viewNode: AssistStructure.ViewNode,
        hint: String,
    ): AutofillId? {
        Timber.tag(TAG).d(
            "findAutofillIdByHint::node::autofillId       : %s", viewNode.autofillId.toString()
        )
        Timber.tag(TAG).d(
            "findAutofillIdByHint::node::autofillHints    : %s", viewNode.autofillHints.toString()
        )
        Timber.tag(TAG).d(
            "findAutofillIdByHint::node::hint             : %s", viewNode.hint.toString()
        )

        if (viewNode.autofillHints?.isNotEmpty() == true) {
            for (autofillHint in viewNode.autofillHints!!) {
                if (autofillHint.lowercase() == hint.lowercase()) {
                    return viewNode.autofillId
                }
            }
        } else {
            if (viewNode.hint?.lowercase()?.contains(hint.lowercase()) == true) {
                return viewNode.autofillId
            }
        }

        for (i in 0 until viewNode.childCount) {
            val childNode = viewNode.getChildAt(i)
            val autofillId = findAutofillIdByHint(childNode, hint)
            if (autofillId != null) {
                return autofillId
            }
        }

        return null
    }

    private fun traverseStructure(structure: AssistStructure) {

        val windowNodes: List<AssistStructure.WindowNode> = structure.run {
            (0 until windowNodeCount).map { getWindowNodeAt(it) }
        }

        windowNodes.forEach { windowNode: AssistStructure.WindowNode ->
            val viewNode: AssistStructure.ViewNode? = windowNode.rootViewNode
            traverseNode(viewNode)
        }
    }

    private fun traverseNode(viewNode: AssistStructure.ViewNode?) {
        if (viewNode?.autofillHints?.isNotEmpty() == true) {

            viewNode.autofillHints?.asList()?.forEach {
                Timber.tag("traverseNode::autofillHints").d(it.toString())
            }

            if (viewNode.autofillHints!!.contains("email")) {
                usernameId = viewNode.autofillId
            } else if (viewNode.autofillHints!!.contains("password")) {
                passwordId = viewNode.autofillId
            }

        } else {

            Timber.tag("traverseNode::hint").d(viewNode?.hint?.lowercase())

            // Or use your own heuristics to describe the contents of a view
            // using methods such as getText() or getHint()
            if (viewNode?.hint?.lowercase()?.contains("email".lowercase()) == true) {
                usernameId = viewNode.autofillId
            } else if (viewNode?.hint?.lowercase()?.contains("password".lowercase()) == true) {
                passwordId = viewNode.autofillId
            }
        }

        val children: List<AssistStructure.ViewNode>? = viewNode?.run {
            (0 until childCount).map { getChildAt(it) }
        }

        children?.forEach { childNode: AssistStructure.ViewNode ->
            traverseNode(childNode)
        }
    }


    private fun fetchUserData(parsedStructure: ParsedStructure): UserData {
        // TODO: Replace with your own logic to fetch user data from a secure storage
        val username = "john.doe@example.com"
        val password = "p@ssw0rd"
        return UserData(username, password)
    }

    private fun createPresentationView(text: String): RemoteViews {
        val context = applicationContext
        val presentationView = RemoteViews(context.packageName, R.layout.remote_view_layout)
        presentationView.setTextViewText(R.id.text_view_id, text)
        return presentationView
    }

    private fun createDataset(
        autofillId: AutofillId,
        value: String,
        presentation: RemoteViews,
    ): Dataset {
        return Dataset.Builder().setValue(autofillId, AutofillValue.forText(value), presentation)
            .build()
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        Timber.tag(TAG).d("onSaveRequest : %s", request.toString())

        // Get the structure from the request
        val context: List<FillContext> = request.fillContexts
        val structure: AssistStructure = context[context.size - 1].structure

        // Traverse the structure looking for data to save
        traverseStructure(structure)

        // Persist the data - if there are no errors, call onSuccess()
        callback.onSuccess()
    }

    data class ParsedStructure(val usernameId: AutofillId?, val passwordId: AutofillId?)
    data class UserData(val username: String, val password: String)

    companion object {
        private val TAG: String = PlayXAutofillService::class.java.simpleName
    }
}
