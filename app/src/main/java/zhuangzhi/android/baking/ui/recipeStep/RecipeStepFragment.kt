package zhuangzhi.android.baking.ui.recipeStep


import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.squareup.picasso.Picasso
import zhuangzhi.android.baking.MainApplication
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.data.Step
import javax.inject.Inject

class RecipeStepFragment: Fragment() {

    @Inject lateinit var dataSource: DataSource

    @BindView(R.id.recipe_step_video) lateinit var stepVideoView: SimpleExoPlayerView
    @BindView(R.id.recipe_step_description) lateinit var stepDescription: TextView
    @BindView(R.id.recipe_step_no_video) lateinit var noVideoImage: ImageView

    private var stepVideo: SimpleExoPlayer? = null
    private lateinit var step: Step

    private val argumentRecipeId: Int
        get() = arguments!!.getInt(ARGUMENT_RECIPE_ID)
    private val argumentStepId: Int
        get() = arguments!!.getInt(ARGUMENT_STEP_ID)

    override fun onAttach(context: Context?) {
        MainApplication.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.recipe_step_frag, container, false)
        ButterKnife.bind(this, root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step = dataSource.getStep(argumentRecipeId, argumentStepId, context!!)
        populateUI()
    }

    private fun populateUI() {
        stepDescription.text = step.description
        if (step.videoUrl.isEmpty()) {
            setUpImage(step)
        } else {
            setUpVideo(step)
        }
    }

    private fun setUpImage(step: Step) {
        stepVideoView.visibility = View.INVISIBLE
        noVideoImage.visibility = View.VISIBLE
        if (step.thumbnailUrl.isNotEmpty()) {
            val lastThree = step.thumbnailUrl.substringAfterLast('.')
            if (lastThree == "jpg" || lastThree == "png") {
                Picasso.get()
                        .load(Uri.parse(step.thumbnailUrl))
                        .placeholder(R.drawable.video_placeholder)
                        .into(noVideoImage)
            }
        }
    }

    private fun setUpVideo(step: Step) {
        stepVideoView.visibility = View.VISIBLE
        noVideoImage.visibility = View.INVISIBLE
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        stepVideo = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        stepVideoView.player = stepVideo
        val dataSourceFactory = DefaultDataSourceFactory(context, "BakingApp", DefaultBandwidthMeter())
        val extractorFactory = DefaultExtractorsFactory()
        val videoSource = ExtractorMediaSource(
                Uri.parse(step.videoUrl),
                dataSourceFactory,
                extractorFactory,
                null,
                null
        )
        stepVideo?.prepare(videoSource)
    }

    override fun onDestroy() {
        stepVideo?.release()
        super.onDestroy()
    }

    companion object {
        const val TAG = "RecipeStepFragment"
        const val ARGUMENT_RECIPE_ID = "RECIPE_ID"
        const val ARGUMENT_STEP_ID = "STEP_ID"
        operator fun invoke(recipeId: Int, stepId: Int): RecipeStepFragment {
            return RecipeStepFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARGUMENT_RECIPE_ID, recipeId)
                    putInt(ARGUMENT_STEP_ID, stepId)
                }
            }
        }
    }
}