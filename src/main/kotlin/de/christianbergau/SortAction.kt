package de.christianbergau

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import com.intellij.openapi.project.Project

class SortAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project? = event.project
        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)

        if (editor == null || project == null) {
            return
        }

        val selectionModel: SelectionModel = editor.selectionModel
        val document: Document = editor.document

        val selectedText = selectionModel.selectedText ?: return
        val lines = selectedText.split("\n").sorted()
        val sortedText = lines.joinToString("\n")

        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(
                selectionModel.selectionStart,
                selectionModel.selectionEnd,
                sortedText
            )
        }
    }
}
