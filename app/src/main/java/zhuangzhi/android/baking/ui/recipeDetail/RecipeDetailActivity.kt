package zhuangzhi.android.baking.ui.recipeDetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.disposables.CompositeDisposable
import zhuangzhi.android.baking.MainApplication
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.data.Recipe
import zhuangzhi.android.baking.data.Step
import zhuangzhi.android.baking.event.RxBus
import zhuangzhi.android.baking.event.StepClickEvent
import zhuangzhi.android.baking.ui.recipeStep.RecipeStepActivity
import zhuangzhi.android.baking.ui.recipeStep.RecipeStepFragment
import zhuangzhi.android.baking.util.Constants
import zhuangzhi.android.baking.util.PreferenceHelper
import zhuangzhi.android.baking.util.addFragmentToActivity
import zhuangzhi.android.baking.util.replaceFragmentToActivity
import zhuangzhi.android.baking.widget.RecipeWidgetManager
import javax.inject.Inject

class RecipeDetailActivity: AppCompatActivity() {
    @Inject lateinit var dataSource: DataSource
    @Inject lateinit var preferenceHelper: PreferenceHelper
    @Inject lateinit var recipeWidgetManager: RecipeWidgetManager

    @BindView(R.id.recipe_detail_ingredient_list) lateinit var ingredientsText: TextView
    @BindView(R.id.recipe_detail_step_list) lateinit var stepList: RecyclerView

    private var recipeId = 0
    private lateinit var recipe: Recipe

    private lateinit var listAdapter: RecipeDetailStepAdapter
    private var listData: List<Step> = emptyList()

    private val eventDisposable = CompositeDisposable()

    private var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_detail_act)

        ButterKnife.bind(this)

        recipeId = intent.getIntExtra(RECIPE_ID, 0)
        recipe = dataSource.getRecipe(recipeId, this)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = recipe.name
        }

        listAdapter = RecipeDetailStepAdapter(listData)
        val layoutManager = LinearLayoutManager(this)
        stepList.layoutManager = layoutManager
        stepList.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        stepList.adapter = listAdapter

        if (findViewById<FrameLayout>(R.id.recipe_detail_step_container) != null) {
            twoPane = true
        }

        populateUI()
        bind()
    }

    private fun bind() {
        eventDisposable.add(
                RxBus.listen(StepClickEvent::class.java).subscribe { it ->
                    if (!twoPane) {
                        val intent = Intent(this, RecipeStepActivity::class.java)
                        intent.putExtra(RecipeStepActivity.RECIPE_ID, recipeId)
                        intent.putExtra(RecipeStepActivity.STEP_ID, it.position)
                        startActivity(intent)
                    } else {
                        if (supportFragmentManager.findFragmentById(R.id.recipe_detail_step_container) == null) {
                            addFragmentToActivity(supportFragmentManager, RecipeStepFragment(recipeId, it.position), R.id.recipe_detail_step_container)
                        } else {
                            replaceFragmentToActivity(supportFragmentManager, RecipeStepFragment(recipeId, it.position), R.id.recipe_detail_step_container)
                        }
                    }
                }
        )
    }

    private fun populateUI() {
        listAdapter.replaceData(recipe.steps)
        val ingredients = recipe.ingredients
        var ingredientString = ""
        for (ingredient in ingredients) {
            if (ingredient.ingredient.isNotEmpty()) {
                ingredientString += "• ${ingredient.quantity} ${ingredient.measure} ${ingredient.ingredient}\n"
            }
        }
        ingredientsText.text = ingredientString.substring(0, ingredientString.length-1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.add_to_widget) {
            preferenceHelper.set(Constants.RECIPE_WIDGET_RECIPE_ID, recipeId)
            recipeWidgetManager.updateWidget()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        eventDisposable.dispose()
    }

    companion object {
        const val RECIPE_ID = "RECIPE_ID"
    }
}