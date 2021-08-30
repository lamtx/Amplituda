package linc.com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

import linc.com.amplituda.Amplituda;
import linc.com.amplituda.AmplitudaProgressListener;
import linc.com.amplituda.AmplitudaResult;
import linc.com.amplituda.Compress;
import linc.com.amplituda.InputAudio;
import linc.com.amplituda.ProgressOperation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Amplituda amplituda = new Amplituda(this);

        amplituda.processAudio(
                "/storage/emulated/0/Music/Linc - Amplituda.mp3",
                Compress.withParams(Compress.AVERAGE, 1),
                new AmplitudaProgressListener() {
                    @Override
                    public void onStartProgress() {
                        super.onStartProgress();
                    }

                    @Override
                    public void onStopProgress() {
                        super.onStopProgress();
                    }

                    @Override
                    public void onProgress(ProgressOperation operation, int progress) {

                    }
                }
        ).get(result -> { printResult(result);
        }, exception -> { exception.printStackTrace(); });
    }

    private void printResult(AmplitudaResult<?> result) {
        System.out.printf(Locale.US,
                "Audio info:\n" +
                        "millis = %d\n" +
                        "seconds = %d\n\n" +
                        "source = %s\n" +
                        "source type = %s\n\n" +
                        "Amplitudes:\n" +
                        "size: = %d\n" +
                        "list: = %s\n" +
                        "json: = %s\n" +
                        "single line sequence = %s\n" +
                        "new line sequence = %s\n" +
                        "custom delimiter sequence = %s\n%n",
                result.getAudioDuration(AmplitudaResult.DurationUnit.MILLIS),
                result.getAudioDuration(AmplitudaResult.DurationUnit.SECONDS),
                result.getInputAudioType() == InputAudio.Type.FILE ? ((File) result.getAudioSource()).getAbsolutePath() : result.getAudioSource(),
                result.getInputAudioType().name(),
                result.amplitudesAsList().size(),
                Arrays.toString(result.amplitudesAsList().toArray()),
                result.amplitudesAsJson(),
                result.amplitudesAsSequence(AmplitudaResult.SequenceFormat.SINGLE_LINE),
                result.amplitudesAsSequence(AmplitudaResult.SequenceFormat.NEW_LINE),
                result.amplitudesAsSequence(AmplitudaResult.SequenceFormat.SINGLE_LINE, " * ")
        );
    }
}