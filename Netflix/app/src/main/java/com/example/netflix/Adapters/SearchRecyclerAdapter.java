package com.example.netflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.MainScreens.Search;
import com.example.netflix.Model.AllCategory;
import com.example.netflix.Model.CategoryItemList;
import com.example.netflix.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> implements Filterable {

    Context context;
    List<AllCategory> allCategoryList;
    List<AllCategory> fullist;

    public SearchRecyclerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
        this.fullist = new ArrayList<>(allCategoryList);
    }

    public static final class SearchViewHolder extends RecyclerView.ViewHolder{
        RecyclerView itemRecycler;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRecycler= itemView.findViewById(R.id.search_recycler);
        }
    }
    @Override
    public Filter getFilter()
    {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<AllCategory> filteredlist = new ArrayList<>();
            if(charSequence==null || charSequence.length()==0) {
                filteredlist.addAll(allCategoryList);
            }
            else {
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for(AllCategory item:fullist){
                    if(item.getCategoryTitle().toLowerCase().contains(filterpattern)){
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            allCategoryList.clear();
            allCategoryList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    @NonNull
    @Override
    public SearchRecyclerAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchRecyclerAdapter.SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.searchrecyclerlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.SearchViewHolder holder, int position) {
        setItemRecycler(holder.itemRecycler,allCategoryList.get(position).getCategoryItemList());

    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }
    public void setItemRecycler(RecyclerView recyclerView,List<CategoryItemList> categoryItem){
        ItemRecyclerAdapter itemRecyclerAdapter = new ItemRecyclerAdapter(context,categoryItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}
