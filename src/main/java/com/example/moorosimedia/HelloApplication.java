package com.example.moorosimedia;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        String style =getClass().getResource("Style.css").toExternalForm();

        VBox root = new VBox(5);
        root.setFillWidth(true);

        HBox header = new HBox();

        Button fileChooserBTN = new Button("Open Media");
        Button playback = new Button("Playback");
        Button tools = new Button("Tools");
        Button view = new Button("View");
        Button help = new Button("Help");

        header.getChildren().addAll(fileChooserBTN,playback,view,tools,help);


        MediaView mediaView = new MediaView();

        mediaView.setFitWidth(1200);
        mediaView.setFitHeight(650);
        mediaView.setPreserveRatio(true);
        mediaView.setPickOnBounds(true);

        HBox controls = new HBox(5);
        Label statusLabel = new Label();

        Button playButton = new Button("▶");
        Button pauseButton = new Button("⏸");
        Button stopButton = new Button("⏹");
        Button loopButton = new Button("🔂");
        Button randomButton = new Button("🔀");
        Label volumeIcon = new Label("🔊");
        Slider durationSlider = new Slider();
        Label timeLabel = new Label("00:00/00:00");
        Slider volume = new Slider();


        HBox preview = new HBox();
        Pane spacer = new Pane();
        spacer.setMinWidth(55);

        Pane spacer1 = new Pane();
        spacer1.setMinWidth(850);


        fileChooserBTN.setOnAction(actionEvent -> {

            controls.getChildren().addAll(playButton,pauseButton,stopButton,loopButton,randomButton,spacer1,volumeIcon,volume);

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            Media media = new Media(file.toURI().toString());

            MediaPlayer mediaPlayer = new MediaPlayer(media);

            playButton.setOnAction(e -> mediaPlayer.play());
            pauseButton.setOnAction(e -> mediaPlayer.pause());
            stopButton.setOnAction(e -> mediaPlayer.stop());
            loopButton.setOnAction(e ->mediaPlayer.getMedia());

            volume.setMin(0);
            volume.setMax(1);
            volume.setValue(1);
            volume.setFocusTraversable(true);
            volume.setShowTickLabels(false);
            volume.setShowTickMarks(false);

            volume.valueProperty().addListener((observable,oldValue,newValue) ->{
                mediaPlayer.setVolume(newValue.doubleValue());
            });

            durationSlider.setMin(0);
            durationSlider.setMax(media.getDuration().toHours());
            durationSlider.setValue(0);

            durationSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    mediaPlayer.seek(Duration.hours(newValue.doubleValue()));
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration duration = mediaPlayer.getMedia().getDuration();
                    durationSlider.setMax(duration.toHours());
                }
            });

            preview.getChildren().addAll(spacer,mediaView);

            mediaView.setPreserveRatio(true);

            fileChooser.setTitle("Open Media File");
            if (file != null) {
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setAutoPlay(true);
                root.getChildren().addAll(preview,durationSlider,controls);
            }

        });


        root.getChildren().addAll(header);

        Scene scene = new Scene(root,1300,750);
        stage.setTitle("Hello!");
        scene.getStylesheets().addAll(style);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}