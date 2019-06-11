package com.example.getfood.ui.foodmenu;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.getfood.R;
import com.example.getfood.models.FoodItem;
import com.example.getfood.ui.base.BaseFragment;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class FoodCategoryFragment extends BaseFragment implements FoodCategoryMvpView {

    private List<FoodItem> foodItem;
    private String CATEGORY = null;
    private ShimmerFrameLayout shimmerLayout;
    private RecyclerView foodRecyclerView;
    private FoodMenuRecyclerViewDisplayAdapter mAdapter;
    private FoodCategoryPresenter<FoodCategoryFragment> presenter;

    public FoodCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void initViews(View view) {
        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        foodRecyclerView = view.findViewById(R.id.foodDisplayRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodRecyclerView.addItemDecoration(new DividerItemDecoration(foodRecyclerView.getContext(), LinearLayoutManager.VERTICAL));

        presenter = new FoodCategoryPresenter<>();
        presenter.onAttach(this);

        foodItem = new ArrayList<>();

//        Log.d("##DebugData", AppUtils.getInstance(getContext()).generateString());
        Bundle args = this.getArguments();

        if (args != null) {
            CATEGORY = args.getString(getString(R.string.cat_type));
        } else {
            Toast.makeText(getContext(), getString(R.string.args_empty), Toast.LENGTH_SHORT).show();
        }
        mAdapter = new FoodMenuRecyclerViewDisplayAdapter(foodItem, getContext());
        foodRecyclerView.setAdapter(mAdapter);
        presenter.fetchFoodList(CATEGORY);
    }

    @Override
    public void bindFoodListAdapter(final ArrayList<FoodItem> foodItems) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.updateFoodItems(foodItems);
//                TODO: Update arraylist and nnotifydatasetchandged()
                if (shimmerLayout.isShimmerStarted()) {
                    shimmerLayout.stopShimmer();
                    shimmerLayout.setVisibility(View.GONE);
                }
            }
        }, 500);
    }

    @Override
    public void onDatabaseError(DatabaseError databaseError) {

    }

    @Override
    public void setListeners(View view) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_food_category;
    }

}
