package com.example.mybudget2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ContactViewHolder>{

    //создаём ArrayList для списка контактов
    private ArrayList<Budget> budgetArrayList = new ArrayList<>();
    private MainActivity mainActivity;

    public BudgetAdapter(ArrayList<Budget> budgetArrayList, MainActivity mainActivity) {
        this.budgetArrayList = budgetArrayList;
        this.mainActivity = mainActivity;
    }

    public void setBudgetArrayList(ArrayList<Budget> budgetArrayList) {
        this.budgetArrayList = budgetArrayList;
        notifyDataSetChanged(); //извещение адаптера об изменении данных
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        Budget budget = budgetArrayList.get(position);
        holder.itemTextView.setText(budget.getItem());
        holder.subItemTextView.setText(budget.getSubItem());
        holder.valueTextView.setText(budget.getValue());
        holder.accountTextView.setText(budget.getAccount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.addAndEditContact(true, budget, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return budgetArrayList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        private TextView itemTextView, subItemTextView, valueTextView, accountTextView;

        public ContactViewHolder(@NonNull View itemView) {

            super(itemView);
            itemTextView = itemView.findViewById(R.id.itemTextView);
            subItemTextView = itemView.findViewById(R.id.subItemTextView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
            accountTextView = itemView.findViewById(R.id.accountTextView);

        }
    }
}
