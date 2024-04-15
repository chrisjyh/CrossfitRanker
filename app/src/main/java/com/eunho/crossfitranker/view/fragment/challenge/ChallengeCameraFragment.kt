package com.eunho.crossfitranker.view.fragment.challenge

import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.eunho.crossfitranker.common.PoseAnalyzer
import com.eunho.crossfitranker.databinding.FragmentChallengeCameraBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChallengeCameraFragment : BaseFragment<FragmentChallengeCameraBinding>(
    FragmentChallengeCameraBinding::inflate
) {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var poseDetector: PoseDetector
    private var isBackCamera = true

    override fun onViewCreated() {

        // 카메라 executor 세팅 안하면 메인 스레드 사용
        cameraExecutor = Executors.newSingleThreadExecutor()

        // setting pose detector option
        val options = PoseDetectorOptions.Builder()
            // Real-time detection
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()

        poseDetector = PoseDetection.getClient(options)

        binding.btnSwitchCamera.setOnClickListener {
            switchCamera()
        }

        startCamera()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OptIn(ExperimentalGetImage::class)
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // 프리뷰 세팅
            val preview = Preview
                .Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            // 이미지 분석
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, PoseAnalyzer(poseDetector, binding.tvSquatCount))
                }

            // 카메라 앞뒤 전환
            val cameraSelector = if(isBackCamera){
                CameraSelector.DEFAULT_BACK_CAMERA
            }else{
                CameraSelector.DEFAULT_FRONT_CAMERA
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
            } catch (exc: Exception) { }
        }, ContextCompat.getMainExecutor(requireContext()))


    }

    private fun switchCamera() {
        isBackCamera = !isBackCamera
        startCamera()
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}