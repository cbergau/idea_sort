package de.christianbergau

class SortLinesDescAction : BaseSortLines() {
    override fun sortLines(lines: Array<String>) {
        lines.sortDescending()
    }
}
