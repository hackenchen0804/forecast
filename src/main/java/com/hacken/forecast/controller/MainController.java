package com.hacken.forecast.controller;

import com.hacken.forecast.event.*;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.service.ExcelService;
import com.hacken.forecast.service.ExcelServiceThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@EnableAsync
public class MainController {

    @FXML
    Button selectBtn;

    @FXML
    Label fileNameLbl;

    @FXML
    ComboBox sheetsCmb;

    @FXML
    Button importBtn;

    @FXML
    Label msgLal;

    @FXML
    public void initialize() {
        this.selectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    initComponent();
                } catch (ForecastEx e) {
                    showAlert(e);
                }
            }
        });
        this.importBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String excelFile = MainController.this.fileNameLbl.getText();
                String sheetName = MainController.this.sheetsCmb.getSelectionModel().getSelectedItem().toString();
                Thread thread = new Thread(new ExcelServiceThread(excelFile,sheetName));
                thread.start();
                MainController.this.importBtn.setDisable(true);
            }
        });

    }


    @EventListener(classes = {ExcelRowEvent.class})
    public void handleRowEvent(ExcelRowEvent excelRowEvent) {
        String eventMsg = excelRowEvent.msg();
        showEventInMsgLabel(eventMsg);
    }

    @EventListener(classes = {FinishEvent.class})
    public void handleFinishEvent(FinishEvent finishEvent) {
        String eventMsg = finishEvent.msg();
        showEventInMsgLabel(eventMsg);
        this.importBtn.setDisable(false);
    }

    @EventListener(classes = {PrnWriterEvent.class})
    public void handlePrnWriterEvent(PrnWriterEvent prnWriterEvent) {
        String eventMsg = prnWriterEvent.msg();
        showEventInMsgLabel(eventMsg);
    }

    @EventListener(classes = {SheetWriterEvent.class})
    public void handleSheetWriterEvent(SheetWriterEvent sheetWriterEvent) {
        String eventMsg = sheetWriterEvent.msg();
        showEventInMsgLabel(eventMsg);
    }

    @EventListener(classes = {AlertEvent.class})
    public void handleAlertEvent(AlertEvent alertEvent){
        showAlert(alertEvent.getForecastEx());
    }

    private void showEventInMsgLabel(String eventMsg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MainController.this.msgLal.setText(eventMsg);
            }
        });
    }


    private void showAlert(ForecastEx e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("信息");
        alert.setHeaderText(e.getStatus().msgHead());
        alert.setContentText(e.getStatus().msgContent());
        alert.show();
    }


    private void initSheetsCmb(Workbook workbook) {
        if (workbook != null) {
            ObservableList<String> options = FXCollections.observableArrayList();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                options.add(workbook.getSheetName(i));
            }
            sheetsCmb.setItems(options);
        }

    }

    private void initComponent() throws ForecastEx {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("所有xlsx文件", "*.xlsx");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(selectBtn.getScene().getWindow());
        if (file != null) {
            fileNameLbl.setText(file.getAbsoluteFile().toString());
            try {
                ZipSecureFile.setMinInflateRatio(-1.0d);
                Workbook workbook = new XSSFWorkbook(file);
                initSheetsCmb(workbook);
            } catch (IOException ioException) {
                throw new ForecastEx(Status.FILENOTEXISTS);
            } catch (InvalidFormatException e) {
                throw new ForecastEx(Status.WRONGFILEFORMAT);
            } catch (Exception e) {
                throw new ForecastEx(Status.WRONGFILEFORMAT);
            }
        }
    }
}
