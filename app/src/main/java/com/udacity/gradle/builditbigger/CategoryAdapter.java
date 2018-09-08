package com.udacity.gradle.builditbigger;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.gradle.builditbigger.databinding.CategoryListItemBinding;

import java.util.List;

/**
 * {@link CategoryAdapter} exposes a list of categories to a {@link RecyclerView}.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    /** Member variable for the list of categories */
    private List<Category> mCategoryList;

    /** An on-click handler */
    private final CategoryAdapterOnClickHandler mOnClickHandler;

    public interface CategoryAdapterOnClickHandler {
        void onItemClick(int position);
    }

    /**
     * Constructor for CategoryAdapter.
     */
    public CategoryAdapter(List<Category> categoryList, CategoryAdapterOnClickHandler onClickHandler) {
        mCategoryList = categoryList;
        mOnClickHandler = onClickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created.
     */
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryListItemBinding categoryBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(categoryBinding);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = mCategoryList.get(position);
        holder.bind(category);
    }

    /**
     * This method simply return the number of items to display.
     *
     * @return The number of categories
     */
    @Override
    public int getItemCount() {
        if (null == mCategoryList) return 0;
        return mCategoryList.size();
    }

    /**
     * Cache the children views for a category list item.
     */
    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** This field is used for data binding */
        private CategoryListItemBinding mCategoryBinding;

        /**
         * Constructor for the CategoryViewHolder.
         *
         * @param categoryListItemBinding Used to access the layout's variable and views
         */
        public CategoryViewHolder(CategoryListItemBinding categoryListItemBinding) {
            super(categoryListItemBinding.getRoot());
            mCategoryBinding = categoryListItemBinding;
            itemView.setOnClickListener(this);
        }

        /**
         * This method will take a category object as input and use it to display the category image
         * and the name within a list item.
         *
         * @param category The category object
         */
        void bind(Category category) {
            mCategoryBinding.ivCategory.setImageResource(category.getImageId());
            mCategoryBinding.tvCategory.setText(category.getCategoryName());
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickHandler.onItemClick(adapterPosition);
        }
    }
}
