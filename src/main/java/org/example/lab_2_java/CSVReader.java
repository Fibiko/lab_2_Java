package org.example.lab_2_java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

public class CSVReader {

    public static void reader(File file, double height, double width, GraphicsContext gc, Canvas canvas) {
        ArrayList<String> lineList = new ArrayList<>();
        var columns = 0;
        var rowsOriginal = 0;
        var counter = 0;
        // Reading CSV File
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (counter == 0) { // Column amount
                    line = line.substring(0, line.length() - 1);
                    columns = Integer.parseInt(line);
                    counter+=1;
                } else if (counter == 1) { // Row amount
                    line = line.substring(0, line.length() - 1);
                    rowsOriginal = Integer.parseInt(line);
                    counter+=1;
                } else { // Show picture
                    lineList.add(line);
                    System.out.println(line);
                }
            }

            List<int[]> matrix = lineList.stream()
                    .map(row -> Arrays.stream(row.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toList();

            int rows = matrix.size();
            int cols = matrix.get(0).length;

            List<List<Integer>> hintsRows = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                List<Integer> rowHints = new ArrayList<>();
                int inRowLength = 0;
                for (int j = 0; j < cols; j++) {
                    if (matrix.get(i)[j] == 1) {
                        inRowLength++;
                    } else {
                        if (inRowLength > 0) {
                            rowHints.add(inRowLength);
                            inRowLength = 0;
                        }
                    }
                }
                if (inRowLength > 0) {
                    rowHints.add(inRowLength);
                }
                hintsRows.add(rowHints);
            }

            List<List<Integer>> hintsColumns = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                List<Integer> colHints = new ArrayList<>();
                int inColumnLength = 0;
                for (int i = 0; i < rows; i++) {
                    if (matrix.get(i)[j] == 1) {
                        inColumnLength++;
                    } else {
                        if (inColumnLength > 0) {
                            colHints.add(inColumnLength);
                            inColumnLength = 0;
                        }
                    }
                }
                if (inColumnLength > 0) {
                    colHints.add(inColumnLength);
                }
                hintsColumns.add(colHints);
            }

            int maxRowCount = hintsRows.stream().mapToInt(List::size).max().orElse(0);
            int maxColumnCount = hintsColumns.stream().mapToInt(List::size).max().orElse(0);
            System.out.println("MaxRowCount = " + maxRowCount);
            System.out.println("MaxColumnCount = " + maxColumnCount);

            //Standart rows list
            for (int i = 0; i < hintsRows.size(); i++) {
                List<Integer> rowHints = hintsRows.get(i);
                while (rowHints.size() < maxRowCount) {
                    rowHints.add(0);
                }
            }

            System.out.println("Hints for rows:");
            System.out.println(hintsRows);
            System.out.println("Size = " + hintsRows.size());


            //Standart column list
            for (int i = 0; i < hintsColumns.size(); i++) {
                List<Integer> colHints = hintsColumns.get(i);
                while (colHints.size() < maxColumnCount) {
                    colHints.add(0);
                }
            }
            System.out.println("Hints for columns:");
            System.out.println(hintsColumns);
            System.out.println("Size = " + hintsColumns.size());

            // Amount for adaptive size
            var finalColumns = columns + maxColumnCount;
            var finalRows = rowsOriginal + maxRowCount;

            // Setting size for each square
            var size1 = height/finalColumns;
            var size2 = width/finalRows;
            var size = Math.max(size1, size2);
            System.out.println("Columns = " + columns);
            System.out.println("Rows = " + rowsOriginal);
            System.out.println("Size of each square = " + size);

            List<Rectangle> fields = new ArrayList<>();
            int[][] stateMatrix = new int[columns][rowsOriginal];

