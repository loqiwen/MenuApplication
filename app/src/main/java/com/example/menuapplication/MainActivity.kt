package com.example.menuapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.menuapplication.R

class MainActivity : AppCompatActivity() {


    private lateinit var buttonMenu: Button
    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        menuAdapter = MenuAdapter(mainViewModel.getItems())
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.adapter = menuAdapter


        // устанавливается состояние видимости меню (для правильного отображения при смене ориентации)
        when (mainViewModel.recyclerViewVisible.value) {
            null -> {
                mainViewModel.recyclerViewVisible.value = true
                recyclerViewMenu.visibility = View.VISIBLE
            }
            false -> recyclerViewMenu.visibility = View.GONE
            else -> recyclerViewMenu.visibility = View.VISIBLE
        }

        // удаление элемента по свайпу
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                menuAdapter.removeItem(position)
            }
        }).attachToRecyclerView(recyclerViewMenu)


        // скрываем или показываем меню по нажатию кнопки
        buttonMenu.setOnClickListener {
            if (mainViewModel.recyclerViewVisible.value == true) {
                hideRecyclerViewWithAnimation()

            } else if (mainViewModel.recyclerViewVisible.value == false) {
                showRecyclerViewWithAnimation()
            }
            mainViewModel.recyclerViewVisible.value = !mainViewModel.recyclerViewVisible.value!!

        }
    }

    // скрытие меню
    private fun hideRecyclerViewWithAnimation() {
        val itemCount = menuAdapter.itemCount
        for (i in 0 until itemCount) {
            val holder = recyclerViewMenu.findViewHolderForAdapterPosition(i)
            holder?.itemView?.let { view ->
                view.clearAnimation()
                val animation = TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 5f
                )
                animation.duration = 500
                animation.startOffset = (i * 100).toLong()
                animation.interpolator = AccelerateInterpolator()
                if (i == 0) {
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {}
                        override fun onAnimationRepeat(animation: Animation?) {}
                        override fun onAnimationEnd(animation: Animation?) {
                            recyclerViewMenu.visibility = View.GONE
                        }
                    })
                }
                view.startAnimation(animation)
            }
        }
    }

    // открытие меню
    private fun showRecyclerViewWithAnimation() {
        recyclerViewMenu.visibility = View.VISIBLE
        val itemCount = menuAdapter.itemCount
        for (i in itemCount - 1 downTo 0) {
            val holder = recyclerViewMenu.findViewHolderForAdapterPosition(i)
            holder?.itemView?.let { view ->
                view.clearAnimation()
                val animation = TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 5f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0f
                )
                animation.duration = 400
                animation.startOffset = ((itemCount - 1 - i) * 100).toLong()
                animation.interpolator = AccelerateInterpolator()
                view.startAnimation(animation)
            }
        }
    }


    // инициализация элементов
    private fun initViews() {
        buttonMenu = findViewById(R.id.buttonMenu)
        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)
    }

}