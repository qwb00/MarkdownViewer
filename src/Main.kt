package main.kotlin

import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

fun main() {
    // Main frame
    val frame = JFrame("Markdown Viewer")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(800, 600)
    frame.layout = BorderLayout()

    // The "Load File" button
    val loadButton = JButton("Load Markdown File")
    val buttonPanel = JPanel()
    buttonPanel.add(loadButton)
    frame.add(buttonPanel, BorderLayout.NORTH)

    // Text area to display the markdown content
    val textArea = JTextArea()
    textArea.isEditable = false

    // Scrollable pane for the text area
    val scrollPane = JScrollPane(textArea)
    frame.add(scrollPane, BorderLayout.CENTER)

    // Action listener for the "Load File" button
    loadButton.addActionListener {
        // Create a file chooser
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = "Select a Markdown File"
        fileChooser.fileFilter = FileNameExtensionFilter("Markdown Files (*.md)", "md")

        // Show the file chooser dialog
        val result = fileChooser.showOpenDialog(frame)

        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile

            // Check if the file has .md extension
            if (selectedFile.extension.lowercase() == "md") {
                try {
                    // Read the file content
                    val markdownContent = selectedFile.readText()

                    textArea.text = markdownContent

                    // If the content was loaded adding scroll pane and changing the button place
                    if (frame.contentPane.components.contains(buttonPanel)) {
                        frame.remove(buttonPanel)
                        val bottomPanel = JPanel()
                        bottomPanel.add(loadButton)
                        frame.add(bottomPanel, BorderLayout.SOUTH)
                        frame.validate()
                    }

                } catch (e: Exception) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "An error occurred while reading the file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            } else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Please select a file with a .md extension.",
                    "Invalid File",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }
    frame.isVisible = true // Show the frame
}
