package zhuangzhi.android.baking.ui.recipe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.kabu.eats.util.SchedulerProvider
import zhuangzhi.android.baking.MainApplication
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.Recipe
import javax.inject.Inject
import android.util.DisplayMetrics
import io.reactivex.disposables.CompositeDisposable
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.event.RecipeClickedEvent
import zhuangzhi.android.baking.event.RxBus
import zhuangzhi.android.baking.ui.recipeDetail.RecipeDetailActivity


class RecipeActivity : AppCompatActivity() {
    @Inject lateinit var dataSource: DataSource
    @Inject lateinit var schedulerProvider: SchedulerProvider

    @BindView(R.id.recipe_list) lateinit var recipeList: RecyclerView

    private val eventDisposable = CompositeDisposable()

    private lateinit var listAdapter: RecipeAdapter
    private var listData: List<Recipe> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_act)

        ButterKnife.bind(this)

        listAdapter = RecipeAdapter(listData)
        val layoutManager = GridLayoutManager(this, getGridColNum())
        recipeList.layoutManager = layoutManager
        recipeList.adapter = listAdapter

        populateUI()
        bind()
    }

    private fun getGridColNum(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthDivider = 800
        val width = displayMetrics.widthPixels
        val nColumns = width / widthDivider
        return if (nColumns < 1) 1 else nColumns
    }

    private fun populateUI() {
        val recipes = dataSource.getRecipes(this)
        listAdapter.replaceData(recipes)
    }

    private fun bind() {
        eventDisposable.add(
                RxBus.listen(RecipeClickedEvent::class.java).subscribe { it ->
                    val intent = Intent(this, RecipeDetailActivity::class.java)
                    intent.putExtra(RecipeDetailActivity.RECIPE_ID, it.position)
                    startActivity(intent)
                }
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        eventDisposable.dispose()
    }
}