            // Stroke reactive rectangles
            for (int i=0; i<columns; i++) {
                for (int j=0; j<rowsOriginal; j++) {
                    Rectangle field = new Rectangle(maxRowCount*size + i * size, maxColumnCount*size+ j * size,
                            maxRowCount*size + size*(i+1), maxColumnCount*size +size*(j+1), 0, false);
                    stateMatrix[i][j] = 0;
                    fields.add(field);
                    field.draw(gc);
                    stateMatrix[i][j] = 0;
                }
            }

            // Stroke hint rectangles for rows with numbers
            for (int i = 0; i < maxColumnCount; i++) {
                for (int j = 0; j < rowsOriginal; j++) {
                    RectangleHint field = new RectangleHint(i * size, maxColumnCount * size + j * size,
                            size * (i + 1), maxColumnCount * size + size * (j + 1), hintsRows.get(j).get(i).toString());
                    field.draw(gc);
                }
            }

            // Stroke hint rectangles for columns with numbers
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < maxRowCount; j++) {
                    RectangleHint field = new RectangleHint(maxRowCount * size + i * size, j * size,
                            maxColumnCount * size + size * (i + 1), size * (j + 1), hintsColumns.get(i).get(j).toString());
                    field.draw(gc);
                }
            }

            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.get(i).length; j++) {
                    System.out.print(matrix.get(i)[j] + " ");
                }
                System.out.println();
            }

            int[][] matrixArray = new int[matrix.size()][];
            System.out.println(matrixArray);
            for (int i = 0; i < matrix.size(); i++) {
                matrixArray[i] = matrix.get(i);
            }
            for (int i = 0; i < matrixArray.length; i++) {
                for (int j = 0; j < matrixArray.length; j++) {
                    System.out.print(matrixArray[i][j] + " ");
                }
                System.out.println();
            }

            // Set listeners and fill reactive rectangles
            canvas.setOnMouseClicked(mouseEvent -> {
                double mouseX = mouseEvent.getX();
                double mouseY = mouseEvent.getY();

                for (Rectangle currentField : fields) {
                    if (currentField.contains(mouseX, mouseY)) {
                        if ((mouseEvent.getButton() == MouseButton.PRIMARY) && (!currentField.isCircleDrawn())) {
                            if (currentField.state == 0) {
                                gc.setFill(Color.BLACK);
                                gc.fillRect(currentField.getX1(), currentField.getY1(), size, size);
                                currentField.state = 1;
                            } else if (currentField.state == 1) {
                                gc.setFill(Color.WHITE);
                                gc.fillRect(currentField.getX1(), currentField.getY1(), size, size);
                                currentField.state = 0;
                            }
                            currentField.draw(gc);
                        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            if (currentField.isCircleDrawn()) {
                                currentField.setCircleDrawn(false);
                                gc.setFill(Color.WHITE);
                                gc.fillRect(currentField.getX1(), currentField.getY1(), size, size); // Стираем квадрат
                                currentField.draw(gc);
                                currentField.state = 0;
                            } else {
                                gc.setFill(Color.WHITE);
                                gc.fillRect(currentField.getX1(), currentField.getY1(), size, size);
                                currentField.state = 0;
                                currentField.drawCircle(gc);
                                currentField.setCircleDrawn(true);
                            }
                        }
                    }
                }
                for (int i = 0; i < fields.size(); i++) {
                    Rectangle currentField = fields.get(i);
                    if (currentField.contains(mouseX, mouseY)) {
                        int col = i / matrix.get(0).length;
                        int row = i % matrix.get(0).length;

                        if (currentField.state == matrix.get(row)[col]) {
                            stateMatrix[row][col] = currentField.state;
                            System.out.println("Состояния совпадают: " + currentField.state);
                        } else {
                            stateMatrix[row][col] = currentField.state;
                            System.out.println("Состояния не совпадают: " + currentField.state + " != " + matrix.get(row)[col]);
                        }
                        if (Arrays.deepEquals(stateMatrix, matrixArray)) {
                            System.out.println("ПОБЕДА");
                        }
                    }
                }
            });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }