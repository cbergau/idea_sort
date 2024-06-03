package de.christianbergau

class SortLinesAscAction : BaseSortLines() {
    override fun sortLines(lines: Array<String>) {
        lines.sort()
    }
}
