package de.christianbergau

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import java.util.*

class SortAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val editor = event.getData(CommonDataKeys.EDITOR)

        if (editor == null || project == null) {
            return
        }

        val selectionModel = editor.selectionModel
        val document = editor.document

        // Get the start and end positions of the selection
        val selectionStart = selectionModel.selectionStart
        val selectionEnd = selectionModel.selectionEnd

        // Get the start and end lines of the selection
        val startLine = document.getLineNumber(selectionStart)
        var endLine = document.getLineNumber(selectionEnd)

        // Adjust endLine if the selection ends exactly at the start of a line
        if (selectionEnd == document.getLineStartOffset(endLine)) {
            endLine--
        }

        // Extract the lines to be sorted
        val selectedLinesBuilder = StringBuilder()
        for (i in startLine..endLine) {
            val lineStartOffset = document.getLineStartOffset(i)
            val lineEndOffset = document.getLineEndOffset(i)
            selectedLinesBuilder
                .append(document.text.substring(lineStartOffset, lineEndOffset))
                .append("\n")
        }

        // Sort the extracted lines
        val lines = selectedLinesBuilder
            .toString()
            .split("\n".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        Arrays.sort(lines)

        // Join the sorted lines back into a single string
        val sortedText = lines.joinToString("\n")

        // Replace the selected lines with the sorted text within a write action
        WriteCommandAction.runWriteCommandAction(project) {
            val startOffset = document.getLineStartOffset(startLine)
            val endOffset = document.getLineEndOffset(endLine)
            document.replaceString(startOffset, endOffset, sortedText)
        }
    }
}
