package github.hongbeomi.macgyver.mlkit.object_detection

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer

class ObjectDetectionProcessor(override var doOnSuccess: (List<DetectedObject>) -> Unit) : BaseImageAnalyzer<List<DetectedObject>>() {

    private val options = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
        .enableClassification()
        .build()
    private val detector = ObjectDetection.getClient(options)

    override val process: Task<List<DetectedObject>>?
        get() = image?.let { detector.process(it) }

}