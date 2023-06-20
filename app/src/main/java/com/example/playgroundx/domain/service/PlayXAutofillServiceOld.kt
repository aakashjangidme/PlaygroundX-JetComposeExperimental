package com.example.playgroundx.domain.service
/*

import android.app.assist.AssistStructure
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
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

            val usernamePresentation = createPresentationView(username)
            val passwordPresentation = createPresentationView(password)

            var response: FillResponse? = null;


            if (parsedStructure.usernameId != null) {

                val dataset = Dataset.Builder().setValue(
                    parsedStructure.usernameId,
                    AutofillValue.forText(username),
                    usernamePresentation
                ).build()

                response = FillResponse.Builder().addDataset(dataset).build()
            }

            if (parsedStructure.passwordId != null) {

                val dataset = Dataset.Builder().setValue(
                    parsedStructure.passwordId,
                    AutofillValue.forText(password),
                    passwordPresentation
                ).build()

                response = FillResponse.Builder().addDataset(dataset).build()
            }

            callback.onSuccess(response)
        } else {
            callback.onFailure("Failed to retrieve structure.")
        }
    }

    private fun parseStructure(structure: AssistStructure): ParsedStructure {

        Timber.tag(TAG).d("parseStructure::isHomeActivity       : %s", structure.isHomeActivity)
        Timber.tag(TAG).d("parseStructure::windowNodeCount      : %s", structure.windowNodeCount)
        Timber.tag(TAG).d("parseStructure::activityComponent    : %s", structure.activityComponent)
        Timber.tag(TAG).d(
            "parseStructure::rootViewNode        : %s",
            structure.getWindowNodeAt(0).rootViewNode.toString()
        )


        val windowNode = structure.getWindowNodeAt(0).rootViewNode


        val windowNodes: List<AssistStructure.WindowNode> = structure.run {
            (0 until windowNodeCount).map { getWindowNodeAt(it) }
        }

        */
/*        var usernameId: AutofillId? = null
                var passwordId: AutofillId? = null

                windowNodes.forEach { windowNode: AssistStructure.WindowNode ->
                    val viewNode: AssistStructure.ViewNode? = windowNode.rootViewNode
                    if (viewNode != null) {
                        usernameId = findAutofillIdByHint(viewNode, "username")
                        passwordId = findAutofillIdByHint(viewNode, "password")
                    }

                }*//*


        val usernameId = findAutofillIdByHint(windowNode, "username")
        val passwordId = findAutofillIdByHint(windowNode, "password")


        Timber.tag(TAG).d("parseStructure::findAutofillIdByHint::usernameId : %s", usernameId)
        Timber.tag(TAG).d("parseStructure::findAutofillIdByHint::passwordId : %s", passwordId)


        return ParsedStructure(usernameId, passwordId)
    }

    fun traverseStructure(structure: AssistStructure) {
        val windowNodes: List<AssistStructure.WindowNode> = structure.run {
            (0 until windowNodeCount).map { getWindowNodeAt(it) }
        }

        windowNodes.forEach { windowNode: AssistStructure.WindowNode ->
            val viewNode: AssistStructure.ViewNode? = windowNode.rootViewNode
            traverseNode(viewNode)
        }
    }


    fun traverseNode(viewNode: AssistStructure.ViewNode?) {
        if (viewNode?.autofillHints?.isNotEmpty() == true) {
            // If the client app provides autofill hints, you can obtain them using
            // viewNode.getAutofillHints();
        } else {
            // Or use your own heuristics to describe the contents of a view
            // using methods such as getText() or getHint()


        }

        val children: List<AssistStructure.ViewNode>? = viewNode?.run {
            (0 until childCount).map { getChildAt(it) }
        }

        children?.forEach { childNode: AssistStructure.ViewNode ->
            traverseNode(childNode)
        }
    }


    private fun findAutofillIdByHint(
        viewNode: AssistStructure.ViewNode,
        hint: String,
    ): AutofillId? {
        Timber.tag(TAG)
            .d("findAutofillIdByHint::node::autofillId       : %s", viewNode.autofillId.toString())
        Timber.tag(TAG).d(
            "findAutofillIdByHint::node::autofillHints    : %s", viewNode.autofillHints.toString()
        )
        Timber.tag(TAG)
            .d("findAutofillIdByHint::node::hint             : %s", viewNode.hint.toString())



        if (viewNode.autofillHints?.isNotEmpty() == true) {
            // If the client app provides autofill hints, you can obtain them using
            viewNode.getAutofillHints();
        } else {
            // Or use your own heuristics to describe the contents of a view
            // using methods such as getText() or getHint()

            if (viewNode.hint.toString().lowercase().contains(hint)) {
                return viewNode.autofillId
            }
        }


        */
/*    val autofillHints = node.autofillHints

            if ((autofillHints != null) && autofillHints.any {
                    it.lowercase().contains(hint.lowercase())
                }) {
                return node.autofillId
            }
    *//*

        // If a matching autofill ID is not found in the current node,
        // recursively search in the child nodes.
        for (i in 0 until viewNode.childCount) {
            val childNode = viewNode.getChildAt(i)
            val autofillId = findAutofillIdByHint(childNode, hint)
            if (autofillId != null) {
                return autofillId
            }
        }

        return null
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

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        Timber.tag(TAG).d("onSaveRequest : %s", request.toString())

    }


    data class ParsedStructure(val usernameId: AutofillId?, val passwordId: AutofillId?)
    data class UserData(val username: String, val password: String)

    companion object {
        private val TAG: String = PlayXAutofillService::class.java.simpleName
    }
}*/
