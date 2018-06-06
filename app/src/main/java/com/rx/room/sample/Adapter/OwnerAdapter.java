package com.rx.room.sample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.room.sample.R;
import com.rx.room.sample.db.Owner;

import java.util.List;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<Owner> data;

    public OwnerAdapter(List<Owner> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }


    @Override
    public OwnerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OwnerAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvName.setText(data.get(position).getName());
        holder.tvAddress.setText(data.get(position).getAddress());


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setList(List<Owner> owners) {
        this.data = owners;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onClick(Owner Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvAddress = itemView.findViewById(R.id.address);
            background = itemView.findViewById(R.id.image);

        }


        public void click(final Owner owners, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClick(owners));
        }
    }


}
