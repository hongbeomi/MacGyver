package github.hongbeomi.macgyver.mlkit.face_detection

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer

class FaceDetectionProcessor(override var doOnSuccess: (List<Face>) -> Unit) : BaseImageAnalyzer<List<Face>>() {

    private val highAccuracyOpts = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)

    override val process: Task<List<Face>>?
        get() = image?.let { detector.process(it) }

}