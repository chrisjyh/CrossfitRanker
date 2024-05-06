package com.eunho.crossfitranker.view.fragment.challenge

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.eunho.crossfitranker.R
import com.eunho.crossfitranker.common.PoseAnalyzer
import com.eunho.crossfitranker.common.commonToast
import com.eunho.crossfitranker.databinding.FragmentChallengeCameraBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * 스쿼트 측정을 위한 카메라 프레그먼트
 * */
class ChallengeCameraFragment : BaseFragment<FragmentChallengeCameraBinding>(
    FragmentChallengeCameraBinding::inflate
) {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var poseDetector: PoseDetector
    private var isBackCamera = true

    override fun onViewCreated() {

        // 카메라 executor 세팅 안하면 메인 스레드 사용
        cameraExecutor = Executors.newSingleThreadExecutor()

        // pose detector option
        val options = PoseDetectorOptions.Builder()
            // 실시간 감지
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()

        poseDetector = PoseDetection.getClient(options)

        // 카메라 전환
        binding.btnSwitchCamera
            .clicks()
            .onEach {
                switchCamera()
            }.launchIn(lifecycleScope)
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
            } catch (exc: Exception) {
                commonToast(requireContext(), R.string.toast_camera_fail)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    // 카메라 전환
    private fun switchCamera() {
        isBackCamera = !isBackCamera
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}