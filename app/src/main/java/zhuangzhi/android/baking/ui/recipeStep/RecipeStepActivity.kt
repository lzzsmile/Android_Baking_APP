package zhuangzhi.android.baking.ui.recipeStep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import zhuangzhi.android.baking.MainApplication
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.data.Step
import zhuangzhi.android.baking.util.addFragmentToActivity
import javax.inject.Inject

class RecipeStepActivity: AppCompatActivity() {
    @Inject lateinit var dataSource: DataSource

    @BindView(R.id.recipe_step_container) lateinit var stepContainer: FrameLayout

    private var recipeId = 0
    private var stepId = 0
    private lateinit var step: Step

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_step_act)

        ButterKnife.bind(this)

        recipeId = intent.getIntExtra(RECIPE_ID, 0)
        stepId = intent.getIntExtra(STEP_ID, 0)
        step = dataSource.getStep(recipeId, stepId, this)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = "${step.id}. ${step.shortDescription}"
        }

        if (supportFragmentManager.findFragmentById(R.id.recipe_step_container) == null) {
            addFragmentToActivity(supportFragmentManager, RecipeStepFragment(recipeId, stepId), R.id.recipe_step_container)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val RECIPE_ID = "RECIPE_ID"
        const val STEP_ID = "STEP_ID"
    }
}